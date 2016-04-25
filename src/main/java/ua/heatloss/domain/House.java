package ua.heatloss.domain;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 //   @Type(type = "objectid")
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

    public void setAddress(Address address) {
        this.address = address;
    }
}
