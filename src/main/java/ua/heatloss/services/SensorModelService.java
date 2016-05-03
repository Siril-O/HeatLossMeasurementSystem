package ua.heatloss.services;

import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.SensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;

import java.util.List;


public interface SensorModelService extends CrudService<SensorModel> {

    List<TemperatureSensorModel> getTemperatureModelsList(final Integer startPosition, final Integer maxResults);

    List<FlowSensorModel> getFlowModelsList(final Integer startPosition, final Integer maxResults);

    Long getTemperatureModelsTotalCount();

    Long getFlowModelsTotalCount();

}
