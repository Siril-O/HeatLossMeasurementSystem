package ua.heatloss.domain.modules;

import ua.heatloss.domain.sensors.model.SensorModel;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class MeasurementsGroup {

    @ManyToOne
    @JoinColumn(name = "INPUT_SENSOR_MODEL_ID")
    private SensorModel input;
    @ManyToOne
    @JoinColumn(name = "FLOW_SENSOR_MODEL_ID")
    private SensorModel flow;
    @ManyToOne
    @JoinColumn(name = "OUTPUT_SENSOR_MODEL_ID")
    private SensorModel output;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasurementsGroup that = (MeasurementsGroup) o;

        if (input != null ? !input.equals(that.input) : that.input != null) return false;
        if (flow != null ? !flow.equals(that.flow) : that.flow != null) return false;
        return !(output != null ? !output.equals(that.output) : that.output != null);

    }

    @Override
    public int hashCode() {
        int result = input != null ? input.hashCode() : 0;
        result = 31 * result + (flow != null ? flow.hashCode() : 0);
        result = 31 * result + (output != null ? output.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MeasurementsGroup{" +
                "input=" + input +
                ", flow=" + flow +
                ", output=" + output +
                '}';
    }
}
