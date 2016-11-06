package ua.heatloss.facades.impl;

import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.*;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.facades.MeasurementModuleFacade;
import ua.heatloss.services.ApartmentService;
import ua.heatloss.services.MeasurementModuleService;
import ua.heatloss.services.PipeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.web.controller.mmodule.dto.CreateModuleRequest;

@Component
public class DefaultMeasurementModuleFacade implements MeasurementModuleFacade {

    @Autowired
    private MeasurementModuleService measurementModuleService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private PipeService pipeService;

    @Override
    public void createPipeMeasurementModule(PipeMeasurementModule module, TemperatureSensorModel temperatureSensorModel,
                                            FlowSensorModel flowSensorModel) {
        Pipe pipe = pipeService.findById(module.getPipe().getId());
        pipeService.refresh(pipe);
        module.setPipe(pipe);
        if (temperatureSensorModel != null && flowSensorModel != null) {
            module.setMeasurementsGroup(createMeasurementGroup(temperatureSensorModel, flowSensorModel));
        }
        measurementModuleService.create(module);
    }

    @Override
    public void createMainMeasurementModule(MainMeasurementModule module, TemperatureSensorModel temperatureSensorModel,
                                            FlowSensorModel flowSensorModel) {
        if (temperatureSensorModel != null && flowSensorModel != null) {
            module.setMeasurementsGroup(createMeasurementGroup(temperatureSensorModel, flowSensorModel));
        }
        measurementModuleService.create(module);
    }

    @Override
    public void createApartmentMeasurementModule(ApartmentMeasurementModule module, TemperatureSensorModel temperatureSensorModel, FlowSensorModel flowSensorModel) {
        Pipe pipe = pipeService.findById(module.getPipe().getId());
        pipeService.refresh(pipe);
        module.setPipe(pipe);

        manageOrdinalNumber(module);
        if (temperatureSensorModel != null && flowSensorModel != null) {
            module.setMeasurementsGroup(createMeasurementGroup(temperatureSensorModel, flowSensorModel));
        }
        measurementModuleService.create(module);
    }


    private MeasurementsGroup createMeasurementGroup(TemperatureSensorModel temperatureSensorModel, FlowSensorModel flowSensorModel) {
        MeasurementsGroup group = new MeasurementsGroup();
        group.setInput(temperatureSensorModel);
        group.setFlow(flowSensorModel);
        group.setOutput(temperatureSensorModel);
        return group;
    }

    private void manageOrdinalNumber(ApartmentMeasurementModule module) {
        List<ApartmentMeasurementModule> measurementModules = module.getPipe().getApartmentMeasurementModules();
        if (measurementModules == null || measurementModules.isEmpty()) {
            module.setOrdinalNumber(1);
        } else {
            module.setOrdinalNumber(measurementModules.size() + 1);
        }
    }

}
