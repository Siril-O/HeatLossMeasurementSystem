package ua.heatloss.domain.sensors.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "TEMP")
public class TemperatureSensorModel extends SensorModel {

    private Double absoluteAccuracy;

    public Double getAbsoluteAccuracy() {
        return absoluteAccuracy;
    }

    public void setAbsoluteAccuracy(Double absoluteAccuracy) {
        this.absoluteAccuracy = absoluteAccuracy;
    }

}
