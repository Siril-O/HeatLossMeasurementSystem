package ua.heatloss.domain;

import ua.heatloss.domain.sensors.model.SensorModel;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class MeasurementModule {

    @ManyToOne
    @JoinColumn(name = "INPUT_ADDITIONAL_SENSOR_MODEL_ID")
    private SensorModel inputAdditional;
    @ManyToOne
    @JoinColumn(name = "INPUT_SENSOR_MODEL_ID")
    private SensorModel input;
    @ManyToOne
    @JoinColumn(name = "FLOW_SENSOR_MODEL_ID")
    private SensorModel flow;
    @ManyToOne
    @JoinColumn(name = "OUTPUT_SENSOR_MODEL_ID")
    private SensorModel output;
    @ManyToOne
    @JoinColumn(name = "OUTPUT_ADDITIONAL_SENSOR_MODEL_ID")
    private SensorModel outputAdditional;

    public SensorModel getInputAdditional() {
        return inputAdditional;
    }

    public void setInputAdditional(SensorModel inputAdditional) {
        this.inputAdditional = inputAdditional;
    }

    public SensorModel getInput() {
        return input;
    }

    public void setInput(SensorModel input) {
        this.input = input;
    }

    public SensorModel getFlow() {
        return flow;
    }

    public void setFlow(SensorModel flow) {
        this.flow = flow;
    }

    public SensorModel getOutput() {
        return output;
    }

    public void setOutput(SensorModel output) {
        this.output = output;
    }

    public SensorModel getOutputAdditional() {
        return outputAdditional;
    }

    public void setOutputAdditional(SensorModel outputAdditional) {
        this.outputAdditional = outputAdditional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeasurementModule)) return false;

        MeasurementModule module = (MeasurementModule) o;

        if (inputAdditional != null ? !inputAdditional.equals(module.inputAdditional) : module.inputAdditional != null)
            return false;
        if (input != null ? !input.equals(module.input) : module.input != null) return false;
        if (flow != null ? !flow.equals(module.flow) : module.flow != null) return false;
        if (output != null ? !output.equals(module.output) : module.output != null) return false;
        return outputAdditional != null ? outputAdditional.equals(module.outputAdditional) : module.outputAdditional == null;

    }

    @Override
    public int hashCode() {
        int result = inputAdditional != null ? inputAdditional.hashCode() : 0;
        result = 31 * result + (input != null ? input.hashCode() : 0);
        result = 31 * result + (flow != null ? flow.hashCode() : 0);
        result = 31 * result + (output != null ? output.hashCode() : 0);
        result = 31 * result + (outputAdditional != null ? outputAdditional.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MeasurementModule{" +
                "inputAdditional=" + inputAdditional +
                ", input=" + input +
                ", flow=" + flow +
                ", output=" + output +
                ", outputAdditional=" + outputAdditional +
                '}';
    }
}
