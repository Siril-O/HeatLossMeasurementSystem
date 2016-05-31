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
    public Map<Date, Double> buildReportOfModulePower(AbstractMeasurementModule section, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return consumptionCalculationService.calculateModulePowerInTimePeriod(section, period.getStartDate(),
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

    @Override
    public Map<Date, Double> buildReportOfHousePowerLoss(House house, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return consumptionCalculationService.calculatePowerLossByHouse(house, period.getStartDate(), period.getEndDate());
    }

    @Override
    public Map<Date, Double> buildReportOfInputHousePower(House house, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return consumptionCalculationService.calculateModulePowerInTimePeriod(house.getMainMeasurementModule(),
                period.getStartDate(), period.getEndDate());

    }

    @Override
    public Map<Date, Double> buildReportOfHouseConsumedPower(House house, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return consumptionCalculationService.calculateHouseConsumedPowerForTimePeriod(house, period.getStartDate(),
                period.getEndDate());
    }

    @Override
    public Map<Date, Double> buildPowerReportForMeasurementModule(AbstractMeasurementModule module, Date startDate, Date endDate){
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return consumptionCalculationService.calculateModulePowerInTimePeriod(module, period.getStartDate(),
                period.getEndDate());
    }

    @Override
    public Map<Date, Double> buildEnergyReportForMeasurementModuleByDay(AbstractMeasurementModule module, Date startDate, Date endDate){
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return consumptionCalculationService.calculateEnergyForMeasurementModuleByDays(module, period.getStartDate(),
                period.getEndDate());
    }

}
