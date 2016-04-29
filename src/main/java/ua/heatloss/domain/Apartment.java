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

    @ManyToOne
    @JoinColumn(name = "HOUSE_ID")
    private House house;

    @OneToMany(mappedBy = "appartament", fetch = FetchType.LAZY)
    private List<MeasurementModule> measurementModules;

    private Integer number;
    private String owner;
    private Integer rooms;


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

    public List<MeasurementModule> getMeasurementModules() {
        return measurementModules;
    }

    public void setMeasurementModules(List<MeasurementModule> measurementModules) {
        this.measurementModules = measurementModules;
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
}
