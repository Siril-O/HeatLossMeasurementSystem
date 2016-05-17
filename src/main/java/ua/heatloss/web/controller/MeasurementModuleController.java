package ua.heatloss.web.controller;

import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.modules.MeasurementModule;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.facades.MeasurementSectionFacade;
import ua.heatloss.services.MeasurementModuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/measurementModule")
public class MeasurementModuleController extends AbstractController {

    @Autowired
    private MeasurementSectionFacade measurementSectionFacade;

    @Autowired
    private MeasurementModuleService measurementModuleService;

    @RequestMapping(method = RequestMethod.POST)
    public String createMeasurementModule(MeasurementModule measurementModule,
                                          Model model, RedirectAttributes redirectAttributes,
                                          @RequestParam("temperatureSensorModelId") TemperatureSensorModel temperatureSensorModel,
                                          @RequestParam("flowSensorModelId") FlowSensorModel flowSensorModel) {
        measurementSectionFacade.createMeasurementModule(measurementModule, temperatureSensorModel, flowSensorModel);
        return redirectToManagePipe(measurementModule.getPipe(), redirectAttributes);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/apartment")
    public String createApartmentMeasurementModule(ApartmentMeasurementModule measurementModule,
                                                   Model model, RedirectAttributes redirectAttributes,
                                                   @RequestParam("temperatureSensorModelId") TemperatureSensorModel temperatureSensorModel,
                                                   @RequestParam("flowSensorModelId") FlowSensorModel flowSensorModel,
                                                   @RequestParam(value = "apartmentId") Apartment apartment) {

        measurementSectionFacade.createApartmentMeasurementModule(measurementModule, temperatureSensorModel, flowSensorModel, apartment);
        return redirectToManagePipe(measurementModule.getPipe(), redirectAttributes);
    }


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = SLASH + MANAGE)
    public String manageMeasurementModule(@RequestParam("measurementModuleId") MeasurementModule measurementModule, Model model) {
        measurementModuleService.refresh(measurementModule);
        model.addAttribute("measurementModule", measurementModule);
        return "measurementModule" + "." + MANAGE;
    }

    private String redirectToManagePipe(Pipe pipe, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("pipeId", pipe.getId());
        return REDIRECT + SLASH + "pipe" + SLASH + MANAGE;
    }
}
