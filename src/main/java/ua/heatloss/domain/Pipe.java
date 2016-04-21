package ua.heatloss.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class Pipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "objectid")
    private String id;
    @ManyToOne
    private House house;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
