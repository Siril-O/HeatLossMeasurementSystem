package ua.heatloss.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.MeasurementService;
import ua.heatloss.services.helper.PowerToEnergyCalculator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class DefaultHeatConsumptionCalculationService implements HeatConsumptionCalculationService {

    public static final Double SPECIFIC_HEAT_CAPACITY = 4.187;

    @Autowired
    private MeasurementService measurementService;

    @Override
    public double calculateEnergyForMeasurementModule(AbstractMeasurementModule module,
                                                              Date startDate, Date endDate) {
        return PowerToEnergyCalculator.calculate(calculateModulePowerInTimePeriod(module, startDate, endDate));
    }

    @Override
    public Map<Date, Double> calculateEnergyForMeasurementModuleByDays(AbstractMeasurementModule module, Date startDate, Date endDate) {
        final Map<Date, Double> energyByDays = new LinkedHashMap<>();

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        Date prevDate = start.getTime();
        start.add(Calendar.DATE, 1);
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            energyByDays.put(date, PowerToEnergyCalculator.calculate(calculateModulePowerInTimePeriod(module, prevDate, date)));
            prevDate = start.getTime();
        }
        return energyByDays;
    }
            @Override
    public Map<Date, Double> calculateModulePowerInTimePeriod(AbstractMeasurementModule module, Date startDate, Date endDate) {
        return calculateModulePowerByTime(module, startDate, endDate);
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
                result += PowerToEnergyCalculator.calculate(calculateModulePowerByTime(module, startDate, endDate));
            }
        }
        return result;
    }


    @Override
    public Map<AbstractMeasurementModule, Double> calculateConsumedEnergyByHouseByApartments(House house, Date startDate, Date endDate) {

        Map<AbstractMeasurementModule, Double> result = new LinkedHashMap<>();
        for (Pipe pipe : house.getPipes()) {
            for (AbstractMeasurementModule module : pipe.getApartmentMeasurementModules()) {
                result.put(module, PowerToEnergyCalculator.calculate(calculateModulePowerByTime(module, startDate, endDate)));
            }
        }
        return result;
    }

    private Map<Date, Double> calculateModulePowerByTime(AbstractMeasurementModule module, Date startDate, Date endDate) {
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


    private double calculatePowerLosses(LossContext context) {
        double mainPowerConsumption = calculatePowerConsumptionForMeasurement(context.getMainMeasurement());
        double consumedPowerByCustomers = 0;
        for (Measurement measurement : context.getPipeMeasurements()) {
            consumedPowerByCustomers += calculatePowerConsumptionForMeasurement(measurement);
        }
        return mainPowerConsumption - consumedPowerByCustomers;
    }

    private double calculatePowerConsumption(LossContext context) {
        double consumedPowerByCustomers = 0;
        for (Measurement measurement : context.getPipeMeasurements()) {
            consumedPowerByCustomers += calculatePowerConsumptionForMeasurement(measurement);
        }
        return consumedPowerByCustomers;
    }

    private Map<Date, Double> calculatePowerLossesInTimePeriod(final Map<Date, LossContext> lossContextsByDate) {
        Map<Date, Double> powerConsumptionByTime = new LinkedHashMap<>();
        for (Map.Entry<Date, LossContext> entry : lossContextsByDate.entrySet()) {
            powerConsumptionByTime.put(entry.getKey(), calculatePowerLosses(entry.getValue()));
        }
        return powerConsumptionByTime;
    }

    private Map<Date, Double> calculatePowerConsumptionInTimePeriod(final Map<Date, LossContext> lossContextsByDate) {
        Map<Date, Double> powerConsumptionByTime = new LinkedHashMap<>();
        for (Map.Entry<Date, LossContext> entry : lossContextsByDate.entrySet()) {
            powerConsumptionByTime.put(entry.getKey(), calculatePowerConsumption(entry.getValue()));
        }
        return powerConsumptionByTime;
    }

    private Map<Date, LossContext> groupLossContextByDate(final House house, final Date startDate, final Date endDate) {
        Map<Date, LossContext> lossContextByDates = new LinkedHashMap<>();
        List<Measurement> mainMeasurements = measurementService.findInTimePeriodForMeasurementModule
                (house.getMainMeasurementModule(), startDate, endDate);
        List<Measurement> pipesMeasurements = new ArrayList<>();
        for (Pipe pipe : house.getPipes()) {
            final List<Measurement> pipeMeasurements = measurementService.findInTimePeriodForMeasurementModule
                    (pipe.getPipeMeasurementModule(), startDate, endDate);
            if (pipeMeasurements.size() != mainMeasurements.size()) {
                throw new IllegalArgumentException("Measurement data does not match");
            }
            pipesMeasurements.addAll(pipeMeasurements);
        }
        Map<Date, List<Measurement>> pipeMeasurementsByDate = new LinkedHashMap<>();

        for (Measurement measurement : pipesMeasurements) {
            List<Measurement> measurementsForDate = pipeMeasurementsByDate.get(measurement.getTimestamp());
            if (measurementsForDate == null) {
                measurementsForDate = new ArrayList<>();
                pipeMeasurementsByDate.put(measurement.getTimestamp(), measurementsForDate);
            }
            measurementsForDate.add(measurement);
        }

        for (Measurement mainMeasurement : mainMeasurements) {
            lossContextByDates.put(mainMeasurement.getTimestamp(),
                    new LossContext(mainMeasurement, pipeMeasurementsByDate.get(mainMeasurement.getTimestamp())));
        }
        return lossContextByDates;
    }

    @Override
    public Map<Date, Double> calculatePowerLossByHouse(House house, Date startDate, Date endDate) {
        Map<Date, LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
        return calculatePowerLossesInTimePeriod(groupLossContextByDate);
    }

    @Override
    public double calculateEnergyLossOnHouse(House house, Date startDate, Date endDate) {
        Map<Date, LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
        Map<Date, Double> lossByDate = calculatePowerLossesInTimePeriod(groupLossContextByDate);
        return PowerToEnergyCalculator.calculate(lossByDate);
    }

    @Override
    public Map<Date, Double> calculateHouseConsumedPowerForTimePeriod(House house, Date startDate, Date endDate) {
        Map<Date, LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
        return calculatePowerConsumptionInTimePeriod(groupLossContextByDate);
    }


    @Override
    public Map<Date, Double> calculateApartmentPowerForTimePeriod(Apartment app, Date startDate, Date endDate) {
        List<Map<Date, Double>> listMaps = new ArrayList<>();
        for (ApartmentMeasurementModule module : app.getMeasurementModules()) {
            listMaps.add(calculateModulePowerInTimePeriod(module, startDate, endDate));
        }
        return sumDateMaps(listMaps);
    }

    private Map<Date, Double> sumDateMaps(List<Map<Date, Double>> listMaps) {
        checkMapSize(listMaps);
        Map<Date, Double> summ = new LinkedHashMap<>();
        for (Map<Date, Double> map : listMaps) {
            for (Map.Entry<Date, Double> entry : map.entrySet()) {
                if (summ.get(entry.getKey()) == null) {
                    summ.put(entry.getKey(), entry.getValue());
                } else {
                    summ.put(entry.getKey(), entry.getValue() + entry.getValue());
                }
            }
        }
        return summ;
    }

    private void checkMapSize(List<Map<Date, Double>> listMaps) {
        int size = 0;
        if (!listMaps.iterator().hasNext()) {
            size = listMaps.get(0).size();
        }
        for (Map<Date, Double> map : listMaps) {
            if (size != map.size()) {
                throw new IllegalStateException("Measurements does not match");
            }
        }
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
