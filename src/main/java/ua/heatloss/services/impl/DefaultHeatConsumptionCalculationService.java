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
    public static final int MILLIS_IN_SECOND = 1000;

    @Autowired
    private MeasurementService measurementService;

    @Override
    public Map<Date, Double> calculateSummaryHeatConsumptionPowerForMeasurementSection(MeasurementSection section,
                                                                                       Date startDate, Date endDate) {

        return calculateDataPowerConsumptionForSection(section, startDate, endDate);
    }


    @Override
    public double calculateRadiatorHeatPowerLoss(MeasurementContext measurementContext) {
        double flowMeasure = measurementContext.getFlow().getValue();
        double inputMeasure = measurementContext.getInput().getValue();
        double outputMeasure = measurementContext.getOutput().getValue();

        double loss = flowMeasure * SPECIFIC_HEAT_CAPACITY * (inputMeasure - outputMeasure);
        return loss;
    }

    @Override
    public double calculateEnergyConsumedInPeriodForHouse(House house, Date startDate, Date endDate) {
        double result = 0;
        for (Pipe pipe : house.getPipes()) {
            for (MeasurementSection section : pipe.getMeasurementSections()) {
                result += (calculateEnergyConsumedInPeriodForMeasurementSection(section, startDate, endDate));
            }
        }
        return result;
    }

    @Override
    public Map<MeasurementSection, Double> calculateEnergyConsumedInPeriodForHouseBySections(House house, Date startDate, Date endDate) {

        Map<MeasurementSection, Double> result = new LinkedHashMap<>();
        for (Pipe pipe : house.getPipes()) {
            for (MeasurementSection section : pipe.getMeasurementSections()) {
                result.put(section,calculateEnergyConsumedInPeriodForMeasurementSection(section, startDate, endDate));
            }
        }
        return result;
    }

    @Override
    public double calculateEnergyConsumedInPeriodForMeasurementSection(MeasurementSection section, Date startDate, Date endDate) {
        Map<Date, Double> power = calculateDataPowerConsumptionForSection(section, startDate, endDate);
        long timePeriod = (endDate.getTime() - startDate.getTime()) / MILLIS_IN_SECOND;
        double average = calculateAveragePowerForSecton(power);
        double energy = average * timePeriod /1000;
        return energy;
    }

    private double calculateAveragePowerForSecton(Map<Date, Double> power) {
        double summ = 0;
        for (double p : power.values()) {
            summ += p;
        }
        double average = summ / power.size();
        return average;
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
