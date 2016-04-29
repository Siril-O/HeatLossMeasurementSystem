package ua.heatloss.domain;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries(
        {
                @NamedQuery(name = "House.findHouses", query = "SELECT h FROM House AS h"),
                @NamedQuery(name = "House.findHousesTotalResultCount", query = "SELECT count(h.id) FROM House AS h"),
        }
)
@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "house", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Pipe> pipes;

    @OneToMany(mappedBy = "house", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Appartament> appartments;

    @Embedded
    private Address address;

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

    public List<Appartament> getAppartments() {
        return appartments;
    }

    public void setAppartments(List<Appartament> appartments) {
        this.appartments = appartments;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", pipes=" + (pipes == null ? "" : pipes) +
                ", appartments=" + (appartments == null ? "" : appartments) +
                ", address=" + address +
                '}';
    }
}
