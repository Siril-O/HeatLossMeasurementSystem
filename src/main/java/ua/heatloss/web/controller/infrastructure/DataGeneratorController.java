package ua.heatloss.web.controller.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.heatloss.domain.House;
import ua.heatloss.facades.DataGeneratingFacade;
import ua.heatloss.web.controller.AbstractController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("generate")
public class DataGeneratorController extends AbstractController {

    @Autowired
    private DataGeneratingFacade dataGeneratingFacade;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String generateDataModel(@RequestParam("houseId") House house) {
        dataGeneratingFacade.generateMeasurementModules(house);
        return "Successfully generated";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/measurements")
    @ResponseBody
    public String generateMeauseremtsDataForHouse(@RequestParam("houseId") House house,
                                                  @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date startDate,
                                                  @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern = "MM-dd-yyyy") Date finishDate) {
        long density = TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES);
        dataGeneratingFacade.generateHouseMeasurementData(startDate, finishDate, density, house, true);
        return "Successfully generated";
    }
}


