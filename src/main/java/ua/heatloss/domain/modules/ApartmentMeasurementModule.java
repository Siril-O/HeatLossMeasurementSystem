package ua.heatloss.domain.modules;

import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.Pipe;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@DiscriminatorValue(value = "APARTMENT")
public class ApartmentMeasurementModule extends AbstractMeasurementModule {


    private Integer ordinalNumber;

    @ManyToOne
    @JoinColumn(name = "PIPE_ID")
    private Pipe pipe;

    @ManyToOne
    @JoinColumn(name = "APARTMENT_ID")
    private Apartment apartment;

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }


    public Pipe getPipe() {
        return pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }
}
