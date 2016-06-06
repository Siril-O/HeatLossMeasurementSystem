package ua.heatloss.services;

import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.web.controller.dto.HouseReportData;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HeatConsumptionCalculationService {

    double calculateEnergy(Object target, Date startDate, Date endDate);

    Map<Date, Double> calculateEnergyByDays(Object target, Date startDate, Date endDate);

    Map<Date, Double> calculatePowerInTimePeriod(Object target, Date startDate, Date endDate);

    Map<Date, Double> calculatePowerLossOnHouse(House house, Date startDate, Date endDate);

    double calculateEnergyLossOnHouse(House house, Date startDate, Date endDate);

    Map<Date, Double> calculateHouseConsumedPowerForTimePeriod(House house, Date startDate, Date endDate);

    List<HouseReportData> calculateHousePower(House house, Date startDate, Date endDate);

    List<HouseReportData> calculateHouseEnergyInTimePeriod(House house, Date startDate, Date endDate);
}
