package ua.heatloss.services.helper;

import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.sensors.SensorType;

import java.util.List;


public class MeasurementContext {

    private Measurement input;
    private Measurement output;
    private Measurement flow;
    private Measurement inputAdditional;
    private Measurement outputAdditional;

    public MeasurementContext() {
    }

    public MeasurementContext(List<Measurement> measurements) {
        populate(measurements);
    }

    public Measurement getInput() {
        return input;
    }

    public void setInput(Measurement input) {
        this.input = input;
    }

    public Measurement getOutput() {
        return output;
    }

    public void setOutput(Measurement output) {
        this.output = output;
    }

    public Measurement getFlow() {
        return flow;
    }

    public void setFlow(Measurement flow) {
        this.flow = flow;
    }

    public Measurement getInputAdditional() {
        return inputAdditional;
    }

    public void setInputAdditional(Measurement inputAdditional) {
        this.inputAdditional = inputAdditional;
    }

    public Measurement getOutputAdditional() {
        return outputAdditional;
    }

    public void setOutputAdditional(Measurement outputAdditional) {
        this.outputAdditional = outputAdditional;
    }

    protected void populate(List<Measurement> measurements) {
        for (Measurement measurement : measurements) {
            SensorType sensorType = measurement.getSensor().getSensorType();
            switch (sensorType) {
                case ADDITIONAL_INPUT:
                    inputAdditional = measurement;
                    break;
                case INPUT:
                    setInput(measurement);
                    break;
                case OUTPUT:
                    setOutput(measurement);
                    break;
                case ADDITIONAL_OUTPUT:
                    outputAdditional = measurement;
                    break;
                case FLOW:
                    setFlow(measurement);
                    break;
                default:
                    throw new IllegalArgumentException("wrong amount of measurements");
            }
        }
    }
}
