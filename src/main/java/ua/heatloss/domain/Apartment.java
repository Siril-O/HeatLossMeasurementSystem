package ua.heatloss.domain;


import javax.persistence.*;
import java.util.List;

@NamedQueries(
        {
                @NamedQuery(name = "Apartment.find", query = "SELECT m FROM Apartment AS m"),
                @NamedQuery(name = "Apartment.findTotalResultCount", query = "SELECT count(m.id) FROM Apartment AS m"),
        }
)

@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY)
    private List<MeasurementSection> measurementSections;

    private Integer number;
    private String owner;
    private Integer rooms;
    private Integer floor;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MeasurementSection> getMeasurementSections() {
        return measurementSections;
    }

    public void setMeasurementSections(List<MeasurementSection> measurementSections) {
        this.measurementSections = measurementSections;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", number=" + number +
                ", owner='" + owner + '\'' +
                ", rooms=" + rooms +
                ", floor=" + floor +
                ", measurementSections=" + measurementSections +
                '}';
    }
}
