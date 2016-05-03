package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.facades.MeasurementSectionFacade;
import ua.heatloss.services.MeasurementSectionService;

@Controller
@RequestMapping(value = "/measurementSection")
public class MeasurementSectionController extends AbstractController {

    @Autowired
    private MeasurementSectionFacade measurementSectionFacade;

    @Autowired
    private MeasurementSectionService measurementSectionService;

    @RequestMapping(method = RequestMethod.POST)
    public String createMeasurementSection(MeasurementSection measurementSection,
                                           Model model, RedirectAttributes redirectAttributes,
                                           @RequestParam("temperatureSensorModelId") TemperatureSensorModel temperatureSensorModel,
                                           @RequestParam("flowSensorModelId") FlowSensorModel flowSensorModel) {
        measurementSectionFacade.createMeasurementSection(measurementSection, temperatureSensorModel, flowSensorModel);
        return redirectToManagePipe(measurementSection.getPipe(), redirectAttributes);
    }

    private String redirectToManagePipe(Pipe pipe, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("pipeId", pipe.getId());
        return REDIRECT + SLASH + "pipe" + SLASH + MANAGE;
    }
}
