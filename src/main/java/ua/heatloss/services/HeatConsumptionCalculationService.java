package ua.heatloss.services;

import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.modules.AbstractMeasurementModule;

import java.util.Date;
import java.util.Map;


public interface HeatConsumptionCalculationService {


    double calculateConsumedEnergyForMeasurementModule(AbstractMeasurementModule module,
                                                       Date startDate, Date endDate);

    Map<Date, Double> calculateModulePowerConsumptionForTimePeriod(AbstractMeasurementModule module, Date startDate, Date endDate);

    double calculatePowerConsumptionForMeasurement(Measurement measurement);

    double calculateConsumedEnergyByHouseApartments(House house, Date startDate, Date endDate);

    Map<AbstractMeasurementModule, Double> calculateConsumedEnergyByHouseByApartments(House house, Date startDate, Date endDate);

    Map<Date, Double> calculatePowerLossByHouse(House house, Date startDate, Date endDate);

    double calculateEnergyLossOnHouse(House house, Date startDate, Date endDate);
}
