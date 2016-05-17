package ua.heatloss.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.domain.Pipe;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.MeasurementService;

import java.util.*;

@Component
public class DefaultHeatConsumptionCalculationService implements HeatConsumptionCalculationService {

    public static final Double SPECIFIC_HEAT_CAPACITY = 4.187;
    public static final int MILLIS_IN_SECOND = 1000;

    @Autowired
    private MeasurementService measurementService;

    @Override
    public Map<Date, Double> calculateSummaryHeatConsumptionPowerForMeasurementSection(AbstractMeasurementModule section,
                                                                                       Date startDate, Date endDate) {
        return calculateDataPowerConsumptionForSection(section, startDate, endDate);
    }


    @Override
    public double calculateRadiatorHeatPowerLoss(Measurement measurement) {
        double flowMeasure = measurement.getFlowValue();
        double inputMeasure = measurement.getInputValue();
        double outputMeasure = measurement.getOutputValue();

        double loss = flowMeasure * SPECIFIC_HEAT_CAPACITY * (inputMeasure - outputMeasure);
        return loss;
    }

    @Override
    public double calculateEnergyConsumedInPeriodForHouse(House house, Date startDate, Date endDate) {
        double result = 0;
        for (Pipe pipe : house.getPipes()) {
            for (AbstractMeasurementModule section : pipe.getMeasurementModules()) {
                result += (calculateEnergyConsumedInPeriodForMeasurementSection(section, startDate, endDate));
            }
        }
        return result;
    }

    @Override
    public Map<AbstractMeasurementModule, Double> calculateEnergyConsumedInPeriodForHouseBySections(House house, Date startDate, Date endDate) {

        Map<AbstractMeasurementModule, Double> result = new LinkedHashMap<>();
        for (Pipe pipe : house.getPipes()) {
            for (AbstractMeasurementModule section : pipe.getMeasurementModules()) {
                result.put(section, calculateEnergyConsumedInPeriodForMeasurementSection(section, startDate, endDate));
            }
        }
        return result;
    }

    @Override
    public double calculateEnergyConsumedInPeriodForMeasurementSection(AbstractMeasurementModule section, Date startDate, Date endDate) {
        Map<Date, Double> power = calculateDataPowerConsumptionForSection(section, startDate, endDate);
        long timePeriod = (endDate.getTime() - startDate.getTime()) / MILLIS_IN_SECOND;
        double average = calculateAveragePowerForSection(power);
        double energy = average * timePeriod / 1000;
        return energy;
    }

    private double calculateAveragePowerForSection(Map<Date, Double> power) {
        double summ = 0;
        for (double p : power.values()) {
            summ += p;
        }
        double average = summ / power.size();
        return average;
    }

    private Map<Date, Double> calculateDataPowerConsumptionForSection(AbstractMeasurementModule section, Date startDate, Date endDate) {
        List<Measurement> measurements = measurementService.findInTimePeriodForMeasurementSection(section, startDate, endDate);

        Map<Date, Measurement> measurementContexts = groupMeasurementsByDate(measurements);
        Map<Date, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Date, Measurement> entry : measurementContexts.entrySet()) {
            result.put(entry.getKey(), calculateRadiatorHeatPowerLoss(entry.getValue()));
        }
        return result;
    }


    private Map<Date, Measurement> groupMeasurementsByDate(List<Measurement> measurements) {
        Map<Date, Measurement> measurementsByDate = new HashMap<>();
        for (Measurement measurement : measurements) {
            measurementsByDate.put(measurement.getTimestamp(), measurement);
        }
        return measurementsByDate;
    }
}
