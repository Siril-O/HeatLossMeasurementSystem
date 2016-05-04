package ua.heatloss.web.controller.dto;

import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;

import java.util.List;


public class MeasurementSectionContext {

    private List<TemperatureSensorModel> temperatureSensorModels;

    private List<FlowSensorModel> flowSensorModels;

    private Pipe pipe;

    public void setTemperatureSensorModels(List<TemperatureSensorModel> temperatureSensorModels) {
        this.temperatureSensorModels = temperatureSensorModels;
    }

    public List<FlowSensorModel> getFlowSensorModels() {
        return flowSensorModels;
    }

    public void setFlowSensorModels(List<FlowSensorModel> flowSensorModels) {
        this.flowSensorModels = flowSensorModels;
    }

    public Pipe getPipe() {
        return pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    public List<TemperatureSensorModel> getTemperatureSensorModels() {
        return temperatureSensorModels;
    }

    @Override
    public String toString() {
        return "MeasurementSectionContext{" +
                ", temperatureSensorModels=" + temperatureSensorModels +
                ", flowSensorModels=" + flowSensorModels +
                '}';
    }
}
