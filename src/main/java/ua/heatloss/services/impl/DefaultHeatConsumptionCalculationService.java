package ua.heatloss.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.sensors.Sensor;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.MeasurementService;
import ua.heatloss.services.helper.MeasurementContext;

import java.util.*;

@Component
public class DefaultHeatConsumptionCalculationService implements HeatConsumptionCalculationService {

    public static final Double SPECIFIC_HEAT_CAPACITY = 4.187;

    @Autowired
    private MeasurementService measurementService;

    @Override
    public Map<Date, Double> calculateSummaryHeatConsumptionPowerForMeasurementSection(MeasurementSection section,
                                                                                       Date startDate, Date endDate) {

        return calculateDataPowerConsumptionForSection(section, startDate, endDate);
    }


    @Override
    public Double calculateRadiatorHeatPowerLoss(MeasurementContext measurementContext) {
        Double flowMeasure = measurementContext.getFlow().getValue();
        Double inputMeasure = measurementContext.getInput().getValue();
        Double outputMeasure = measurementContext.getOutput().getValue();

        double loss = flowMeasure * SPECIFIC_HEAT_CAPACITY * (inputMeasure - outputMeasure);
        return loss;
    }

    @Override
    public Map<Date, Double> calculateDataForSummaryHosePowerReport(House house, Date startDate, Date endDate) {
        List<Map<Date, Double>> result = new ArrayList<>();
        for (Pipe pipe : house.getPipes()) {
            for (MeasurementSection section : pipe.getMeasurementSections()) {
                result.add(calculateDataPowerConsumptionForSection(section, startDate, endDate));
            }
        }
        return null;
    }

    private Double calculateEnergyConsumedInPeriodForMeasuremetSection(MeasurementSection section, Date startDate, Date endDate) {
        return 0D;
    }

    private Map<Date, Double> calculateDataPowerConsumptionForSection(MeasurementSection section, Date startDate, Date endDate) {
        List<Measurement> measurements = measurementService.findInTimePeriodForMeasurementSection(section, startDate, endDate);

        Map<Date, MeasurementContext> measurementContexts = createContextsForMeasurementSection(measurements);
        Map<Date, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Date, MeasurementContext> entry : measurementContexts.entrySet()) {
            result.put(entry.getKey(), calculateRadiatorHeatPowerLoss(entry.getValue()));
        }
        return result;
    }


    private Map<Date, MeasurementContext> createContextsForMeasurementSection(MeasurementSection section) {
        List<Measurement> measurements = new ArrayList<>();
        for (Sensor sensor : section.getSensors()) {
            measurements.addAll(sensor.getMeasurements());
        }
        return createContextsForMeasurementSection(measurements);
    }

    private Map<Date, MeasurementContext> createContextsForMeasurementSection(List<Measurement> measurements) {
        Map<Date, List<Measurement>> measurementsByDate = groupMeasurementsByDate(measurements);
        Map<Date, MeasurementContext> contextsByDate = new HashMap<>();
        for (Map.Entry<Date, List<Measurement>> entry : measurementsByDate.entrySet()) {
            MeasurementContext context = new MeasurementContext(entry.getValue());
            contextsByDate.put(entry.getKey(), context);
        }
        return contextsByDate;
    }

    private Map<Date, List<Measurement>> groupMeasurementsByDate(List<Measurement> measurements) {
        Map<Date, List<Measurement>> measurementsByDate = new HashMap<>();
        for (Measurement measurement : measurements) {
            Date timestamp = measurement.getTimestamp();
            List<Measurement> mForDate = measurementsByDate.get(timestamp);
            if (mForDate == null) {
                mForDate = new ArrayList<>();
                mForDate.add(measurement);
                measurementsByDate.put(timestamp, mForDate);
            } else {
                mForDate.add(measurement);
            }
        }
        return measurementsByDate;
    }
}
