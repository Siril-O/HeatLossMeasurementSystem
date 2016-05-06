package ua.heatloss.domain.sensors.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "SensorModel.find", query = "SELECT sm FROM SensorModel AS sm "),
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

    private String name;

    private String maker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }
}
