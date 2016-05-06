package ua.heatloss.services;

import ua.heatloss.domain.House;
import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.services.helper.MeasurementContext;

import java.util.Date;
import java.util.Map;


public interface HeatConsumptionCalculationService {


    Map<Date, Double> calculateSummaryHeatConsumptionPowerForMeasurementSection(MeasurementSection section, Date startDat, Date endDate);

    double calculateRadiatorHeatPowerLoss(MeasurementContext measurementContext);

    double calculateEnergyConsumedInPeriodForHouse(House house, Date startDate, Date endDate);

    Map<MeasurementSection, Double> calculateEnergyConsumedInPeriodForHouseBySections(House house, Date startDate, Date endDate);

    double calculateEnergyConsumedInPeriodForMeasurementSection(MeasurementSection section, Date startDate, Date endDate);
}
