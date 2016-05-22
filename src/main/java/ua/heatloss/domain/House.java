package ua.heatloss.domain;


import ua.heatloss.domain.modules.MainMeasurementModule;

import javax.persistence.*;
import java.util.List;

@NamedQueries(
        {
                @NamedQuery(name = "House.find", query = "SELECT h FROM House AS h"),
                @NamedQuery(name = "House.findTotalResultCount", query = "SELECT count(h.id) FROM House AS h"),
        }
)
@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "house", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Pipe> pipes;

    @Enumerated(EnumType.STRING)
    private PipeSystem pipeSystem;

    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER)
    private List<Apartment> apartments;

    @Embedded
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL)
    private MainMeasurementModule mainMeasurementModule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Pipe> getPipes() {
        return pipes;
    }

    public void setPipes(List<Pipe> pipes) {
        this.pipes = pipes;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PipeSystem getPipeSystem() {
        return pipeSystem;
    }

    public void setPipeSystem(PipeSystem pipeSystem) {
        this.pipeSystem = pipeSystem;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public MainMeasurementModule getMainMeasurementModule() {
        return mainMeasurementModule;
    }

    public void setMainMeasurementModule(MainMeasurementModule mainMeasurementModule) {
        this.mainMeasurementModule = mainMeasurementModule;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", pipes=" + (pipes == null ? "" : pipes) +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House)) return false;

        House house = (House) o;

        if (id != null ? !id.equals(house.id) : house.id != null) return false;
        return address != null ? address.equals(house.address) : house.address == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

}
