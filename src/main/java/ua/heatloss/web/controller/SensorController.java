package ua.heatloss.web.controller;

import ua.heatloss.dao.AbstractDao;
import ua.heatloss.domain.sensors.Sensor;
import ua.heatloss.services.SensorService;
import ua.heatloss.web.utils.PagingUtils;
import ua.heatloss.web.utils.WebConstants;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/sensor")
@Controller
public class SensorController {

    private static final String SENSOR = "sensor";

    @Autowired
    private SensorService sensorService;


    @RequestMapping(value = WebConstants.SLASH + WebConstants.CREATE)
    public String showAddSensorForm(Model model) {
        //house.setAddress(address);
        // model.addAttribute("house", house);
        return SENSOR + "." + WebConstants.CREATE;
    }

    @RequestMapping(method = RequestMethod.GET, value = WebConstants.SLASH + WebConstants.LIST)
    public String getAllSensors(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                @RequestParam(value = "limit", required = false, defaultValue = AbstractDao.DEFAULT_LIMIT_STRING) Integer limit,
                                Model model) {
        final List<Sensor> sensors = sensorService.getList(offset, limit);
        Long total = sensorService.getTotalResultCount();

        PagingUtils.preparePaging(offset, limit, total, model);
        //LOG.debug("Offset:" + offset + " Limit:" + limit + " Total:" + total + " pagesNumber:" + paging + " Houses:" + houses);

        model.addAttribute("sensors", sensors);
        return SENSOR + WebConstants.PAGED_LIST;
    }
}