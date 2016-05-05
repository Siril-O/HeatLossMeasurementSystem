package ua.heatloss.services;

import ua.heatloss.domain.House;
import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.services.helper.MeasurementContext;

import java.util.Date;
import java.util.Map;


public interface HeatConsumptionCalculationService {


    Map<Date, Double> calculateSummaryHeatConsumptionPowerForMeasurementSection(MeasurementSection section, Date startDat, Date endDate);

    Double calculateRadiatorHeatPowerLoss(MeasurementContext measurementContext);

    Map<Date, Double> calculateDataForSummaryHosePowerReport(House house, Date startDate, Date endDate);
}
