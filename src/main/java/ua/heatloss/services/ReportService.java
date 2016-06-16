package ua.heatloss.services;


import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.services.helper.DatePeriod;
import ua.heatloss.web.controller.dto.ApartmentReportData;
import ua.heatloss.web.controller.dto.HouseReportData;

import java.util.Date;
import java.util.Map;

public interface ReportService {
    HouseReportData buildHouseEnergyReportDataInTimePeriod(House house, Date startDate, Date endDate);

    HouseReportData buildHousePowerReportData(House house, Date startDate, Date endDate);

    ApartmentReportData buildApartmentEnergyReportDataInTimePeriod(Apartment apartment, Date startDate, Date endDate);

    ApartmentReportData buildPowerReportForApartment(Apartment apartment, Date startDate, Date endDate);

    Map<Date, Double> buildModuleEnergyReportDataInTimePeriod(AbstractMeasurementModule module, Date startDate, Date endDate);

    Map<Date, Double> buildModulePowerReportDataInTimePeriod(AbstractMeasurementModule module, Date startDate, Date endDate);

}
