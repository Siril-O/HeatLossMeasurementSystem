package ua.heatloss.domain.user;

import javax.persistence.*;


@NamedQueries({
        @NamedQuery(name = "User.find", query = "SELECT u FROM Measurement AS u"),
        @NamedQuery(name = "User.getTotalCount", query = "SELECT count(u.id) FROM User AS u"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User AS u WHERE u.email=:email"),
        @NamedQuery(name = "User.countWithEmail", query = "SELECT count(u.id) FROM User AS u WHERE u.email=:email")

})

@Entity
@Table(name = "USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
