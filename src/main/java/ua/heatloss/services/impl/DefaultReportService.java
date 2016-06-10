package ua.heatloss.services.impl;

import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.ReportService;
import ua.heatloss.services.helper.DateHelper;
import ua.heatloss.web.controller.dto.ApartmentReportData;
import ua.heatloss.web.controller.dto.HouseReportData;
import ua.heatloss.web.controller.dto.HouseReportDataEntry;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultReportService implements ReportService {

    @Autowired
    private HeatConsumptionCalculationService calculationService;

    @Override
    public HouseReportData buildHouseEnergyReportDataInTimePeriod(House house, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        final List<HouseReportDataEntry> chartData = calculationService.calculateHouseEnergyInTimePeriod(house, period.getStartDate(),
                period.getEndDate());
        return new HouseReportData(period.getStartDate(), period.getEndDate(), chartData);
    }

    @Override
    public ApartmentReportData buildApartmentEnergyReportReportDataInTimePeriod(Apartment apartment, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        final Map<Date, Double> energy = calculationService.calculateEnergyByDays(apartment, period.getStartDate(),
                period.getEndDate());
        return new ApartmentReportData(period.getStartDate(), period.getEndDate(), energy);
    }

}
