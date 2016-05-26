package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.facades.ReportsFacade;
import ua.heatloss.services.HouseService;
import ua.heatloss.web.utils.PagingUtils;
import ua.heatloss.web.utils.PagingWrapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController extends AbstractController {

    @Autowired
    private ReportsFacade reportsFacade;
    @Autowired
    private HouseService houseService;


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String indexHandler(@RequestParam(value = "houseId", defaultValue = "1") House house,
                               @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                               @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                               Model model) {
        return showHousePowerLossReport(house,startDate,endDate, model);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/energy")
    public String builReportForPowerOfHeatConsumptionForHouse(@RequestParam(value = "houseId", defaultValue = "1") House house,
                                                              @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                                              @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                                                              Model model, PagingWrapper paging) {
        Map<AbstractMeasurementModule, Double> dataMap = reportsFacade.
                calculateEnergyConsumedInPeriodForHouseBySections(house, startDate, endDate);
        model.addAttribute("dataMap", dataMap);
        final List<House> houses = houseService.getList(paging.getOffset(), paging.getLimit());
        Long total = houseService.getTotalResultCount();
        PagingUtils.preparePaging(paging, total, model);
        model.addAttribute("houses", houses);
        return "report.paging.energyConsumptionOfHouseByPipes";
    }

    @RequestMapping(value = "power/house/loss")
    public String showHousePowerLossReport(@RequestParam(value = "houseId", defaultValue = "1") House house,
                                           @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                           @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                                           Model model) {
        final Map<Date, Double> chartData = reportsFacade.buildReportOfHousePowerLoss(house, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        return "report.houseHeatLoss";
    }

    @RequestMapping(value = "power/house/input")
    public String showHouseInputPowerReport(@RequestParam(value = "houseId", defaultValue = "1") House house,
                                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                            @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                                            Model model) {
        final Map<Date, Double> chartData = reportsFacade.buildReportOfInputHousePower(house, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        return "report.houseHeatLoss";
    }

    @RequestMapping(value = "power/house/consumed")
    public String showHouseConsumedPowerReport(@RequestParam(value = "houseId", defaultValue = "1") House house,
                                               @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                               @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                                               Model model) {
        final Map<Date, Double> chartData = reportsFacade.buildReportOfHouseConsumedPower(house, startDate, endDate);
        model.addAttribute("dataMap", chartData);
        return "report.houseHeatLoss";
    }
}