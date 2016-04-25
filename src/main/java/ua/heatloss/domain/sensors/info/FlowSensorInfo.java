package ua.heatloss.domain.sensors.info;

import ua.heatloss.domain.sensors.FlowSensor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class FlowSensorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "FLOW_SENSOR_ID")
    private FlowSensor flowSensor;

    private Double maxFlowRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMaxFlowRate() {
        return maxFlowRate;
    }

    public void setMaxFlowRate(Double maxFlowRate) {
        this.maxFlowRate = maxFlowRate;
    }
}
