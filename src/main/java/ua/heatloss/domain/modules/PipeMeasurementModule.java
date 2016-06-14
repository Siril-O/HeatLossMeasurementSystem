package ua.heatloss.domain.modules;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PIPE")
public class PipeMeasurementModule extends AbstractPipeMeasurementModule {

}
