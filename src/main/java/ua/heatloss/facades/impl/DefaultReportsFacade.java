package ua.heatloss.facades.impl;

import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.facades.ReportsFacade;
import ua.heatloss.services.HeatConsumptionCalculationService;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DefaultReportsFacade implements ReportsFacade {


    @Autowired
    private HeatConsumptionCalculationService consumptionCalculationService;

    @Override
    public Map<Date, Double> calculateDataForPowerReport(AbstractMeasurementModule section, Date startDate, Date endDate) {
        DatePeriod period = new DatePeriod(startDate, endDate);
        period.checkDates();
        return consumptionCalculationService.calculateModulePowerConsumptionForTimePeriod(section, period.startDate, period.endDate);
    }

    @Override
    public Map<AbstractMeasurementModule, Double> calculateEnergyConsumedInPeriodForHouseBySections(House house, Date startDate, Date endDate) {
        DatePeriod period = new DatePeriod(startDate, endDate);
        period.checkDates();
        Map<AbstractMeasurementModule, Double> result = consumptionCalculationService.calculateConsumedEnergyByHouseByModules(house, period.startDate, period.endDate);

        for (Map.Entry<AbstractMeasurementModule, Double> entry : result.entrySet()) {
            if (entry.getValue().isNaN()) {
                result.put(entry.getKey(), 0D);
            }
        }
        return result;
    }


    private static class DatePeriod {
        private static Calendar calendar = Calendar.getInstance();
        Date startDate;
        Date endDate;

        public DatePeriod(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        private void checkDates() {
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if (startDate == null || endDate == null) {
                endDate = calendar.getTime();
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
                startDate = calendar.getTime();
            }
        }
    }
}
