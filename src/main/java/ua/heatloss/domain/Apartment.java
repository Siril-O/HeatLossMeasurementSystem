package ua.heatloss.domain;


import ua.heatloss.domain.modules.ApartmentMeasurementModule;

import javax.persistence.*;
import java.util.List;

@NamedQueries(
        {
                @NamedQuery(name = "Apartment.find", query = "SELECT m FROM Apartment AS m"),
                @NamedQuery(name = "Apartment.findTotalResultCount", query = "SELECT count(m.id) FROM Apartment AS m"),
                @NamedQuery(name = "Apartment.findByNumberLike", query = "SELECT a FROM Apartment AS a WHERE a.number LIKE CONCAT('%', :value, '%')"),


        }
)

@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY)
    private List<ApartmentMeasurementModule> measurementModules;

    @ManyToOne
    @JoinColumn(name = "HOUSE_ID")
    private House house;

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

    public List<ApartmentMeasurementModule> getMeasurementModules() {
        return measurementModules;
    }

    public void setMeasurementModules(List<ApartmentMeasurementModule> measurementModules) {
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", number=" + number +
                ", owner='" + owner + '\'' +
                ", rooms=" + rooms +
                ", floor=" + floor +
                ", measurementModules=" + measurementModules +
                '}';
    }
}
