package ua.heatloss.domain;

import ua.heatloss.domain.sensors.AbstractSensor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEASUREMENT_ID")
    private Long id;

    @Type(type = "timestamp")
    @Column(name = "TIME")
    private Date timestamp;

    @Column(name = "VALUE")
    private Double value;

    @ManyToOne
    @JoinColumn(name = "SENSOR_ID")
    private AbstractSensor sensor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public AbstractSensor getSensor() {
        return sensor;
    }

    public void setSensor(AbstractSensor sensor) {
        this.sensor = sensor;
    }
}
