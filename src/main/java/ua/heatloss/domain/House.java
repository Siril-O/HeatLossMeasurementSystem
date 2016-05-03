package ua.heatloss.domain;


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

    @OneToMany(mappedBy = "house", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Pipe> pipes;

    @Enumerated(EnumType.STRING)
    private PipeSystem pipeSystem;

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

    public PipeSystem getPipeSystem() {
        return pipeSystem;
    }

    public void setPipeSystem(PipeSystem pipeSystem) {
        this.pipeSystem = pipeSystem;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", pipes=" + (pipes == null ? "" : pipes) +
                ", address=" + address +
                '}';
    }
}
