package ua.heatloss.services.impl;


import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.MeasurementService;
import ua.heatloss.services.helper.PowerToEnergyCalculator;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultHeatConsumptionCalculationService implements HeatConsumptionCalculationService {

    public static final Double SPECIFIC_HEAT_CAPACITY = 4.187;

    @Autowired
    private MeasurementService measurementService;

    @Override
    public double calculateConsumedEnergyForMeasurementModule(AbstractMeasurementModule module,
                                                              Date startDate, Date endDate) {
        return PowerToEnergyCalculator.calculate(calculateModulePowerConsumptionForTimePeriod(module, startDate, endDate));
    }

    @Override
    public Map<Date, Double> calculateModulePowerConsumptionForTimePeriod(AbstractMeasurementModule module, Date startDate, Date endDate) {
        return calculateModulePowerConsumptionByTime(module, startDate, endDate);
    }


    @Override
    public double calculatePowerConsumptionForMeasurement(Measurement measurement) {
        double flowMeasure = measurement.getFlowValue();
        double inputMeasure = measurement.getInputValue();
        double outputMeasure = measurement.getOutputValue();

        double power = flowMeasure * SPECIFIC_HEAT_CAPACITY * (inputMeasure - outputMeasure);
        return power;
    }

    @Override
    public double calculateConsumedEnergyByHouse(House house, Date startDate, Date endDate) {
        double result = 0;
        for (Pipe pipe : house.getPipes()) {
            for (AbstractMeasurementModule module : pipe.getMeasurementModules()) {
                result += PowerToEnergyCalculator.calculate(calculateModulePowerConsumptionByTime(module, startDate, endDate));
            }
        }
        return result;
    }


    @Override
    public Map<AbstractMeasurementModule, Double> calculateConsumedEnergyByHouseByModules(House house, Date startDate, Date endDate) {

        Map<AbstractMeasurementModule, Double> result = new LinkedHashMap<>();
        for (Pipe pipe : house.getPipes()) {
            for (AbstractMeasurementModule module : pipe.getMeasurementModules()) {
                result.put(module, PowerToEnergyCalculator.calculate(calculateModulePowerConsumptionByTime(module, startDate, endDate)));
            }
        }
        return result;
    }

    private Map<Date, Double> calculateModulePowerConsumptionByTime(AbstractMeasurementModule module, Date startDate, Date endDate) {
        List<Measurement> measurements = measurementService.findInTimePeriodForMeasurementModule(module, startDate, endDate);
        Map<Date, Measurement> measurementByDate = groupMeasurementsByDate(measurements);
        return calculateModulePowerConsumptionForTimePeriod(measurementByDate);
    }


    private Map<Date, Double> calculateModulePowerConsumptionForTimePeriod(final Map<Date, Measurement> measurementByTime) {
        Map<Date, Double> powerConsumptionByTime = new HashMap<>();
        for (Map.Entry<Date, Measurement> entry : measurementByTime.entrySet()) {
            powerConsumptionByTime.put(entry.getKey(), calculatePowerConsumptionForMeasurement(entry.getValue()));
        }
        return powerConsumptionByTime;
    }

    private Map<Date, Measurement> groupMeasurementsByDate(List<Measurement> measurements) {
        Map<Date, Measurement> measurementsByDate = new LinkedHashMap<>();
        for (Measurement measurement : measurements) {
            measurementsByDate.put(measurement.getTimestamp(), measurement);
        }
        return measurementsByDate;
    }

}
