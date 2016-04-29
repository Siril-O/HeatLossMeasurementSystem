package ua.heatloss.web.controller;

import ua.heatloss.dao.AbstractDao;
import ua.heatloss.domain.sensors.model.SensorModel;
import ua.heatloss.services.SensorModelService;
import ua.heatloss.web.utils.PagingUtils;
import ua.heatloss.web.utils.WebConstants;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SensorModelController {

    private static final String SENSOR_MODEL = "sensorModel";

    @Autowired
    private SensorModelService sensorModelService;


    @RequestMapping(value = WebConstants.SLASH + WebConstants.CREATE)
    public String showAddSensorModelForm(Model model) {
        //house.setAddress(address);
        // model.addAttribute("house", house);
        return SENSOR_MODEL + "." + WebConstants.CREATE;
    }

    @RequestMapping(method = RequestMethod.GET, value = WebConstants.SLASH + WebConstants.LIST)
    public String getSensorsModels(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                   @RequestParam(value = "limit", required = false, defaultValue = AbstractDao.DEFAULT_LIMIT_STRING) Integer limit,
                                   Model model) {
        final List<SensorModel> sensorModels = sensorModelService.getList(offset, limit);
        Long total = sensorModelService.getTotalResultCount();

        PagingUtils.preparePaging(offset, limit, total, model);
        //LOG.debug("Offset:" + offset + " Limit:" + limit + " Total:" + total + " pagesNumber:" + paging + " Houses:" + houses);

        model.addAttribute("sensorModels", sensorModels);
        return SENSOR_MODEL + WebConstants.PAGED_LIST;
    }
}
