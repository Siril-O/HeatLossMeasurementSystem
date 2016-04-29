package ua.heatloss.domain.sensors.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "FLOW")
public class FlowSensorModel extends SensorModel {

    private Double maxFlowRate;

    public Double getMaxFlowRate() {
        return maxFlowRate;
    }

    public void setMaxFlowRate(Double maxFlowRate) {
        this.maxFlowRate = maxFlowRate;
    }
}
