package ua.heatloss.domain;


import ua.heatloss.domain.modules.ApartmentMeasurementModule;
import ua.heatloss.domain.user.Customer;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID")
    private House house;

    private Integer number;
    private String owner;
    private Integer rooms;
    private Integer floor;

    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY)
    private List<Customer> customers;

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


    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Apartment apartment = (Apartment) o;

        return !(id != null ? !id.equals(apartment.id) : apartment.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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
