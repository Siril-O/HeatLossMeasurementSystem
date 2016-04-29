package ua.heatloss.domain;


import ua.heatloss.domain.sensors.Sensor;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    private Appartament appartament;

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

    public Appartament getAppartament() {
        return appartament;
    }

    public void setAppartament(Appartament appartament) {
        this.appartament = appartament;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
}
