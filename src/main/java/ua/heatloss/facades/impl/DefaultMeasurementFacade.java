package ua.heatloss.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.MeasurementModuleType;
import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.domain.sensors.Sensor;
import ua.heatloss.facades.MeasurementFacade;
import ua.heatloss.services.MeasurementService;
import ua.heatloss.services.SensorService;
import ua.heatloss.web.controller.dto.MeasurementData;

import java.util.*;

@Component
public class DefaultMeasurementFacade implements MeasurementFacade {


    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private SensorService sensorService;

    @Override
    public void saveMeasurementData(MeasurementData measurementData) {
        Date date = new Date();
        Map<Long, Double> readings = measurementData.getReadings();
        if (readings.size() < MeasurementModuleType.STANDART.getSensorsQuantity()) {
            throw new IllegalArgumentException("Minimum amount of readings must be " + MeasurementModuleType.STANDART.getSensorsQuantity());
        }
        List<Measurement> measurements = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : readings.entrySet()) {
            Sensor sensor = sensorService.findById(entry.getKey());
            Measurement measurement = new Measurement(date, entry.getValue(), sensor);
            measurements.add(measurement);
            measurementService.create(measurement);
        }

        checkUniqueOfSensors(measurements);

        for (Measurement measurement : measurements) {
            measurementService.create(measurement);
        }

    }

    private void checkUniqueOfSensors(List<Measurement> measurements) {
        Set<Sensor> sensors = new HashSet<>();
        MeasurementSection measurementSection = measurements.get(0).getSensor().getMeasurementSection();

        for (Measurement measurement : measurements) {
            Sensor sensor = measurement.getSensor();
            if (sensor.getMeasurementSection() != measurementSection) {
                throw new IllegalArgumentException("Sensors must be from same Measurement Section");
            }
            sensors.add(sensor);
        }
        if (measurements.size() != sensors.size()) {
            throw new IllegalArgumentException("Dublicated Sensors");
        }
        if (MeasurementModuleType.EXTENDED == measurementSection.getModuleType()
                && sensors.size() != MeasurementModuleType.EXTENDED.getSensorsQuantity()) {
            throw new IllegalArgumentException("Minimum amount of readings must be "
                    + MeasurementModuleType.EXTENDED.getSensorsQuantity());
        }

    }
}
