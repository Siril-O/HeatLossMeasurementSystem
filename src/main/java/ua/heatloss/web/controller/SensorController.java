package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.heatloss.domain.sensors.Sensor;
import ua.heatloss.services.SensorService;
import ua.heatloss.web.utils.PagingUtils;
import ua.heatloss.web.utils.PagingWraper;

import java.util.List;

@Controller
@RequestMapping(value = "/sensor")
public class SensorController extends AbstractController {

    private static final String SENSOR = "sensor";

    @Autowired
    private SensorService sensorService;


    @RequestMapping(value = SLASH + CREATE)
    public String showAddSensorForm(Model model) {
        return SENSOR + "." + CREATE;
    }

    @RequestMapping(method = RequestMethod.GET, value = SLASH + LIST)
    public String getAllSensors(PagingWraper paging, Model model) {
        final List<Sensor> sensors = sensorService.getList(paging.getOffset(), paging.getLimit());
        Long total = sensorService.getTotalResultCount();

        PagingUtils.preparePaging(paging, total, model);

        model.addAttribute("sensors", sensors);
        return SENSOR + PAGED_LIST;
    }
}