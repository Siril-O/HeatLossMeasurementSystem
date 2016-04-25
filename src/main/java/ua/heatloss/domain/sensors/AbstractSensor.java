package ua.heatloss.domain.sensors;

import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.MeasurementModule;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SENSOR")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SENSOR_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEASUREMENT_MODULE_ID")
    private MeasurementModule measurementModule;


    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    private List<Measurement> measurement;

    public List<Measurement> getMeasurement() {
        return measurement;
    }

    public void setMeasurement(List<Measurement> measurement) {
        this.measurement = measurement;
    }


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

}
