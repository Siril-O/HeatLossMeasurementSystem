package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.heatloss.dao.MeasurementModuleDao;
import ua.heatloss.dao.SensorModelDao;
import ua.heatloss.domain.House;
import ua.heatloss.domain.MeasurementModuleType;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.modules.MainMeasurementModule;
import ua.heatloss.domain.modules.PipeMeasurementModule;
import ua.heatloss.services.HouseService;
import ua.heatloss.services.PipeService;
import ua.heatloss.web.controller.dto.MeasurementSectionContext;

import java.util.Optional;

@Controller
@RequestMapping(value = "/pipe")
public class PipeController extends AbstractController {

    public static final String PIPE = "pipe";

    @Autowired
    private PipeService pipeService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private MeasurementModuleDao measurementModuleDao;

    @Autowired
    private SensorModelDao sensorModelDao;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = SLASH + MANAGE)
    public String managePipe(@RequestParam("pipeId") Pipe pipe, Model model) {
        MeasurementSectionContext measurementSectionContext = new MeasurementSectionContext();
        pipeService.refresh(pipe);

        PipeMeasurementModule module = new PipeMeasurementModule();
        ApartmentMeasurementModule apmodule = new ApartmentMeasurementModule();
        module.setPipe(pipe);
        apmodule.setPipe(pipe);
        measurementSectionContext.setPipe(pipe);

        measurementSectionContext.setFlowSensorModels(sensorModelDao.getFlowModelsList(0, 20));
        measurementSectionContext.setTemperatureSensorModels(sensorModelDao.getTemperatureModelsList(0, 20));
        model.addAttribute("moduleTypes", MeasurementModuleType.values());
        model.addAttribute("measurementModule", module);
        model.addAttribute("apartmentMeasurementModule", module);
        model.addAttribute("sectionContext", measurementSectionContext);
        return PIPE + DOT + MANAGE;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createPipe(@RequestParam("houseId") House house, Model model, RedirectAttributes redirectAttributes) {
        Optional<Integer> max = house.getPipes().stream().map(Pipe::getOrdinalNumber).max(Integer::compareTo);
        Pipe pipe = new Pipe();
        if (max.isPresent()) {
            pipe.setOrdinalNumber(max.get() + 1);
        } else {
            pipe.setOrdinalNumber(1);
        }
        pipe.setHouse(house);
        pipeService.create(pipe);
        house.getPipes().add(pipe);
        houseService.update(house);
        return redirectToManageHouse(house, redirectAttributes);
    }

}
