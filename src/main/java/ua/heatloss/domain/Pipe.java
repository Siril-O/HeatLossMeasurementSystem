package ua.heatloss.domain;

import ua.heatloss.domain.modules.AbstractMeasurementModule;

import javax.persistence.*;
import java.util.List;

@NamedQueries(
        {
                @NamedQuery(name = "Pipe.find", query = "SELECT p FROM Pipe AS p"),
                @NamedQuery(name = "Pipe.findTotalResultCount", query = "SELECT count(p.id) FROM Pipe AS p"),
        }
)

@Entity
public class Pipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ordinalNumber;

    @ManyToOne
    @JoinColumn(name = "HOUSE_ID")
    private House house;

    @OneToMany(mappedBy = "pipe", fetch = FetchType.LAZY)
    private List<AbstractMeasurementModule> measurementModules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public List<AbstractMeasurementModule> getMeasurementModules() {
        return measurementModules;
    }

    public void setMeasurementModules(List<AbstractMeasurementModule> measurementModules) {
        this.measurementModules = measurementModules;
    }

    @Override
    public String toString() {
        return "Pipe{" +
                "id=" + id +
                ", ordinalNumber=" + ordinalNumber +
                ", house=" + (house != null ? house.getId() : "") +
                ", measurementModules=" + measurementModules +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pipe)) return false;

        Pipe pipe = (Pipe) o;

        if (id != null ? !id.equals(pipe.id) : pipe.id != null) return false;
        return ordinalNumber != null ? ordinalNumber.equals(pipe.ordinalNumber) : pipe.ordinalNumber == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ordinalNumber != null ? ordinalNumber.hashCode() : 0);
        return result;
    }
}
