package ua.heatloss.facades;


import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;

public interface MeasurementSectionFacade {

    void createMeasurementModule(AbstractMeasurementModule module, TemperatureSensorModel temperatureSensorModel,
                                 FlowSensorModel flowSensorModel);

    void createApartmentMeasurementModule(ApartmentMeasurementModule module, TemperatureSensorModel temperatureSensorModel,
                                          FlowSensorModel flowSensorModel, Apartment newApartment);
}
