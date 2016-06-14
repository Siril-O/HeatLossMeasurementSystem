package ua.heatloss.domain.modules;

import ua.heatloss.domain.Measurement;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@NamedQueries(
        {
                @NamedQuery(name = "AbstractMeasurementModule.find", query = "SELECT m FROM AbstractMeasurementModule AS m"),
                @NamedQuery(name = "AbstractMeasurementModule.findTotalResultCount", query = "SELECT count(m.id) FROM AbstractMeasurementModule AS m"),
        }
)

@Entity
@Table(name = "MEASUREMENT_MODULE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MODULE_TYPE")
public abstract class AbstractMeasurementModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MeasurementsGroup measurementsGroup;

    @OneToMany(mappedBy = "measurementModule", fetch = FetchType.LAZY)
    private List<Measurement> measurements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public MeasurementsGroup getMeasurementsGroup() {
        return measurementsGroup;
    }

    public void setMeasurementsGroup(MeasurementsGroup measurementsGroup) {
        this.measurementsGroup = measurementsGroup;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractMeasurementModule)) return false;

        AbstractMeasurementModule that = (AbstractMeasurementModule) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (measurementsGroup != null ? !measurementsGroup.equals(that.measurementsGroup) : that.measurementsGroup != null)
            return false;
        return measurements != null ? measurements.equals(that.measurements) : that.measurements == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (measurementsGroup != null ? measurementsGroup.hashCode() : 0);
        result = 31 * result + (measurements != null ? measurements.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "AbstractMeasurementModule{" +
                "id=" + id +
                ", measurementsGroup=" + measurementsGroup +
                '}';
    }
}
