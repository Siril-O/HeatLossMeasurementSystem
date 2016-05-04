package ua.heatloss.domain;

import org.hibernate.annotations.Type;
import ua.heatloss.domain.sensors.Sensor;

import javax.persistence.*;
import java.util.Date;

@NamedQueries(
        {
                @NamedQuery(name = "Measurement.find", query = "SELECT m FROM Measurement AS m"),
                @NamedQuery(name = "Measurement.findTotalResultCount", query = "SELECT count(m.id) FROM Measurement AS m"),
        }
)

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
    private Sensor sensor;

    public Measurement() {
    }

    public Measurement(Date timestamp, Double value, Sensor sensor) {
        this.timestamp = timestamp;
        this.value = value;
        this.sensor = sensor;
    }

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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
