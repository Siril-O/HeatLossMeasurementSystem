package ua.heatloss.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.sensors.Sensor;
import ua.heatloss.facades.MeasurementFacade;
import ua.heatloss.services.MeasurementService;
import ua.heatloss.services.SensorService;
import ua.heatloss.web.controller.dto.MeasurementData;

import java.util.Date;
import java.util.Map;

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
        for (Map.Entry<Long, Double> entry : readings.entrySet()) {
            Sensor sensor = sensorService.findById(entry.getKey());
            Measurement measurement = new Measurement(date, entry.getValue(), sensor);
            measurementService.create(measurement);
        }
    }
}
