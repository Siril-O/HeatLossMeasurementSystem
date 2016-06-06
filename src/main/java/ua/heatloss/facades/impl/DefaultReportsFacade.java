package ua.heatloss.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.facades.ReportsFacade;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.helper.DateHelper;
import ua.heatloss.web.controller.dto.HouseReportData;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class DefaultReportsFacade implements ReportsFacade {


    @Autowired
    private HeatConsumptionCalculationService calculationService;

    @Override
    public Map<Date, Double> buildReportOfModulePower(AbstractMeasurementModule section, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return calculationService.calculatePowerInTimePeriod(section, period.getStartDate(),
                period.getEndDate());
    }

    @Override
    public Map<Date, Double> buildReportOfHousePowerLoss(House house, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return calculationService.calculatePowerLossOnHouse(house, period.getStartDate(), period.getEndDate());
    }

    @Override
    public Map<Date, Double> buildReportOfInputHousePower(House house, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return calculationService.calculatePowerInTimePeriod(house.getMainMeasurementModule(),
                period.getStartDate(), period.getEndDate());

    }

    @Override
    public Map<Date, Double> buildReportOfHouseConsumedPower(House house, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return calculationService.calculateHouseConsumedPowerForTimePeriod(house, period.getStartDate(),
                period.getEndDate());
    }

    @Override
    public Map<Date, Double> buildPowerReportForMeasurementModule(AbstractMeasurementModule module, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return calculationService.calculatePowerInTimePeriod(module, period.getStartDate(),
                period.getEndDate());
    }


    @Override
    public Map<Date, Double> buildPowerReportForApartment(Apartment apartment, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return calculationService.calculatePowerInTimePeriod(apartment, period.getStartDate(),
                period.getEndDate());
    }


    @Override
    public Map<Date, Double> buildEnergyReportForMeasurementModuleByDay(AbstractMeasurementModule module, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return calculationService.calculateEnergyByDays(module, period.getStartDate(),
                period.getEndDate());
    }


    @Override
    public Map<Date, Double> buildEnergyReportForApartmentByDay(Apartment apartment, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return calculationService.calculateEnergyByDays(apartment, period.getStartDate(),
                period.getEndDate());
    }

    @Override
    public List<HouseReportData> buidHousePowerReport(House house, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        return calculationService.calculateHousePower(house, period.getStartDate(), period.getEndDate());
    }
}
