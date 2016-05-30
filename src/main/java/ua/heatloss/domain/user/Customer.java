package ua.heatloss.domain.user;

import ua.heatloss.domain.Apartment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

    @ManyToOne
    @JoinColumn(name = "APARTMENT_ID")
    private Apartment apartment;

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
