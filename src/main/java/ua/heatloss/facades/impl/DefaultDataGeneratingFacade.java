package ua.heatloss.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.domain.*;
import ua.heatloss.domain.sensors.SensorType;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.facades.DataGeneratingFacade;
import ua.heatloss.facades.MeasurementSectionFacade;
import ua.heatloss.services.*;

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

    public static final double DEFAULT_LOSS_ON_RADIATOR = 2.5;
    public static final double DELTA_LOSS_ON_RADIATOR = 0.5;

    public static final double DEFAULT_LOSS_ON_OVERLAP = 0.2;
    public static final double DELTA_LOSS_ON_OVERLAP = 0.05;

    public static final double DEFAULT_LOSS_ON_PIPE = 0.8;
    public static final double DELTA_LOSS_ON_PIPE = 0.2;

    private static final Random RANDOM = new Random();

    @Autowired
    private HouseService houseService;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private PipeService pipeService;
    @Autowired
    private MeasurementSectionFacade measurementSectionFacade;
    @Autowired
    private SensorModelService sensorModelService;
    @Autowired
    private MeasurementService measurementService;

    //frequency in seconds
    @Override
    public void generateMeasurementData(Date startDate, Date finishDate, long density, Object target) {
        density *= 1000;
        List<Measurement> measurements = new ArrayList<>();
        for (long i = startDate.getTime(); i < finishDate.getTime(); i += density) {
            if (target instanceof Pipe) {
                measurements.addAll(generateMeasurementDataForPipe((Pipe) target, generateStartTemperature(), new Date(i)));
            } else if (target instanceof House) {
                measurements.addAll(generateMeasurementDataForHouse((House) target, generateStartTemperature(), new Date(i)));
            }
        }
        measurementService.createButch(measurements);
    }


    private double generateStartTemperature() {
        return generateRandomValue(MIN_START_TEMPERATURE_RANGE, MAX_START_TEMPERATURE_RANGE);
    }

    @Override
    public List<Measurement> generateMeasurementDataForHouse(House house, double startTemperature, Date timestamp) {
        List<Measurement> measurements = new ArrayList<>();
        for (Pipe pipe : house.getPipes()) {
            measurements.addAll(generateMeasurementDataForPipe(pipe, startTemperature, timestamp));
        }
        return measurements;
    }

    @Override
    public List<Measurement> generateMeasurementDataForPipe(Pipe pipe, double startTemperature, Date timestamp) {
        List<Measurement> measurements = new ArrayList<>();
        for (MeasurementSection section : pipe.getMeasurementSections()) {
            Measurement measurement = generateMeasurementDataForSection(section, timestamp, startTemperature);
            measurements.add(measurement);
            startTemperature = measurement.getOutputAdditionalValue();
        }
        return measurements;
    }


    private Measurement generateMeasurementDataForSection(MeasurementSection section, Date timestamp, double startTemperature) {
        Measurement measurement = new Measurement();
        double generatedValue = generateRandomValueForSensor(SensorType.ADDITIONAL_INPUT, startTemperature);
        measurement.setInputAdditionalValue(generatedValue);
        generatedValue = generateRandomValueForSensor(SensorType.INPUT, generatedValue);
        measurement.setInputValue(generatedValue);
        measurement.setFlowValue(generateRandomValueForFlowSensor());
        generatedValue = generateRandomValueForSensor(SensorType.OUTPUT, generatedValue);
        measurement.setOutputValue(generatedValue);
        generatedValue = generateRandomValueForSensor(SensorType.ADDITIONAL_OUTPUT, generatedValue);
        measurement.setOutputAdditionalValue(generatedValue);

        measurement.setMeasurementSection(section);
        measurement.setTimestamp(timestamp);
        return measurement;
    }

    private double generateRandomValueForSensor(SensorType type, double startTemperature) {
        double minRange = 0;
        double maxRange = 0;
        switch (type) {
            case ADDITIONAL_INPUT:
                minRange = startTemperature - DEFAULT_LOSS_ON_OVERLAP - DELTA_LOSS_ON_OVERLAP;
                maxRange = startTemperature - DEFAULT_LOSS_ON_OVERLAP + DELTA_LOSS_ON_OVERLAP;
                break;
            case INPUT:
                minRange = startTemperature - DEFAULT_LOSS_ON_PIPE - DELTA_LOSS_ON_PIPE;
                maxRange = startTemperature - DEFAULT_LOSS_ON_PIPE + DELTA_LOSS_ON_PIPE;
                break;
            case OUTPUT:
                minRange = startTemperature - DEFAULT_LOSS_ON_RADIATOR - DELTA_LOSS_ON_RADIATOR;
                maxRange = startTemperature - DEFAULT_LOSS_ON_RADIATOR + DELTA_LOSS_ON_RADIATOR;
                break;
            case ADDITIONAL_OUTPUT:
                minRange = startTemperature - DEFAULT_LOSS_ON_PIPE - DELTA_LOSS_ON_PIPE;
                maxRange = startTemperature - DEFAULT_LOSS_ON_PIPE + DELTA_LOSS_ON_PIPE;
                break;
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

    @Override
    public House generateMeasurementModules(House house, MeasurementModuleType moduleType) {

        TemperatureSensorModel temperatureSensorModel = sensorModelService.getTemperatureModelsList(0, 1).get(0);
        FlowSensorModel flowSensorModel = sensorModelService.getFlowModelsList(0, 1).get(0);
        if (house == null || temperatureSensorModel == null || flowSensorModel == null) {
            throw new IllegalArgumentException();
        }

        List<Apartment> apartments = generateApartments(DEFAULT_FLOORS, DEFAULT_APARTMENTS_ON_FLOOR, DEFAULT_ROOMS_QUANTITY, DEFAULT_ENTRANCES, house);
        List<Pipe> pipes = generatePipes(DEFAULT_ROOMS_QUANTITY * DEFAULT_ENTRANCES * DEFAULT_APARTMENTS_ON_FLOOR, house);
        Map<Pipe, List<Apartment>> appMap = mapPipes(apartments, pipes);
        for (Map.Entry<Pipe, List<Apartment>> entry : appMap.entrySet()) {
            generateMeasurementSections(entry.getKey(), moduleType, temperatureSensorModel, flowSensorModel, entry.getValue());
        }
        houseService.refresh(house);
        return house;
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

    private List<MeasurementSection> generateMeasurementSections(Pipe pipe, MeasurementModuleType measurementModuleType,
                                                                 TemperatureSensorModel temperatureSensorModel,
                                                                 FlowSensorModel flowSensorModel, List<Apartment> apartments) {
        List<MeasurementSection> measurementSections = new ArrayList<>();
        for (Apartment apartment : apartments) {
            MeasurementSection section = generateMeasurementSection(pipe, temperatureSensorModel, flowSensorModel, apartment);
            measurementSections.add(section);
        }
        return measurementSections;
    }

    private MeasurementSection generateMeasurementSection(Pipe pipe, TemperatureSensorModel temperatureSensorModel,
                                                          FlowSensorModel flowSensorModel, Apartment apartment) {
        MeasurementSection section = new MeasurementSection();
        section.setApartment(apartment);
        section.setPipe(pipe);
        measurementSectionFacade.createMeasurementSection(section, temperatureSensorModel, flowSensorModel, null);
        return section;
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
}
