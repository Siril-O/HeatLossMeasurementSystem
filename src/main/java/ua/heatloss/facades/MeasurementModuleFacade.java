package ua.heatloss.facades;


import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.modules.MainMeasurementModule;
import ua.heatloss.domain.modules.PipeMeasurementModule;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;

public interface MeasurementModuleFacade {


    void createPipeMeasurementModule(PipeMeasurementModule module, TemperatureSensorModel temperatureSensorModel,
                                     FlowSensorModel flowSensorModel);

    void createMainMeasurementModule(MainMeasurementModule module, TemperatureSensorModel temperatureSensorModel,
                                     FlowSensorModel flowSensorModel);

    void createApartmentMeasurementModule(ApartmentMeasurementModule module, TemperatureSensorModel temperatureSensorModel,
                                          FlowSensorModel flowSensorModel);
}
