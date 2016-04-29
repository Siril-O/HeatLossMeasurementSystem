package ua.heatloss.domain.sensors.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "FLOW")
public class FlowSensorModel extends SensorModel {

    private Double maxFlowRate;

    private Double minFlowRate;

    private Integer absoluteAccuracy;

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

    public Integer getAbsoluteAccuracy() {
        return absoluteAccuracy;
    }

    public void setAbsoluteAccuracy(Integer absoluteAccuracy) {
        this.absoluteAccuracy = absoluteAccuracy;
    }
}
