package ua.heatloss.domain.sensors;

import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.MeasurementModule;
import ua.heatloss.domain.sensors.model.SensorModel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name = "Sensor.find", query = "SELECT s FROM Sensor AS s"),
        @NamedQuery(name = "Sensor.getTotalCount", query = "SELECT count(s.id) FROM Sensor AS s")
})

@Entity
@Table(name = "SENSOR")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SENSOR_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SENSOR_MODEL_ID")
    private SensorModel sensorModel;

    @ManyToOne
    @JoinColumn(name = "MEASUREMENT_MODULE_ID")
    private MeasurementModule measurementModule;


    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    private List<Measurement> measurement;


    private SensorType sensorType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasurementModule getMeasurementModule() {
        return measurementModule;
    }

    public void setMeasurementModule(MeasurementModule measurementModule) {
        this.measurementModule = measurementModule;
    }

    public SensorModel getSensorModel() {
        return sensorModel;
    }

    public void setSensorModel(SensorModel sensorModel) {
        this.sensorModel = sensorModel;
    }

    public List<Measurement> getMeasurement() {
        return measurement;
    }

    public void setMeasurement(List<Measurement> measurement) {
        this.measurement = measurement;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }
}

