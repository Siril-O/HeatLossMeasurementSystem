package ua.heatloss.facades;


import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;

public interface MeasurementSectionFacade {

    void createMeasurementSection(MeasurementSection section, TemperatureSensorModel temperatureSensorModel,
                                  FlowSensorModel flowSensorModel);
}
