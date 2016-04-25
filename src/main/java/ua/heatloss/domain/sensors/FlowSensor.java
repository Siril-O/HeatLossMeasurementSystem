package ua.heatloss.domain.sensors;


import ua.heatloss.domain.sensors.info.FlowSensorInfo;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FLOW_SENSOR")
public class FlowSensor extends AbstractSensor {


    @OneToOne(mappedBy = "flowSensor")
    private FlowSensorInfo flowSensorInfo;


    public FlowSensorInfo getFlowSensorInfo() {
        return flowSensorInfo;
    }

    public void setFlowSensorInfo(FlowSensorInfo flowSensorInfo) {
        this.flowSensorInfo = flowSensorInfo;
    }
}
