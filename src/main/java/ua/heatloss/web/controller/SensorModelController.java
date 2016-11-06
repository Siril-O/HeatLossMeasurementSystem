package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.services.SensorModelService;
import ua.heatloss.web.utils.Paging;
import ua.heatloss.web.utils.PagingUtils;

import java.util.List;

@Controller
@RequestMapping(value = "/sensorModel")
public class SensorModelController extends AbstractController {

    private static final String SENSOR_MODEL = "sensorModel";
    public static final String TEMPERATURE = "Temperature";
    public static final String FLOW = "Flow";

    @Autowired
    private SensorModelService sensorModelService;

    @RequestMapping(method = RequestMethod.POST, value = SLASH + "temperature")
    public String createTemperatureSensorModel(TemperatureSensorModel sensorModel, Model model) {
        sensorModelService.create(sensorModel);
        return REDIRECT + SLASH + SENSOR_MODEL + SLASH + LIST + TEMPERATURE;
    }

    @RequestMapping(method = RequestMethod.POST, value = SLASH + "flow")
    public String createFlowSensorModel(FlowSensorModel sensorModel, Model model) {
        sensorModelService.create(sensorModel);
        return REDIRECT + SLASH + SENSOR_MODEL + SLASH + LIST + FLOW;
    }


    @RequestMapping(value = SLASH + CREATE)
    public String showAddSensorModelForm(Model model) {
        TemperatureSensorModel tempSensorModel = new TemperatureSensorModel();
        FlowSensorModel flowSensorModel = new FlowSensorModel();
        model.addAttribute("flowSensorModel", flowSensorModel);
        model.addAttribute("tempSensorModel", tempSensorModel);
        return SENSOR_MODEL + "." + CREATE;
    }

    @RequestMapping(method = RequestMethod.GET, value = SLASH + LIST + FLOW)
    public String getFlowSensorModels(Paging paging, Model model) {
        final List<FlowSensorModel> flowSensorModels = sensorModelService.getFlowModelsList(paging.getOffset(), paging.getLimit());
        Long total = sensorModelService.getFlowModelsTotalCount();
        PagingUtils.preparePaging(paging, total, model);
        model.addAttribute("sensorModels", flowSensorModels);
        return SENSOR_MODEL + "." + LIST + FLOW;
    }

    @RequestMapping(method = RequestMethod.GET, value = SLASH + LIST + TEMPERATURE)
    public String getTemperatureSensorModels(Paging paging, Model model) {
        final List<TemperatureSensorModel> temperatureSensorModels = sensorModelService.getTemperatureModelsList(paging.getOffset(), paging.getLimit());
        Long total = sensorModelService.getTemperatureModelsTotalCount();
        PagingUtils.preparePaging(paging, total, model);
        model.addAttribute("sensorModels", temperatureSensorModels);
        return SENSOR_MODEL + "." +  LIST + TEMPERATURE;
    }

    @RequestMapping(method = RequestMethod.GET, value = SLASH + LIST)
    public String getAllHouses(@RequestParam("measurementSectionId") AbstractMeasurementModule section, Model model) {
        model.addAttribute("measurementModule", section.getMeasurementsGroup());
        return HOUSE + LIST;
    }

}
