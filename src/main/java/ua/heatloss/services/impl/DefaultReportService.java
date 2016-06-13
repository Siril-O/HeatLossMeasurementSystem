package ua.heatloss.services.impl;

import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.ReportService;
import ua.heatloss.services.helper.DatePeriod;
import ua.heatloss.web.controller.dto.ApartmentReportData;
import ua.heatloss.web.controller.dto.HouseReportData;
import ua.heatloss.web.controller.dto.HouseReportDataEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultReportService implements ReportService {

    @Autowired
    private HeatConsumptionCalculationService calculationService;

    @Autowired
    private EnergyByDateCache energyByDateCache;

    @Override
    public HouseReportData buildHouseEnergyReportDataInTimePeriod(House house, Date startDate, Date endDate) {
        DateHelper.DatePeriod period = DateHelper.checkDates(startDate, endDate);
        final List<HouseReportDataEntry> chartData = calculationService.calculateHouseEnergyInTimePeriod(house, period.getStartDate(),
                period.getEndDate());
        return new HouseReportData(period.getStartDate(), period.getEndDate(), chartData);
    }

    @Override
    public ApartmentReportData buildApartmentEnergyReportReportDataInTimePeriod(Apartment apartment, Date startDate, Date endDate) {
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        Map<Date, Double> energy = energyByDateCache.getCachedValueForApartment(period, apartment);
        if (energy == null) {
            energy = calculationService.calculateEnergyByDays(apartment, period.getStartDate(),
                    period.getEndDate());
            energyByDateCache.addCachedValues(energy, apartment);
        }
        return new ApartmentReportData(period.getStartDate(), period.getEndDate(), energy);
    }

    @Override
    public ApartmentReportData buildPowerReportForApartment(Apartment apartment, Date startDate, Date endDate) {
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        Map<Date, Double> power = calculationService.calculatePowerInTimePeriod(apartment, period.getStartDate(),
                period.getEndDate());
        return new ApartmentReportData(period.getStartDate(), period.getEndDate(), power);
    }

}
