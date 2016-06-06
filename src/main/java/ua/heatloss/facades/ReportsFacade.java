package ua.heatloss.facades;


import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.web.controller.dto.HouseReportData;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReportsFacade {
    Map<Date, Double> buildReportOfModulePower(AbstractMeasurementModule section, Date startDate, Date endDate);

    Map<Date, Double> buildReportOfHousePowerLoss(House house, Date startDate, Date endDate);

    Map<Date, Double> buildReportOfInputHousePower(House house, Date startDate, Date endDate);

    Map<Date, Double> buildReportOfHouseConsumedPower(House house, Date startDate, Date endDate);

    Map<Date, Double> buildPowerReportForMeasurementModule(AbstractMeasurementModule module, Date startDate, Date endDate);

    Map<Date, Double> buildPowerReportForApartment(Apartment apartment, Date startDate, Date endDate);

    Map<Date, Double> buildEnergyReportForMeasurementModuleByDay(AbstractMeasurementModule module, Date startDate, Date endDate);

    Map<Date, Double> buildEnergyReportForApartmentByDay(Apartment apartment, Date startDate, Date endDate);

    List<HouseReportData> buidHousePowerReport(House house, Date startDate, Date endDate);
}
