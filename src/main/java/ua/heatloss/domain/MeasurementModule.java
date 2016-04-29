package ua.heatloss.domain;


import ua.heatloss.domain.sensors.Sensor;

import java.util.List;

import javax.persistence.*;

@NamedQueries(
        {
                @NamedQuery(name = "MeasurementModule.find", query = "SELECT m FROM MeasurementModule AS m"),
                @NamedQuery(name = "MeasurementModule.findTotalResultCount", query = "SELECT count(m.id) FROM MeasurementModule AS m"),
        }
)

@Entity
public class MeasurementModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEASUREMENT_MODULE_ID")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "PIPE_ID")
    private Pipe pipe;

    @ManyToOne
    @JoinColumn(name = "APPARTAMENT_ID")
    private Apartment appartament;

    @OneToMany(mappedBy = "measurementModule", fetch = FetchType.EAGER)
    private List<Sensor> sensors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Pipe getPipe() {
        return pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    public Apartment getAppartament() {
        return appartament;
    }

    public void setAppartament(Apartment appartament) {
        this.appartament = appartament;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
}
