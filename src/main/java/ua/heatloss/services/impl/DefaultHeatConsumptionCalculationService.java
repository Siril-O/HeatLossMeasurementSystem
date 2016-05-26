package ua.heatloss.services.impl;


import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.MeasurementService;
import ua.heatloss.services.helper.PowerToEnergyCalculator;

import java.util.ArrayList;
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

        checkIsValidValue(measurement.getFlowValue(), "flow");
        checkIsValidValue(measurement.getInputValue(), "input Temperature");
        checkIsValidValue(measurement.getOutputValue(), "Output Temperature");

        double flowMeasure = measurement.getFlowValue();
        double inputMeasure = measurement.getInputValue();
        double outputMeasure = measurement.getOutputValue();

        double power = flowMeasure * SPECIFIC_HEAT_CAPACITY * (inputMeasure - outputMeasure);
        return power;
    }

    private void checkIsValidValue(Double value, String name) {
        if (value == null || value == 0) {
            throw new IllegalArgumentException("Empty value of " + name);
        }
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


    private double calculateLosses(LossContext context) {
        double mainPowerConsumption = calculatePowerConsumptionForMeasurement(context.getMainMeasurement());
        double consumedPowerByCustomers = 0;
        for (Measurement measurement : context.getPipeMeasurements()) {
            consumedPowerByCustomers += calculatePowerConsumptionForMeasurement(measurement);
        }
        return mainPowerConsumption - consumedPowerByCustomers;
    }

    private Map<Date, Double> calculateLossesPowerConsumptionForTimePeriod(final Map<Date, LossContext> lossContextsByDate) {
        Map<Date, Double> powerConsumptionByTime = new LinkedHashMap<>();
        for (Map.Entry<Date, LossContext> entry : lossContextsByDate.entrySet()) {
            powerConsumptionByTime.put(entry.getKey(), calculateLosses(entry.getValue()));
        }
        return powerConsumptionByTime;
    }

    private Map<Date, LossContext> groupLossContextByDate(final House house, final Date startDate, final Date endDate) {
        Map<Date, LossContext> lossContextByDates = new LinkedHashMap<>();
        List<Measurement> mainMeasurements = measurementService.findInTimePeriodForMeasurementModule
                (house.getMainMeasurementModule(), startDate, endDate);
        List<List<Measurement>> pipesMeasurements = new ArrayList<>();
        for (Pipe pipe : house.getPipes()) {
            final List<Measurement> pipeMeasurements = measurementService.findInTimePeriodForMeasurementModule
                    (pipe.getPipeMeasurementModule(), startDate, endDate);
            if (pipeMeasurements.size() != mainMeasurements.size()) {
                throw new IllegalArgumentException("Measurement data does not match");
            }
            pipesMeasurements.add(pipeMeasurements);
        }
        for (int i = 0; i < mainMeasurements.size(); i++) {
            lossContextByDates.put(mainMeasurements.get(i).getTimestamp(),
                    new LossContext(mainMeasurements.get(i), pipesMeasurements.get(i)));
        }

        return lossContextByDates;
    }

    @Override
    public Map<Date, Double> calculatePowerLossByHouse(House house, Date startDate, Date endDate) {
        Map<Date, LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
        return calculateLossesPowerConsumptionForTimePeriod(groupLossContextByDate);
    }

    @Override
    public double calculateEnergyLossOnHouse(House house, Date startDate, Date endDate) {
        Map<Date, LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
        Map<Date, Double> lossByDate = calculateLossesPowerConsumptionForTimePeriod(groupLossContextByDate);
        return PowerToEnergyCalculator.calculate(lossByDate);
    }

    private static class LossContext {

        private Measurement mainMeasurement;
        private List<Measurement> pipeMeasurements;

        public LossContext(Measurement mainMeasurement, List<Measurement> pipeMeasurements) {
            this.mainMeasurement = mainMeasurement;
            this.pipeMeasurements = pipeMeasurements;
        }

        public Measurement getMainMeasurement() {
            return mainMeasurement;
        }

        public void setMainMeasurement(Measurement mainMeasurement) {
            this.mainMeasurement = mainMeasurement;
        }

        public List<Measurement> getPipeMeasurements() {
            return pipeMeasurements;
        }

        public void setPipeMeasurements(List<Measurement> pipeMeasurements) {
            this.pipeMeasurements = pipeMeasurements;
        }
    }
}
