package ua.heatloss.domain;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "objectid")
    private String id;

    @OneToMany(mappedBy = "house", cascade = CascadeType.PERSIST)
    private List<Pipe> pipes;

    @Embedded
    private Address address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
