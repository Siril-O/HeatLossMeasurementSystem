package ua.heatloss.domain.modules;

import ua.heatloss.domain.MeasurementModuleType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue(value = "RESIDENTIAL")
public class MeasurementModule extends AbstractMeasurementModule {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "TYPE")
    private MeasurementModuleType moduleType;

    public MeasurementModuleType getModuleType() {
        return moduleType;
    }

    public void setModuleType(MeasurementModuleType moduleType) {
        this.moduleType = moduleType;
    }
}
