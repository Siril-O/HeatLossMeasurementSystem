package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.facades.ReportsFacade;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController extends AbstractController {

    @Autowired
    private ReportsFacade reportsFacade;


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String builReportForPowerOfHeatConsumptionForSection(@RequestParam(value = "sectionId", defaultValue = "1") MeasurementSection section,
                                                                @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                                                @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                                                                Model model) {
         Map<Date,Double> dataMap = reportsFacade.calculateDataForPowerReport(section, startDate, endDate);
        model.addAttribute("dataMap",dataMap);
        return "report.powerOfHeatConsumption";
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String builReportForPowerOfHeatConsumptionForHouse(@RequestParam(value = "houseId", required = false) MeasurementSection section,
                                                                @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                                                @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date endDate,
                                                                Model model) {

        return "powerConsumptionByHouses.jsp"
    }
}
