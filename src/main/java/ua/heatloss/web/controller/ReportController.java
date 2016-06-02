package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.user.Customer;
import ua.heatloss.facades.ReportsFacade;
import ua.heatloss.services.HouseService;
import ua.heatloss.services.UserService;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController extends AbstractController {

    @Autowired
    private ReportsFacade reportsFacade;
    @Autowired
    private HouseService houseService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "power/house/loss")
    public String showHousePowerLossReport(@RequestParam(value = "houseId") House house,
                                           @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                           @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                                           Model model) {
        final Map<Date, Double> chartData = reportsFacade.buildReportOfHousePowerLoss(house, startDate, endDate);
        model.addAttribute("dataMap", chartData);
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
        final Map<Date, Double> chartData = reportsFacade.buildPowerReportForMeasurementModule(module, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        return "report.modulePowerReport";
    }

    @RequestMapping(value = "energy/module")
    public String showModuleEnergyReport(@RequestParam(value = "moduleId", required = false) ApartmentMeasurementModule module,
                                         @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                         @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                         Model model) {
        if (module == null) {
            module = getApartmentIfCustomer().getMeasurementModules().get(0);
        }
        final Map<Date, Double> chartData = reportsFacade.buildEnergyReportForMeasurementModuleByDay(module, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        return "report.moduleEnergyReport";
    }

    @RequestMapping(value = "power/apartment")
    public String showApartmentPowerReport(@RequestParam(value = "apartmentId", required = false) Apartment apartment,
                                           @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                           @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                           Model model) {
        if (apartment == null) {
            apartment = getApartmentIfCustomer();
        }
        final Map<Date, Double> chartData = reportsFacade.buildPowerReportForApartment(apartment, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        return "report.apartmentPowerReport";
    }

    @RequestMapping(value = "energy/apartment")
    public String showApartmentEnergyReport(@RequestParam(value = "apartmentId", required = false) Apartment apartment,
                                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                            Model model) {
        if (apartment == null) {
            apartment = getApartmentIfCustomer();
        }
        final Map<Date, Double> chartData = reportsFacade.buildEnergyReportForApartmentByDay(apartment, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        return "report.apartmentEnergyReport";
    }


    private Apartment getApartmentIfCustomer() {
        Customer customer = userService.getCurrentCustomer();
        if (customer == null || customer.getApartment() == null || customer.getApartment().getMeasurementModules() == null ||
                customer.getApartment().getMeasurementModules().size() <= 0) {
            throw new IllegalArgumentException("no module selected");
        }
        return customer.getApartment();
    }
}