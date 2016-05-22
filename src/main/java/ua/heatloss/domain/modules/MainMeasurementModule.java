package ua.heatloss.domain.modules;

import ua.heatloss.domain.House;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "MAIN")
public class MainMeasurementModule extends AbstractMeasurementModule {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID")
    private House house;

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
