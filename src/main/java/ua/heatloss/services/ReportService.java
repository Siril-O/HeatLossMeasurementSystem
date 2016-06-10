package ua.heatloss.services;


import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.web.controller.dto.ApartmentReportData;
import ua.heatloss.web.controller.dto.HouseReportData;

import java.util.Date;

public interface ReportService {
    HouseReportData buildHouseEnergyReportDataInTimePeriod(House house, Date startDate, Date endDate);

    ApartmentReportData buildApartmentEnergyReportReportDataInTimePeriod(Apartment apartment, Date startDate, Date endDate);
}
