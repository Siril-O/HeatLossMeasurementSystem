package ua.heatloss.services;

import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.MeasurementSection;

import java.util.Date;
import java.util.Map;


public interface HeatConsumptionCalculationService {


    Map<Date, Double> calculateSummaryHeatConsumptionPowerForMeasurementSection(MeasurementSection section, Date startDat, Date endDate);

    double calculateRadiatorHeatPowerLoss(Measurement measurement);

    double calculateEnergyConsumedInPeriodForHouse(House house, Date startDate, Date endDate);

    Map<MeasurementSection, Double> calculateEnergyConsumedInPeriodForHouseBySections(House house, Date startDate, Date endDate);

    double calculateEnergyConsumedInPeriodForMeasurementSection(MeasurementSection section, Date startDate, Date endDate);
}
