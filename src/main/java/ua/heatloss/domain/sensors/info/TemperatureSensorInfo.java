package ua.heatloss.domain.sensors.info;

import ua.heatloss.domain.sensors.TemperatureSensor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class TemperatureSensorInfo {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double absoluteAccuracy;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "TEMPERATURE_SENSOR_ID")
    private TemperatureSensor temperatureSensor;

    public Double getAbsoluteAccuracy() {
        return absoluteAccuracy;
    }

    public void setAbsoluteAccuracy(Double absoluteAccuracy) {
        this.absoluteAccuracy = absoluteAccuracy;
    }

    public TemperatureSensor getTemperatureSensor() {
        return temperatureSensor;
    }

    public void setTemperatureSensor(TemperatureSensor temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }
}
