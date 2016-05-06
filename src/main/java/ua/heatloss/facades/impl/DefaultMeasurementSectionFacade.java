package ua.heatloss.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.MeasurementModule;
import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.facades.MeasurementSectionFacade;
import ua.heatloss.services.ApartmentService;
import ua.heatloss.services.MeasurementSectionService;
import ua.heatloss.services.PipeService;

import java.util.List;

@Component
public class DefaultMeasurementSectionFacade implements MeasurementSectionFacade {

    @Autowired
    private MeasurementSectionService measurementSectionService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private PipeService pipeService;

    @Override
    public void createMeasurementSection(MeasurementSection section, TemperatureSensorModel temperatureSensorModel,
                                         FlowSensorModel flowSensorModel, Apartment newApartment) {
        Pipe pipe = pipeService.findById(section.getPipe().getId());
        pipeService.refresh(pipe);
        section.setPipe(pipe);

        manageSectionOrdinalNumber(section);
        if (temperatureSensorModel != null && flowSensorModel != null) {
            section.setMeasurementModule(createMeasurementModule(temperatureSensorModel, flowSensorModel));
        }
        saveApartment(section, newApartment);
        measurementSectionService.create(section);
    }

    private void saveApartment(MeasurementSection section, Apartment newApartment) {
        if (newApartment != null) {
            section.setApartment(newApartment);
        } else {
            Apartment apartment = section.getApartment();
            if (apartment != null) {
                apartment.setHouse(section.getPipe().getHouse());
                apartmentService.create(apartment);
            }
        }
    }

    private MeasurementModule createMeasurementModule(TemperatureSensorModel temperatureSensorModel, FlowSensorModel flowSensorModel) {
        MeasurementModule module = new MeasurementModule();
        module.setInputAdditional(temperatureSensorModel);
        module.setInput(temperatureSensorModel);
        module.setFlow(flowSensorModel);
        module.setOutput(temperatureSensorModel);
        module.setOutputAdditional(temperatureSensorModel);
        return module;
    }

    private void manageSectionOrdinalNumber(MeasurementSection section) {
        List<MeasurementSection> measurementSections = section.getPipe().getMeasurementSections();
        if (measurementSections == null || measurementSections.isEmpty()) {
            section.setOrdinalNumber(1);
        } else {
            section.setOrdinalNumber(measurementSections.size() + 1);
        }
    }

}
