package ua.heatloss.facades;


import ua.heatloss.domain.House;
import ua.heatloss.domain.MeasurementSection;

import java.util.Date;
import java.util.Map;

public interface ReportsFacade {
    Map<Date, Double> calculateDataForPowerReport(MeasurementSection section, Date startDate, Date endDate);

    Map<MeasurementSection, Double> calculateEnergyConsumedInPeriodForHouseBySections(House house, Date startDate, Date endDate);
}
