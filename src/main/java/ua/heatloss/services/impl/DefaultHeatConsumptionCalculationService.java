package ua.heatloss.services.impl;


import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.modules.MainMeasurementModule;
import ua.heatloss.domain.modules.PipeMeasurementModule;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.MeasurementService;
import ua.heatloss.services.helper.DatePeriod;
import ua.heatloss.services.helper.MeasurementCalculator;
import ua.heatloss.services.helper.PowerToEnergyCalculator;
import ua.heatloss.web.controller.dto.HouseReportDataEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        DatePeriod period = new DatePeriod(startDate, endDate);
        final Map<Date, Double> energyByDays = new LinkedHashMap<>();
        for (Date day : period.getDays()) {
            energyByDays.put(day,
                    PowerToEnergyCalculator.calculateInKJoule(calculatePowerInTimePeriod(target, day, DatePeriod.getNextDay(day))));
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
        List<LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
        return calculatePowerLossesInTimePeriod(groupLossContextByDate);
    }

    @Override
    public double calculateEnergyLossOnHouse(House house, Date startDate, Date endDate) {
        List<LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
        Map<Date, Double> lossByDate = calculatePowerLossesInTimePeriod(groupLossContextByDate);
        return PowerToEnergyCalculator.calculateInKJoule(lossByDate);
    }

    @Override
    public Map<Date, Double> calculateHouseConsumedPowerForTimePeriod(House house, Date startDate, Date endDate) {
        List<LossContext> groupLossContextByDate = groupLossContextByDate(house, startDate, endDate);
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

    private List<HouseReportDataEntry> calculatePowerInTimePeriod(final List<LossContext> lossContexts) {
        List<HouseReportDataEntry> reportDataEntries = new ArrayList<>();
        for (LossContext context : lossContexts) {
            HouseReportDataEntry data = new HouseReportDataEntry();
            data.setDate(context.getDate());
            data.setLoss(MeasurementCalculator.calculatePowerLosses(context));
            data.setConsumed(MeasurementCalculator.calculatePowerConsumedByCustomers(context));
            data.setInput(MeasurementCalculator.calculatePower(context.getMainMeasurement()));
            reportDataEntries.add(data);
        }
        return reportDataEntries;
    }

    private List<HouseReportDataEntry> calculateEnergyInTimePeriod(final List<LossContext> lossContexts) {
        List<HouseReportDataEntry> reportDataEntries = new ArrayList<>();
        for (LossContext context : lossContexts) {
            HouseReportDataEntry data = new HouseReportDataEntry();
            data.setDate(context.getDate());
            data.setLoss(MeasurementCalculator.calculatePowerLosses(context));
            data.setConsumed(MeasurementCalculator.calculatePowerConsumedByCustomers(context));
            data.setInput(MeasurementCalculator.calculatePower(context.getMainMeasurement()));
            reportDataEntries.add(data);
        }
        return reportDataEntries;
    }

    private Map<Date, Double> calculatePowerLossesInTimePeriod(final List<LossContext> lossContexts) {
        Map<Date, Double> powerConsumptionByTime = new LinkedHashMap<>();
        for (LossContext context : lossContexts) {
            powerConsumptionByTime.put(context.getDate(), MeasurementCalculator.calculatePowerLosses(context));
        }
        return powerConsumptionByTime;
    }

    private Map<Date, Double> calculatePowerConsumedByCustomersInTimePeriod(final List<LossContext> lossContexts) {
        Map<Date, Double> powerConsumptionByTime = new LinkedHashMap<>();
        for (LossContext context : lossContexts) {
            powerConsumptionByTime.put(context.getDate(), MeasurementCalculator.calculatePowerConsumedByCustomers(context));
        }
        return powerConsumptionByTime;
    }

    private List<LossContext> groupLossContextByDate(final House house, final Date startDate, final Date endDate) {
        List<LossContext> lossContexts = new ArrayList<>();
        List<Measurement> mainMeasurements = measurementService.findInTimePeriodForMeasurementModule
                (house.getMainMeasurementModule(), startDate, endDate);
        List<Measurement> pipesMeasurements = measurementService.findInTimePeriodForHousePipes(house.getPipes(), startDate, endDate);
        Map<Date, List<Measurement>> pipeMeasurementsByDate = new LinkedHashMap<>();

        for (Measurement measurement : pipesMeasurements) {
            List<Measurement> measurementsForDate = pipeMeasurementsByDate.get(measurement.getTimestamp());
            if (measurementsForDate == null) {
                measurementsForDate = new ArrayList<>();
                pipeMeasurementsByDate.put(measurement.getTimestamp(), measurementsForDate);
            }
            measurementsForDate.add(measurement);
        }

        lossContexts.addAll(mainMeasurements.stream().map(mainMeasurement ->
                new LossContext(mainMeasurement, pipeMeasurementsByDate.get(mainMeasurement.getTimestamp())))
                .collect(Collectors.toList()));
        return lossContexts;
    }

    @Override
    public List<HouseReportDataEntry> calculateHousePower(House house, Date startDate, Date endDate) {
        return calculatePowerInTimePeriod(groupLossContextByDate(house, startDate, endDate));
    }


    @Override
    public List<HouseReportDataEntry> calculateHouseEnergyInTimePeriod(House house, Date startDate, Date endDate) {
        DatePeriod period = new DatePeriod(startDate, endDate);
        final List<HouseReportDataEntry> energyByDays = new ArrayList<>();
        final List<Measurement> measurements = measurementService.findInTimePeriodForHouse(house, startDate, endDate);
        final List<Measurement> inTimePeriodForHousePipes = measurementService.findInTimePeriodForHousePipes(house.getPipes(), startDate, endDate);
        measurements.addAll(inTimePeriodForHousePipes);
        for (Date day : period.getDays()) {
            final Map<Date, List<Measurement>> measurementsByDate = measurements.stream().
                    filter(m -> DatePeriod.isDateInPeriod(m.getTimestamp(), day, DatePeriod.getNextDay(day)))
                    .collect(Collectors.groupingBy(Measurement::getTimestamp));

            final List<LossContext> lossContexts = new ArrayList<>();
            for (Map.Entry<Date, List<Measurement>> m : measurementsByDate.entrySet()) {
                Optional<Measurement> main = m.getValue().stream().filter(measure -> measure.getMeasurementModule()
                        instanceof MainMeasurementModule).findAny();
                if (main.isPresent()) {
                    LossContext lossContext = new LossContext(m.getKey(), main.get(), m.getValue().stream()
                            .filter(measure -> measure.getMeasurementModule() instanceof PipeMeasurementModule)
                            .collect(Collectors.toList()));
                    lossContexts.add(lossContext);
                }
            }
            final List<HouseReportDataEntry> powerInPeriod = calculatePowerInTimePeriod(lossContexts);
            HouseReportDataEntry energy = new HouseReportDataEntry();
            energy.setDate(day);
            energy.setLoss(PowerToEnergyCalculator.calculateInKJoule(powerInPeriod.stream().collect(Collectors.toMap(HouseReportDataEntry::getDate, HouseReportDataEntry::getLoss))));
            energy.setConsumed(PowerToEnergyCalculator.calculateInKJoule(powerInPeriod.stream().collect(Collectors.toMap(HouseReportDataEntry::getDate, HouseReportDataEntry::getConsumed))));
            energy.setInput(PowerToEnergyCalculator.calculateInKJoule(powerInPeriod.stream().collect(Collectors.toMap(HouseReportDataEntry::getDate, HouseReportDataEntry::getInput))));
            energyByDays.add(energy);
        }
        return energyByDays;
    }


    public static class LossContext {

        private Date date;
        private Measurement mainMeasurement;
        private List<Measurement> pipeMeasurements;

        public LossContext(Measurement mainMeasurement, List<Measurement> pipeMeasurements) {
            this.mainMeasurement = mainMeasurement;
            this.pipeMeasurements = pipeMeasurements;
            date = mainMeasurement.getTimestamp();
        }

        public LossContext(Date date, Measurement mainMeasurement, List<Measurement> pipeMeasurements) {
            this.date = date;
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

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
