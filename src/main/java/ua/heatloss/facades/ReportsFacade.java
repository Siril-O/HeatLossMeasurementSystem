package ua.heatloss.facades;


import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.AbstractMeasurementModule;

import java.util.Date;
import java.util.Map;

public interface ReportsFacade {
    Map<Date, Double> calculateDataForPowerReport(AbstractMeasurementModule section, Date startDate, Date endDate);

    Map<AbstractMeasurementModule, Double> calculateEnergyConsumedInPeriodForHouseBySections(House house, Date startDate, Date endDate);
}
