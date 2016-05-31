package ua.heatloss.facades;


import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.AbstractMeasurementModule;

import java.util.Date;
import java.util.Map;

public interface ReportsFacade {
    Map<Date, Double> buildReportOfModulePower(AbstractMeasurementModule section, Date startDate, Date endDate);

    Map<AbstractMeasurementModule, Double> calculateEnergyConsumedInPeriodForHouseBySections(House house, Date startDate, Date endDate);

    Map<Date, Double> buildReportOfHousePowerLoss(House house, Date startDate, Date endDate);

    Map<Date, Double> buildReportOfInputHousePower(House house, Date startDate, Date endDate);

    Map<Date, Double> buildReportOfHouseConsumedPower(House house, Date startDate, Date endDate);

    Map<Date, Double> buildPowerReportForMeasurementModule(AbstractMeasurementModule module, Date startDate, Date endDate);

    Map<Date, Double> buildEnergyReportForMeasurementModuleByDay(AbstractMeasurementModule module, Date startDate, Date endDate);
}
