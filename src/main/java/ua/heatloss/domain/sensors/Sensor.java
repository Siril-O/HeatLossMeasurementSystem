package ua.heatloss.domain.sensors;

import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.domain.sensors.model.SensorModel;

import javax.persistence.*;
import java.util.List;

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
    @JoinColumn(name = "MEASUREMENT_SECTION_ID")
    private MeasurementSection measurementSection;


    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    private List<Measurement> measurements;

    private Integer ordinalNumber;

    @Enumerated(value = EnumType.STRING)
    private SensorType sensorType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasurementSection getMeasurementSection() {
        return measurementSection;
    }

    public void setMeasurementSection(MeasurementSection measurementSection) {
        this.measurementSection = measurementSection;
    }

    public SensorModel getSensorModel() {
        return sensorModel;
    }

    public void setSensorModel(SensorModel sensorModel) {
        this.sensorModel = sensorModel;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", sensorModel=" + sensorModel +
                ", measurementSection=" + measurementSection +
                ", measurements=" + measurements +
                ", ordinalNumber=" + ordinalNumber +
                ", sensorType=" + sensorType +
                '}';
    }
}

