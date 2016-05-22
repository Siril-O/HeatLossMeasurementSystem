package ua.heatloss.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.facades.ReportsFacade;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.helper.DateHelper;

import java.util.Date;
import java.util.Map;

@Controller
public class DefaultReportsFacade implements ReportsFacade {


    @Autowired
    private HeatConsumptionCalculationService consumptionCalculationService;

    @Override
    public Map<Date, Double> calculateDataForPowerReport(AbstractMeasurementModule section, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return consumptionCalculationService.calculateModulePowerConsumptionForTimePeriod(section, period.getStartDate(),
                period.getEndDate());
    }

    @Override
    public Map<AbstractMeasurementModule, Double> calculateEnergyConsumedInPeriodForHouseBySections(House house, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        Map<AbstractMeasurementModule, Double> result =
                consumptionCalculationService.calculateConsumedEnergyByHouseByApartments(house, period.getStartDate(),
                        period.getEndDate());

        for (Map.Entry<AbstractMeasurementModule, Double> entry : result.entrySet()) {
            if (entry.getValue().isNaN()) {
                result.put(entry.getKey(), 0D);
            }
        }
        return result;
    }


}
