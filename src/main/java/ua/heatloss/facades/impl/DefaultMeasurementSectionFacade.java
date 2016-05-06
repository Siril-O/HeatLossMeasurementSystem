package ua.heatloss.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.domain.*;
import ua.heatloss.domain.sensors.Sensor;
import ua.heatloss.domain.sensors.SensorType;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.facades.MeasurementSectionFacade;
import ua.heatloss.services.ApartmentService;
import ua.heatloss.services.MeasurementSectionService;
import ua.heatloss.services.PipeService;
import ua.heatloss.services.SensorService;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultMeasurementSectionFacade implements MeasurementSectionFacade {

    private static final int STANDART_QUANTITY = 3;
    private static final int EXTENDED_QUANTITY = 5;

    @Autowired
    private MeasurementSectionService measurementSectionService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private PipeService pipeService;

    @Override
    public void createMeasurementSection(MeasurementSection section, TemperatureSensorModel temperatureSensorModel,
                                         FlowSensorModel flowSensorModel, Apartment newApartment) {
        Pipe pipe = pipeService.findById(section.getPipe().getId());
        pipeService.refresh(pipe);
        section.setPipe(pipe);

        manageSectionOrdinalNumber(section);
        manageSensors(section, temperatureSensorModel, flowSensorModel);
        section.setSectionType(defineSectionType(section));

        saveApartment(section, newApartment);
        measurementSectionService.create(section);
    }

    private void saveApartment(MeasurementSection section, Apartment newApartment) {
        if (newApartment != null) {
            section.setApartment(newApartment);
        } else {
            Apartment apartment = section.getApartment();
            if (apartment != null) {
                apartment.setHouse(section.getPipe().getHouse());
                apartmentService.create(apartment);
            }
        }
    }

    private void manageSensors(MeasurementSection section, TemperatureSensorModel temperatureSensorModel, FlowSensorModel flowSensorModel) {
        if (section.getModuleType() != null) {
            List<Sensor> sensors = new ArrayList<>();
            populateSensorTypes(section, sensors);
            for (Sensor sensor : sensors) {
                sensor.setMeasurementSection(section);
                if (SensorType.FLOW != sensor.getSensorType()) {
                    sensor.setSensorModel(temperatureSensorModel);
                } else {
                    sensor.setSensorModel(flowSensorModel);
                }
            }
            section.setSensors(sensors);
        }
    }

    private void manageSectionOrdinalNumber(MeasurementSection section) {
        List<MeasurementSection> measurementSections = section.getPipe().getMeasurementSections();
        if (measurementSections == null || measurementSections.isEmpty()) {
            section.setOrdinalNumber(1);
        } else {
            section.setOrdinalNumber(measurementSections.size() + 1);
        }
    }

    private void populateSensorTypes(MeasurementSection section, List<Sensor> sensors) {
        if (MeasurementModuleType.STANDART == section.getModuleType()) {
            creteSensors(sensors, STANDART_QUANTITY);
            populateStandartMeasurementModule(sensors, 0);
        } else if (MeasurementModuleType.EXTENDED == section.getModuleType()) {
            creteSensors(sensors, EXTENDED_QUANTITY);
            sensors.get(0).setSensorType(SensorType.ADDITIONAL_INPUT);
            populateStandartMeasurementModule(sensors, 1);
            sensors.get(sensors.size() - 1).setSensorType(SensorType.ADDITIONAL_OUTPUT);
        }
    }

    private SectionType defineSectionType(MeasurementSection section) {
        Apartment apartment = section.getApartment();
        List<Sensor> sensors = section.getSensors();
        boolean apartmentPresent = apartment != null && apartment.getNumber() != null;
        boolean sensorsPresent = sensors != null && !sensors.isEmpty();
        if (!apartmentPresent && !sensorsPresent) {
            return SectionType.NOT_APARTMENT_WITHOUT_SENSOR;
        } else if (!apartmentPresent && sensorsPresent) {
            return SectionType.MAIN_PIPE_SENSOR;
        } else if (apartmentPresent && sensorsPresent) {
            return SectionType.APARTMENT_WITH_SENSOR;
        } else {
            return SectionType.APARTMENT_WITHOUT_SENSOR;
        }
    }

    private void populateStandartMeasurementModule(List<Sensor> sensors, int shift) {
        sensors.get(shift).setSensorType(SensorType.INPUT);
        sensors.get(shift + 1).setSensorType(SensorType.FLOW);
        sensors.get(shift + 2).setSensorType(SensorType.OUTPUT);
    }

    private void creteSensors(List<Sensor> sensors, int quantity) {
        for (int i = 0; i < quantity; i++) {
            Sensor sensor = new Sensor();
            sensor.setOrdinalNumber(i + 1);
            sensors.add(sensor);
        }
    }
}
