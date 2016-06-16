package ua.heatloss.web.controller;

import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.user.Customer;
import ua.heatloss.domain.user.Role;
import ua.heatloss.facades.ReportsFacade;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.HouseService;
import ua.heatloss.services.ReportService;
import ua.heatloss.services.UserService;
import ua.heatloss.services.helper.DatePeriod;
import ua.heatloss.web.controller.dto.ApartmentReportData;
import ua.heatloss.web.controller.dto.HouseReportData;
import ua.heatloss.web.controller.dto.HouseReportDataEntry;
import ua.heatloss.web.utils.Paging;
import ua.heatloss.web.utils.PagingUtils;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/report")
public class ReportController extends AbstractController {

    @Autowired
    private ReportsFacade reportsFacade;
    @Autowired
    private HouseService houseService;
    @Autowired
    private UserService userService;
    @Autowired
    private HeatConsumptionCalculationService calculationService;
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "power/house")
    public String showHousePowerReport(@RequestParam(value = "houseId") House house,
                                       @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                       @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                       Model model, Paging paging) {
        HouseReportData chartData = reportService.buildHousePowerReportData(house, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        populateDates(chartData.getStartDate(), chartData.getEndDate(), model);
        model.addAttribute("house", house);
        PagingUtils.prepareJSPaging(paging, (long) house.getApartments().size(), model);
        return "admin.report.housePower";
    }

    @RequestMapping(value = "energy/house")
    public String showHouseEnergyReport(@RequestParam(value = "houseId") House house,
                                        @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                        @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                        Model model, Paging paging) {
        final HouseReportData chartData = reportService.buildHouseEnergyReportDataInTimePeriod(house, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        populateDates(chartData.getStartDate(), chartData.getEndDate(), model);
        model.addAttribute("house", house);
        model.addAttribute("energySum", calculateTotalEnergy(chartData));
        PagingUtils.prepareJSPaging(paging, (long) house.getApartments().size(), model);
        return "admin.report.houseEnergy";
    }

    @RequestMapping(value = "power/apartment")
    public String showApartmentPowerReport(@RequestParam(value = "apartmentId", required = false) Apartment apartment,
                                           @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                           @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                           Model model, Paging paging) {
        if (apartment == null) {
            apartment = getApartmentIfCustomer();
        }
        final ApartmentReportData chartData = reportService.buildPowerReportForApartment(apartment, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        populateDates(chartData.getStartDate(), chartData.getEndDate(), model);
        model.addAttribute("apartment", apartment);
        PagingUtils.prepareJSPaging(paging, (long) apartment.getMeasurementModules().size(), model);
        return (userService.hasRole(Role.ROLE_EMPLOYEE) ? "admin." : "") + "report.apartmentPower";
    }

    @RequestMapping(value = "energy/apartment")
    public String showApartmentEnergyReport(@RequestParam(value = "apartmentId", required = false) Apartment apartment,
                                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                            Model model, Paging paging) {
        if (apartment == null) {
            apartment = getApartmentIfCustomer();
        }
        final ApartmentReportData chartData = reportService.buildApartmentEnergyReportDataInTimePeriod(apartment, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        populateDates(chartData.getStartDate(), chartData.getEndDate(), model);
        model.addAttribute("apartment", apartment);
        model.addAttribute("energySum", calculateTotalEnergy(chartData.getResult()));
        PagingUtils.prepareJSPaging(paging, (long) apartment.getMeasurementModules().size(), model);
        return (userService.hasRole(Role.ROLE_EMPLOYEE) ? "admin." : "") + "report.apartmentEnergy";
    }

    @RequestMapping(value = "power/house/loss")
    public String showHousePowerLossReport(@RequestParam(value = "houseId") House house,
                                           @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                           @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                                           Model model) {
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        final Map<Date, Double> chartData = calculationService.calculatePowerLossOnHouse(house, period.getStartDate(),
                period.getEndDate());
        model.addAttribute("dataMap", chartData);
        model.addAttribute("startDate", period.getStartDate());
        model.addAttribute("endDate", period.getEndDate());
        return "report.houseHeatLoss";
    }


    @RequestMapping(value = "power/house/input")
    public String showHouseInputPowerReport(@RequestParam(value = "houseId") House house,
                                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                            @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                                            Model model) {
        final Map<Date, Double> chartData = reportsFacade.buildReportOfInputHousePower(house, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        return "report.houseHeatLoss";
    }

    @RequestMapping(value = "power/house/consumed")
    public String showHouseConsumedPowerReport(@RequestParam(value = "houseId") House house,
                                               @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                               @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                                               Model model) {
        final Map<Date, Double> chartData = reportsFacade.buildReportOfHouseConsumedPower(house, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        return "report.houseHeatLoss";
    }

    @RequestMapping(value = "power/module")
    public String showModulePowerReport(@RequestParam(value = "moduleId", required = false) ApartmentMeasurementModule module,
                                        @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                        @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                        Model model) {
        if (module == null) {
            module = getApartmentIfCustomer().getMeasurementModules().get(0);
        }
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        final Map<Date, Double> chartData = reportService.buildModulePowerReportDataInTimePeriod(module, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        model.addAttribute("module", module);
        populateDates(period.getStartDate(), period.getEndDate(), model);
        return (userService.hasRole(Role.ROLE_EMPLOYEE) ? "admin." : "") + "report.modulePower";
    }

    @RequestMapping(value = "energy/module")
    public String showModuleEnergyReport(@RequestParam(value = "moduleId", required = false) ApartmentMeasurementModule module,
                                         @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                         @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                         Model model) {
        if (module == null) {
            module = getApartmentIfCustomer().getMeasurementModules().get(0);
        }
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        final Map<Date, Double> chartData = reportService.buildModuleEnergyReportDataInTimePeriod(module, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        model.addAttribute("module", module);
        model.addAttribute("energySum", calculateTotalEnergy(chartData));
        populateDates(period.getStartDate(), period.getEndDate(), model);
        return (userService.hasRole(Role.ROLE_EMPLOYEE) ? "admin." : "") + "report.moduleEnergy";
    }

    private Apartment getApartmentIfCustomer() {
        Customer customer = userService.getCurrentCustomer();
        if (customer == null || customer.getApartment() == null || customer.getApartment().getMeasurementModules() == null ||
                customer.getApartment().getMeasurementModules().size() <= 0) {
            throw new IllegalArgumentException("no module selected");
        }
        return customer.getApartment();
    }

    private void populateDates(final Date startDate, final Date endDate, final Model model) {
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
    }

    private HouseReportDataEntry calculateTotalEnergy(HouseReportData reportData) {
        HouseReportDataEntry summ = new HouseReportDataEntry();
        summ.setLoss(reportData.getReportEntries().stream().mapToDouble(HouseReportDataEntry::getLoss).sum());
        summ.setInput(reportData.getReportEntries().stream().mapToDouble(HouseReportDataEntry::getInput).sum());
        summ.setConsumed(reportData.getReportEntries().stream().mapToDouble(HouseReportDataEntry::getConsumed).sum());
        return summ;
    }

    private Double calculateTotalEnergy(final Map<Date, Double> energyMap) {
        return energyMap.values().stream().collect(Collectors.summingDouble(e -> e));
    }

}