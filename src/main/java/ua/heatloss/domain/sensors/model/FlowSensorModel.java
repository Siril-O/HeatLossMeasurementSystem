package ua.heatloss.domain.sensors.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
        @NamedQuery(name = "FlowSensorModel.find", query = "SELECT sm FROM FlowSensorModel AS sm "),
        @NamedQuery(name = "FlowSensorModel.getTotalCount", query = "SELECT count(sm.id) FROM FlowSensorModel AS sm")
})


@Entity
@DiscriminatorValue(value = "FLOW")
public class FlowSensorModel extends SensorModel {

    private Double maxFlowRate;

    private Double minFlowRate;

    private Double absoluteAccuracy;

    public Double getMaxFlowRate() {
        return maxFlowRate;
    }

    public void setMaxFlowRate(Double maxFlowRate) {
        this.maxFlowRate = maxFlowRate;
    }

    public Double getMinFlowRate() {
        return minFlowRate;
    }

    public void setMinFlowRate(Double minFlowRate) {
        this.minFlowRate = minFlowRate;
    }

    public Double getAbsoluteAccuracy() {
        return absoluteAccuracy;
    }

    public void setAbsoluteAccuracy(Double absoluteAccuracy) {
        this.absoluteAccuracy = absoluteAccuracy;
    }

    @Override
    public String toString() {
        return "FlowSensorModel{" +
                "maxFlowRate=" + maxFlowRate +
                ", minFlowRate=" + minFlowRate +
                ", absoluteAccuracy=" + absoluteAccuracy +
                '}';
    }
}
