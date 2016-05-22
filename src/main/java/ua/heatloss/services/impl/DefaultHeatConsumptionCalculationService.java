package ua.heatloss.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.MeasurementService;
import ua.heatloss.services.helper.PowerToEnergyCalculator;

import java.util.*;

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

//    @Override
//    public Map<Date, Double> calculateHousePowerMediatorLossForTimePeriod(House house, Date startDate, Date endDate) {
//        Map<AbstractMeasurementModule, Double> result = new LinkedHashMap<>();
//        Map<Date, Double> mainPowerByDate = calculateModulePowerConsumptionByTime(house.getMainMeasurementModule(),startDate, endDate);
//        for (Pipe pipe : house.getPipes()) {
//                calculateModulePowerConsumptionByTime(pipe.getPipeMeasurementModule(), startDate,endDate);
//
//        }
//        return result;
//    }

    @Override
    public double calculatePowerConsumptionForMeasurement(Measurement measurement) {
        double flowMeasure = measurement.getFlowValue();
        double inputMeasure = measurement.getInputValue();
        double outputMeasure = measurement.getOutputValue();

        double power = flowMeasure * SPECIFIC_HEAT_CAPACITY * (inputMeasure - outputMeasure);
        return power;
    }

    @Override
    public double calculateConsumedEnergyByHouseApartments(House house, Date startDate, Date endDate) {
        double result = 0;
        for (Pipe pipe : house.getPipes()) {
            for (AbstractMeasurementModule module : pipe.getApartmentMeasurementModules()) {
                result += PowerToEnergyCalculator.calculate(calculateModulePowerConsumptionByTime(module, startDate, endDate));
            }
        }
        return result;
    }


    @Override
    public Map<AbstractMeasurementModule, Double> calculateConsumedEnergyByHouseByApartments(House house, Date startDate, Date endDate) {

        Map<AbstractMeasurementModule, Double> result = new LinkedHashMap<>();
        for (Pipe pipe : house.getPipes()) {
            for (AbstractMeasurementModule module : pipe.getApartmentMeasurementModules()) {
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

//    private Map<Date, Measurement> summMeasurementsByDate(Map<Date,Double> map1, Map<Date,Double>map2 , BiFunction<Double, Double, Double> operation){
//        Map<Date, Measurement> result = new LinkedHashMap<>();
//        for(Map.Entry<Date,Double> entry: map1.entrySet()){
//            result.put(map1.get(entry.getKey()))
//        }
//    }
}
