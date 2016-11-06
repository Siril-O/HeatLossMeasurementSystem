package ua.heatloss.domain.user;

import ua.heatloss.domain.Apartment;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APARTMENT_ID")
    private Apartment apartment;

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
