package ua.heatloss.facades.impl;

import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.modules.MeasurementsGroup;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.facades.MeasurementSectionFacade;
import ua.heatloss.services.ApartmentService;
import ua.heatloss.services.MeasurementModuleService;
import ua.heatloss.services.PipeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultMeasurementSectionFacade implements MeasurementSectionFacade {

    @Autowired
    private MeasurementModuleService measurementModuleService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private PipeService pipeService;

    @Override
    public void createMeasurementModule(AbstractMeasurementModule module, TemperatureSensorModel temperatureSensorModel,
                                        FlowSensorModel flowSensorModel) {
        Pipe pipe = pipeService.findById(module.getPipe().getId());
        pipeService.refresh(pipe);
        module.setPipe(pipe);

        manageSectionOrdinalNumber(module);
        if (temperatureSensorModel != null && flowSensorModel != null) {
            module.setMeasurementsGroup(createMeasurementModule(temperatureSensorModel, flowSensorModel));
        }
        measurementModuleService.create(module);
    }

    @Override
    public void createApartmentMeasurementModule(ApartmentMeasurementModule module, TemperatureSensorModel temperatureSensorModel, FlowSensorModel flowSensorModel, Apartment newApartment) {
        Pipe pipe = pipeService.findById(module.getPipe().getId());
        pipeService.refresh(pipe);
        module.setPipe(pipe);

        manageSectionOrdinalNumber(module);
        if (temperatureSensorModel != null && flowSensorModel != null) {
            module.setMeasurementsGroup(createMeasurementModule(temperatureSensorModel, flowSensorModel));
        }
        saveApartment(module, newApartment);
        measurementModuleService.create(module);
    }


    private void saveApartment(ApartmentMeasurementModule module, Apartment newApartment) {
        if (newApartment != null) {
            module.setApartment(newApartment);
        } else {
            Apartment apartment = module.getApartment();
            if (apartment != null) {
                apartment.setHouse(module.getPipe().getHouse());
                apartmentService.create(apartment);
            }
        }
    }

    private MeasurementsGroup createMeasurementModule(TemperatureSensorModel temperatureSensorModel, FlowSensorModel flowSensorModel) {
        MeasurementsGroup module = new MeasurementsGroup();
        module.setInput(temperatureSensorModel);
        module.setFlow(flowSensorModel);
        module.setOutput(temperatureSensorModel);
        return module;
    }

    private void manageSectionOrdinalNumber(AbstractMeasurementModule module) {
        List<AbstractMeasurementModule> measurementModules = module.getPipe().getMeasurementModules();
        if (measurementModules == null || measurementModules.isEmpty()) {
            module.setOrdinalNumber(1);
        } else {
            module.setOrdinalNumber(measurementModules.size() + 1);
        }
    }

}
