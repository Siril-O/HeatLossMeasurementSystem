package ua.heatloss.services;

import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.modules.AbstractMeasurementModule;

import java.util.Date;
import java.util.Map;


public interface HeatConsumptionCalculationService {


    Map<Date, Double> calculateSummaryHeatConsumptionPowerForMeasurementSection(AbstractMeasurementModule section, Date startDat, Date endDate);

    double calculateRadiatorHeatPowerLoss(Measurement measurement);

    double calculateEnergyConsumedInPeriodForHouse(House house, Date startDate, Date endDate);

    Map<AbstractMeasurementModule, Double> calculateEnergyConsumedInPeriodForHouseBySections(House house, Date startDate, Date endDate);

    double calculateEnergyConsumedInPeriodForMeasurementSection(AbstractMeasurementModule section, Date startDate, Date endDate);
}
