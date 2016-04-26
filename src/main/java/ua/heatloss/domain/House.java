package ua.heatloss.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class House {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
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
                ", pipes=" + pipes +
                ", appartments=" + appartments +
                ", address=" + address +
                '}';
    }
}
