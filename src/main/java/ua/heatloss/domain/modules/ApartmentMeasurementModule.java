package ua.heatloss.domain.modules;

import ua.heatloss.domain.Apartment;

import javax.persistence.*;


@Entity
@DiscriminatorValue(value = "APARTMENT")
public class ApartmentMeasurementModule extends AbstractPipeMeasurementModule {

    private Integer ordinalNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APARTMENT_ID")
    private Apartment apartment;

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }
}
