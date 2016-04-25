package ua.heatloss.domain.sensors;

import ua.heatloss.domain.sensors.info.TemperatureSensorInfo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TEMPERATURE_SENSOR")
public class TemperatureSensor extends AbstractSensor {


    @OneToOne(mappedBy = "temperatureSensor")
    private TemperatureSensorInfo sensorInfo;

    @Enumerated(value = EnumType.STRING)
    private TemperatureSensorType sensorType;


    public TemperatureSensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(TemperatureSensorType sensorType) {
        this.sensorType = sensorType;
    }
}
