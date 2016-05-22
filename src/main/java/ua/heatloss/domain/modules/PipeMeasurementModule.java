package ua.heatloss.domain.modules;

import ua.heatloss.domain.Pipe;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "PIPE")
public class PipeMeasurementModule extends AbstractMeasurementModule {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RISER_PIPE_ID")
    private Pipe pipe;


    public Pipe getPipe() {
        return pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }
}
