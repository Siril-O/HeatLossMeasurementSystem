package ua.heatloss.domain.sensors.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
        @NamedQuery(name = "TemperatureSensorModel.find", query = "SELECT sm FROM TemperatureSensorModel AS sm "),
        @NamedQuery(name = "TemperatureSensorModel.getTotalCount", query = "SELECT count(sm.id) FROM TemperatureSensorModel AS sm")
})


@Entity
@DiscriminatorValue(value = "TEMP")
public class TemperatureSensorModel extends SensorModel {

    private Double minTemperature;

    private Double maxTemperature;

    private Double absoluteAccuracy;

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getAbsoluteAccuracy() {
        return absoluteAccuracy;
    }

    public void setAbsoluteAccuracy(Double absoluteAccuracy) {
        this.absoluteAccuracy = absoluteAccuracy;
    }


}
