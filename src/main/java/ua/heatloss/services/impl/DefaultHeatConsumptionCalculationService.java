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
import ua.heatloss.services.helper.MeasurementCalculator;
import ua.heatloss.services.helper.PowerToEnergyCalculator;

import java.util.*;

@Component
public class DefaultHeatConsumptionCalculationService implements HeatConsumptionCalculationService {


    @Autowired
    private MeasurementService measurementService;

    @Override
    public double calculateEnergy(Object target, Date startDate, Date endDate) {
        return PowerToEnergyCalculator.calculateInKJoule(calculatePowerInTimePeriod(target, startDate, endDate));
    }

    @Override
    public Map<Date, Double> calculateEnergyByDays(Object target, Date startDate, Date endDate) {
        final Map<Date, Double> energyByDays = new LinkedHashMap<>();

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        Date prevDate = start.getTime();
        start.add(Calendar.DATE, 1);
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            energyByDays.put(date, PowerToEnergyCalculator.calculateInKJoule(calculatePowerInTimePeriod(target, prevDate, date)));
            prevDate = start.getTime();
        }
        return energyByDays;
    }


    @Override
    public Map<Date, Double> calculatePowerInTimePeriod(Object target, Date startDate, Date endDate) {

        if (target instanceof AbstractMeasurementModule) {
            return calculateModulePowerByTime((AbstractMeasurementModule) target, startDate, endDate);
        } else if (target instanceof Apartment) {
            return calculateApartmentPowerForTimePeriod((Apartment) target, startDate, endDate);
        }
        throw new IllegalArgumentException("Only AbstractMeasurementModule and Apartment allowed");
    }

    @Override
    public Map<Date, Double> calculatePowerLossOnHouse(House house, Date startDate, Date endDate) {
        Map<Date, LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
        return calculatePowerLossesInTimePeriod(groupLossContextByDate);
    }

    @Override
    public double calculateEnergyLossOnHouse(House house, Date startDate, Date endDate) {
        Map<Date, LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
        Map<Date, Double> lossByDate = calculatePowerLossesInTimePeriod(groupLossContextByDate);
        return PowerToEnergyCalculator.calculateInKJoule(lossByDate);
    }

    @Override
    public Map<Date, Double> calculateHouseConsumedPowerForTimePeriod(House house, Date startDate, Date endDate) {
        Map<Date, LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
        return calculatePowerConsumedByCustomersInTimePeriod(groupLossContextByDate);
    }


    private Map<Date, Double> calculateApartmentPowerForTimePeriod(Apartment app, Date startDate, Date endDate) {
        List<Measurement> measurements = new ArrayList<>();
        for (ApartmentMeasurementModule module : app.getMeasurementModules()) {
            measurements.addAll(measurementService.findInTimePeriodForMeasurementModule(module, startDate, endDate));
        }
        return calculatePowerForTimePeriod(groupMeasurementsByDate(measurements));
    }

    private Map<Date, Double> calculateModulePowerByTime(AbstractMeasurementModule module, Date startDate, Date endDate) {
        List<Measurement> measurements = measurementService.findInTimePeriodForMeasurementModule(module, startDate, endDate);
        return calculatePowerForTimePeriod(groupMeasurementsByDate(measurements));
    }

    private Map<Date, List<Measurement>> groupMeasurementsByDate(List<Measurement> measurements) {
        Map<Date, List<Measurement>> measurementsByDate = new LinkedHashMap<>();
        for (Measurement measurement : measurements) {
            List<Measurement> measurementsForDay = measurementsByDate.get(measurement.getTimestamp());
            if (measurementsForDay == null) {
                measurementsForDay = new ArrayList<>();
                measurementsByDate.put(measurement.getTimestamp(), measurementsForDay);
            }
            measurementsForDay.add(measurement);
        }
        return measurementsByDate;
    }

    private Map<Date, Double> calculatePowerForTimePeriod(final Map<Date, List<Measurement>> measurementByTime) {
        Map<Date, Double> powerConsumptionByTime = new HashMap<>();
        for (Map.Entry<Date, List<Measurement>> entry : measurementByTime.entrySet()) {
            double power = 0;
            for (Measurement measurement : entry.getValue()) {
                power += MeasurementCalculator.calculatePower(measurement);
            }
            powerConsumptionByTime.put(entry.getKey(), power);
        }
        return powerConsumptionByTime;
    }


    private Map<Date, Double> calculatePowerLossesInTimePeriod(final Map<Date, LossContext> lossContextsByDate) {
        Map<Date, Double> powerConsumptionByTime = new LinkedHashMap<>();
        for (Map.Entry<Date, LossContext> entry : lossContextsByDate.entrySet()) {
            powerConsumptionByTime.put(entry.getKey(), MeasurementCalculator.calculatePowerLosses(entry.getValue()));
        }
        return powerConsumptionByTime;
    }

    private Map<Date, Double> calculatePowerConsumedByCustomersInTimePeriod(final Map<Date, LossContext> lossContextsByDate) {
        Map<Date, Double> powerConsumptionByTime = new LinkedHashMap<>();
        for (Map.Entry<Date, LossContext> entry : lossContextsByDate.entrySet()) {
            powerConsumptionByTime.put(entry.getKey(), MeasurementCalculator.calculatePowerConsumedByCustomers(entry.getValue()));
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

    public static class LossContext {

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
