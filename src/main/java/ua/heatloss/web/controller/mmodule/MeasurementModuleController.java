package ua.heatloss.web.controller.mmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.modules.MainMeasurementModule;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.facades.MeasurementModuleFacade;
import ua.heatloss.services.MeasurementModuleService;
import ua.heatloss.web.controller.AbstractController;
import ua.heatloss.web.controller.mmodule.dto.CreateModuleRequest;

@Controller
@RequestMapping(value = "/measurementModule")
public class MeasurementModuleController extends AbstractController {

    @Autowired
    private MeasurementModuleFacade measurementModuleFacade;

    @Autowired
    private MeasurementModuleService measurementModuleService;

    @RequestMapping(method = RequestMethod.POST)
    public String createMainMeasurementModule(MainMeasurementModule measurementModule, RedirectAttributes redirectAttributes,
                                              @RequestParam("temperatureSensorModelId") TemperatureSensorModel temperatureSensorModel,
                                              @RequestParam("flowSensorModelId") FlowSensorModel flowSensorModel) {
        measurementModuleFacade.createMainMeasurementModule(measurementModule, temperatureSensorModel, flowSensorModel);
        return redirectToManageHouse(measurementModule.getHouse(), redirectAttributes);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/apartment")
    public String createApartmentMeasurementModule(ApartmentMeasurementModule measurementModule, RedirectAttributes redirectAttributes, @RequestParam("temperatureSensorModelId") TemperatureSensorModel temperatureSensorModel,
                                                   @RequestParam("flowSensorModelId") FlowSensorModel flowSensorModel,
                                                   @RequestParam(value = "apartmentId") Apartment apartment) {
        measurementModule.setApartment(apartment);
        measurementModuleFacade.createApartmentMeasurementModule(measurementModule, temperatureSensorModel, flowSensorModel);
        return redirectToManagePipe(measurementModule.getPipe(), redirectAttributes);
    }


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = SLASH + MANAGE)
    public String manageMeasurementModule(@RequestParam("measurementModuleId") MainMeasurementModule measurementModule, Model model) {
        measurementModuleService.refresh(measurementModule);
        model.addAttribute("measurementModule", measurementModule);
        return "measurementModule" + "." + MANAGE;
    }

    private String redirectToManagePipe(Pipe pipe, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("pipeId", pipe.getId());
        return REDIRECT + SLASH + "pipe" + SLASH + MANAGE;
    }

}
