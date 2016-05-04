package ua.heatloss.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.heatloss.domain.*;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.facades.MeasurementSectionFacade;
import ua.heatloss.services.ApartmentService;
import ua.heatloss.services.HouseService;
import ua.heatloss.services.PipeService;
import ua.heatloss.services.SensorModelService;
import ua.heatloss.web.controller.AbstractController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("generate")
public class DataGeneratorController extends AbstractController {

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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public House generateDataModel(@RequestParam("houseId") House house) {

        int floors = 5;
        int apartmentsOnFloor = 2;
        int rooms = 2;
        int entrances = 2;

        TemperatureSensorModel temperatureSensorModel = sensorModelService.getTemperatureModelsList(0, 1).get(0);
        FlowSensorModel flowSensorModel = sensorModelService.getFlowModelsList(0, 1).get(0);
        if (house == null || temperatureSensorModel == null || flowSensorModel == null) {
            throw new IllegalArgumentException();
        }

        List<Apartment> apartments = generateApartments(floors, apartmentsOnFloor, rooms, entrances, house);
        List<Pipe> pipes = generatePipes(rooms * entrances * apartmentsOnFloor, house);
        Map<Pipe, List<Apartment>> appMap = mapPipes(apartments, pipes);
        for (Map.Entry<Pipe, List<Apartment>> entry : appMap.entrySet()) {
            generateMeasurementSections(entry.getKey(), MeasurementModuleType.STANDART, temperatureSensorModel, flowSensorModel, entry.getValue());
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
            MeasurementSection section = generateMeasurementSection(pipe, measurementModuleType,
                    temperatureSensorModel, flowSensorModel, apartment);
            measurementSections.add(section);
        }
        return measurementSections;
    }

    private MeasurementSection generateMeasurementSection(Pipe pipe, MeasurementModuleType measurementModuleType,
                                                          TemperatureSensorModel temperatureSensorModel,
                                                          FlowSensorModel flowSensorModel, Apartment apartment) {
        MeasurementSection section = new MeasurementSection();
        section.setModuleType(measurementModuleType);
        section.setApartment(apartment);
        section.setPipe(pipe);
        measurementSectionFacade.createMeasurementSection(section, temperatureSensorModel, flowSensorModel, null);
        return section;
    }

    private List<Pipe> generatePipes(int quantity, House house) {
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

    private List<Apartment> generateApartments(int floors, int apartmentsOnFloor, int rooms, int entrances, House house) {
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

    private List<House> generateHouses(int quantity) {
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
