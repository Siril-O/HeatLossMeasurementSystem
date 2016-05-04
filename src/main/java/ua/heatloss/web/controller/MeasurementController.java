package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.sensors.Sensor;
import ua.heatloss.facades.MeasurementFacade;
import ua.heatloss.services.MeasurementService;
import ua.heatloss.services.SensorService;
import ua.heatloss.web.controller.dto.MeasurementData;

import java.util.Date;

@Controller
@RequestMapping(value = "/measurement")
public class MeasurementController extends AbstractController {

    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private MeasurementFacade measurementFacade;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void addMeasurement(@RequestParam("value") Double value, @RequestParam("sensorId") Sensor sensor) {
        Measurement measurement = new Measurement(new Date(), value, sensor);
        measurementService.create(measurement);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/batch")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void addMeasurementsFromModule(@RequestBody MeasurementData measurementData) {
        measurementFacade.saveMeasurementData(measurementData);
    }

}
