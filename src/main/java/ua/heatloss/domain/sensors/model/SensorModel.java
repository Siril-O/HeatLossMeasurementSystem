package ua.heatloss.domain.sensors.model;

import ua.heatloss.domain.sensors.Sensor;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name = "SensorModel.findSensors", query = "SELECT sm FROM SensorModel AS sm"),
        @NamedQuery(name = "SensorModel.getTotalCount", query = "SELECT count(sm.id) FROM SensorModel AS sm")
})

@Entity
@Table(name = "SENSOR_MODEL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MODEL_TYPE")
public abstract class SensorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "sensorModel", fetch = FetchType.LAZY)
    private List<Sensor> sensors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
}
