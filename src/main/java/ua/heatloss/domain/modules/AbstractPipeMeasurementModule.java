package ua.heatloss.domain.modules;


import ua.heatloss.domain.Pipe;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public abstract class AbstractPipeMeasurementModule extends AbstractMeasurementModule {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PIPE_ID")
    private Pipe pipe;

    public Pipe getPipe() {
        return pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }
}
