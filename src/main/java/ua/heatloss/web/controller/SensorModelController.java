package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.SensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.services.SensorModelService;
import ua.heatloss.web.utils.PagingUtils;
import ua.heatloss.web.utils.WebConstants;

import java.util.List;

@Controller
@RequestMapping(value = "/sensorModel")
public class SensorModelController {

    private static final String SENSOR_MODEL = "sensorModel";

    @Autowired
    private SensorModelService sensorModelService;

    @RequestMapping(method = RequestMethod.POST, value = "/temp")
    public String createTemperatureSensorModel(TemperatureSensorModel sensorModel, Model model) {
        sensorModelService.create(sensorModel);
        return WebConstants.REDIRECT + WebConstants.SLASH + WebConstants.LIST;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/flow")
    public String createFlowSensorModel(FlowSensorModel sensorModel, Model model) {
        sensorModelService.create(sensorModel);
        return WebConstants.REDIRECT + WebConstants.SLASH + WebConstants.LIST;
    }


    @RequestMapping(value = WebConstants.SLASH + WebConstants.CREATE)
    public String showAddSensorModelForm(Model model) {
        TemperatureSensorModel tempSensorModel = new TemperatureSensorModel();
        FlowSensorModel flowSensorModel = new FlowSensorModel();
        model.addAttribute("flowSensorModel", flowSensorModel);
        model.addAttribute("tempSensorModel", tempSensorModel);
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
