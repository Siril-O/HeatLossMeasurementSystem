package ua.heatloss.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.domain.*;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.modules.MainMeasurementModule;
import ua.heatloss.domain.modules.PipeMeasurementModule;
import ua.heatloss.domain.sensors.SensorType;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.facades.DataGeneratingFacade;
import ua.heatloss.facades.MeasurementModuleFacade;
import ua.heatloss.services.*;
import ua.heatloss.services.helper.DateHelper;

import java.util.*;

@Component
public class DefaultDataGeneratingFacade implements DataGeneratingFacade {

    private static final int DEFAULT_FLOORS = 5;
    private static final int DEFAULT_APARTMENTS_ON_FLOOR = 2;
    private static final int DEFAULT_ROOMS_QUANTITY = 2;
    private static final int DEFAULT_ENTRANCES = 2;

    public static final int LOW_TEMPERATURE_RANGE = 40;
    public static final int HIGHT_TEMPERATURE_RANGE = 75;

    public static final int MAX_START_TEMPERATURE_RANGE = 95;
    public static final int MIN_START_TEMPERATURE_RANGE = 60;

    public static final double MIN_FLOW_RANGE = 0.1;
    public static final double MAX_FLOW_RANGE = 3;

    public static final double DEFAULT_LOSS_ON_RADIATOR = 3;
    public static final double DELTA_LOSS_ON_RADIATOR = 0.7;

    public static final double DEFAULT_LOSS_ON_OVERLAP = 0.2;
    public static final double DELTA_LOSS_ON_OVERLAP = 0.05;

    public static final double DEFAULT_LOSS_ON_MEDIATORS = 3;
    public static final double DELTA_LOSS_ON_MEDIATORS = 1;
    public static final double MAX_LOS_ON_MEDIATOR = DEFAULT_LOSS_ON_MEDIATORS + DELTA_LOSS_ON_MEDIATORS;
    public static final double MIN_LOS_ON_MEDIATOR = DEFAULT_LOSS_ON_MEDIATORS - DELTA_LOSS_ON_MEDIATORS;


    private static final Random RANDOM = new Random();

    @Autowired
    private HouseService houseService;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private PipeService pipeService;
    @Autowired
    private MeasurementModuleFacade measurementSectionFacade;
    @Autowired
    private SensorModelService sensorModelService;
    @Autowired
    private MeasurementService measurementService;

    @Override
    public House generateMeasurementModules(House house) {

        TemperatureSensorModel temperatureSensorModel = sensorModelService.getTemperatureModelsList(0, 1).get(0);
        FlowSensorModel flowSensorModel = sensorModelService.getFlowModelsList(0, 1).get(0);
        if (house == null || temperatureSensorModel == null || flowSensorModel == null) {
            throw new IllegalArgumentException();
        }
        generateMainMeasurementModule(house, temperatureSensorModel, flowSensorModel);
        List<Apartment> apartments = generateApartments(DEFAULT_FLOORS, DEFAULT_APARTMENTS_ON_FLOOR, DEFAULT_ROOMS_QUANTITY, DEFAULT_ENTRANCES, house);
        List<Pipe> pipes = generatePipes(DEFAULT_ROOMS_QUANTITY * DEFAULT_ENTRANCES * DEFAULT_APARTMENTS_ON_FLOOR, house);
        Map<Pipe, List<Apartment>> appMap = mapPipes(apartments, pipes);
        for (Map.Entry<Pipe, List<Apartment>> entry : appMap.entrySet()) {
            Pipe pipe = entry.getKey();
            generatePipeMeasurementModule(pipe, temperatureSensorModel, flowSensorModel);
            generateMeasurementModules(pipe, temperatureSensorModel, flowSensorModel, entry.getValue());
        }
        houseService.refresh(house);
        return house;
    }


    @Override
    public List<Pipe> generatePipes(int quantity, House house) {
        List<Pipe> pipes = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Pipe pipe = new Pipe();
            pipe.setHouse(house);
            pipe.setOrdinalNumber(i + 1);
            pipeService.create(pipe);
            pipes.add(pipe);
        }
        return pipes;
    }

    @Override
    public List<Apartment> generateApartments(int floors, int apartmentsOnFloor, int rooms, int entrances, House house) {
        List<Apartment> apartments = new ArrayList<>();
        int number = 0;
        for (int ent = 0; ent < entrances; ent++) {
            for (int i = 0; i < floors; i++) {
                for (int onFloor = 0; onFloor < apartmentsOnFloor; onFloor++) {
                    Apartment apartment = new Apartment();
                    apartment.setHouse(house);
                    number++;
                    apartment.setFloor(i + 1);
                    apartment.setOwner("John Doe" + number);
                    apartment.setRooms(rooms);
                    apartment.setNumber(number);
                    apartmentService.create(apartment);
                    apartments.add(apartment);
                }
            }
        }
        return apartments;
    }

    @Override
    public List<House> generateHouses(int quantity) {
        List<House> houses = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            House house = new House();
            Address address = new Address();
            address.setHouseNumber("number " + i);
            address.setStreet("Street " + i);
            address.setCountry("Ukraine");
            address.setCity("Kyiv");
            house.setAddress(address);
            houseService.create(house);
            houses.add(house);
        }
        return houses;
    }

    @Override
    public void generateHouseMeasurementData(Date startDate, Date finishDate, long density, House house, boolean withApartments) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, finishDate);
        startDate = period.getStartDate();
        finishDate = period.getEndDate();
        density *= 1000;
        if (house.getPipes().isEmpty()) {
            throw new IllegalArgumentException("No pipes in house");
        }
        List<Measurement> measurements = new ArrayList<>();
        for (long i = startDate.getTime(); i < finishDate.getTime(); i += density) {
            double startTemperature = generateStartTemperature();
            measurements.addAll(generateMeasurementDataForHouse(house, startTemperature, new Date(i), withApartments));
        }
        measurementService.createButch(measurements);
    }

    @Override
    public void generatePipeMeasurementData(Date startDate, Date finishDate, long density, Pipe pipe, boolean withApartments) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, finishDate);
        startDate = period.getStartDate();
        finishDate = period.getEndDate();
        density *= 1000;
        List<Measurement> measurements = new ArrayList<>();
        for (long i = startDate.getTime(); i < finishDate.getTime(); i += density) {
            double startTemperature = generateStartTemperature();
            measurements.addAll(generateMeasurementDataForPipe(pipe, startTemperature, new Date(i), withApartments));
        }
        measurementService.createButch(measurements);
    }


    private double generateStartTemperature() {
        return generateRandomValue(MIN_START_TEMPERATURE_RANGE, MAX_START_TEMPERATURE_RANGE);
    }

    private List<Measurement> generateMeasurementDataForHouse(House house, double startTemperature, Date timestamp, boolean withApartments) {
        List<Measurement> measurements = new ArrayList<>();
        Measurement mainMeasurement = createMeasurement(house.getMainMeasurementModule(), timestamp);
        mainMeasurement.setInputValue(startTemperature);
        List<Pipe> pipes = house.getPipes();
        mainMeasurement.setFlowValue(MAX_FLOW_RANGE * pipes.size());
        for (Pipe pipe : pipes) {
            measurements.addAll(generateMeasurementDataForPipe(pipe, startTemperature, timestamp, withApartments));
        }
        mainMeasurement.setOutputValue(measurements.get(measurements.size() - 1).getOutputValue() -
                generateRandomValue(MIN_LOS_ON_MEDIATOR, MAX_LOS_ON_MEDIATOR));
        return measurements;
    }

    private List<Measurement> generateMeasurementDataForPipe(Pipe pipe, double startTemperature, Date timestamp, boolean withApartments) {
        List<Measurement> measurements = new ArrayList<>();
        Measurement pipeMeasurement = createMeasurement(pipe.getPipeMeasurementModule(), timestamp);
        startTemperature = startTemperature - generateRandomValue(MIN_LOS_ON_MEDIATOR, MAX_LOS_ON_MEDIATOR);
        pipeMeasurement.setInputValue(startTemperature);
        if (withApartments) {
            for (ApartmentMeasurementModule module : pipe.getApartmentMeasurementModules()) {
                Measurement measurement = generateApartmentMeasurement(module, timestamp, startTemperature);
                measurements.add(measurement);
                startTemperature = measurement.getOutputValue();
            }
        }
        pipeMeasurement.setFlowValue(MAX_FLOW_RANGE);
        pipeMeasurement.setOutputValue(measurements.get(measurements.size() - 1).getOutputValue());
        measurements.add(pipeMeasurement);
        return measurements;
    }


    private Measurement createMeasurement(AbstractMeasurementModule module, Date timestamp) {
        Measurement measurement = new Measurement();
        measurement.setMeasurementModule(module);
        measurement.setTimestamp(timestamp);
        return measurement;
    }


    private Measurement generateApartmentMeasurement(ApartmentMeasurementModule module, Date timestamp, double startTemperature) {
        Measurement measurement = createMeasurement(module, timestamp);
        double generatedValue = generateRandomValueForSensor(SensorType.INPUT, startTemperature);
        measurement.setInputValue(generatedValue);
        measurement.setFlowValue(generateRandomValueForFlowSensor());
        generatedValue = generateRandomValueForSensor(SensorType.OUTPUT, generatedValue);
        measurement.setOutputValue(generatedValue);
        return measurement;
    }

    private double generateRandomValueForSensor(SensorType type, double startTemperature) {
        double minRange = 0;
        double maxRange = 0;
        if (SensorType.INPUT == type) {
            minRange = startTemperature - DEFAULT_LOSS_ON_OVERLAP - DELTA_LOSS_ON_OVERLAP;
            maxRange = startTemperature - DEFAULT_LOSS_ON_OVERLAP + DELTA_LOSS_ON_OVERLAP;
        } else if (SensorType.OUTPUT == type) {
            minRange = startTemperature - DEFAULT_LOSS_ON_RADIATOR - DELTA_LOSS_ON_RADIATOR;
            maxRange = startTemperature - DEFAULT_LOSS_ON_RADIATOR + DELTA_LOSS_ON_RADIATOR;
        }
        return generateRandomValue(minRange, maxRange);
    }

    private double generateRandomValueForFlowSensor() {
        return generateRandomValue(MIN_FLOW_RANGE, MAX_FLOW_RANGE);
    }


    private double generateRandomValue(double rangeMin, double rangeMax) {
        double randomValue = rangeMin + (rangeMax - rangeMin) * RANDOM.nextDouble();
        return randomValue;
    }


    private Map<Pipe, List<Apartment>> mapPipes(List<Apartment> apartments, List<Pipe> pipes) {
        Map<Pipe, List<Apartment>> appMap = new LinkedHashMap<>();

        Map<Integer, List<Apartment>> apsByFloor = new LinkedHashMap<>();
        for (Apartment apartment : apartments) {
            int floor = apartment.getFloor();
            List<Apartment> appForCurrentFloor = apsByFloor.get(floor);
            if (appForCurrentFloor == null) {
                List<Apartment> appsOnTheFloor = new ArrayList<>();
                appsOnTheFloor.add(apartment);
                apsByFloor.put(floor, appsOnTheFloor);
            } else {
                appForCurrentFloor.add(apartment);
            }
        }

        for (Map.Entry<Integer, List<Apartment>> entry : apsByFloor.entrySet()) {
            int counter = 0;
            for (Apartment apartment : entry.getValue()) {
                for (int i = 0; i < apartment.getRooms(); i++) {
                    Pipe pipe = pipes.get(counter++);
                    List<Apartment> pipeApartments = appMap.get(pipe);
                    if (pipeApartments == null) {
                        pipeApartments = new ArrayList<>();
                        pipeApartments.add(apartment);
                        appMap.put(pipe, pipeApartments);
                    } else {
                        pipeApartments.add(apartment);
                    }
                }
            }
        }
        return appMap;
    }


    private List<ApartmentMeasurementModule> generateMeasurementModules(Pipe pipe, TemperatureSensorModel temperatureSensorModel,
                                                                        FlowSensorModel flowSensorModel, List<Apartment> apartments) {
        List<ApartmentMeasurementModule> measurementModules = new ArrayList<>();
        for (Apartment apartment : apartments) {
            ApartmentMeasurementModule module = generateApartmentModule(pipe, temperatureSensorModel, flowSensorModel, apartment);
            measurementModules.add(module);
        }
        return measurementModules;
    }

    private ApartmentMeasurementModule generateApartmentModule(Pipe pipe, TemperatureSensorModel temperatureSensorModel,
                                                               FlowSensorModel flowSensorModel, Apartment apartment) {
        ApartmentMeasurementModule module = new ApartmentMeasurementModule();
        module.setApartment(apartment);
        module.setPipe(pipe);
        measurementSectionFacade.createApartmentMeasurementModule(module, temperatureSensorModel, flowSensorModel);
        return module;
    }

    private PipeMeasurementModule generatePipeMeasurementModule(Pipe pipe, TemperatureSensorModel temperatureSensorModel,
                                                                FlowSensorModel flowSensorModel) {
        PipeMeasurementModule module = new PipeMeasurementModule();
        module.setPipe(pipe);
        measurementSectionFacade.createPipeMeasurementModule(module, temperatureSensorModel, flowSensorModel);
        return module;
    }

    private MainMeasurementModule generateMainMeasurementModule(House house, TemperatureSensorModel temperatureSensorModel,
                                                                FlowSensorModel flowSensorModel) {
        MainMeasurementModule module = new MainMeasurementModule();
        module.setHouse(house);
        measurementSectionFacade.createMainMeasurementModule(module, temperatureSensorModel, flowSensorModel);
        return module;
    }


}
