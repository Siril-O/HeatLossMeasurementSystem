package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.heatloss.domain.Measurement;
import ua.heatloss.services.MeasurementService;

@Controller
@RequestMapping(value = "/measurement")
public class MeasurementController extends AbstractController {


    @Autowired
    private MeasurementService measurementService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void addMeasurementForModule(Measurement measurement) {
        measurementService.create(measurement);
    }

}
