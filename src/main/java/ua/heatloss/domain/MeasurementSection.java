package ua.heatloss.domain;

import javax.persistence.*;
import java.util.List;


@NamedQueries(
        {
                @NamedQuery(name = "MeasurementSection.find", query = "SELECT m FROM MeasurementSection AS m"),
                @NamedQuery(name = "MeasurementSection.findTotalResultCount", query = "SELECT count(m.id) FROM MeasurementSection AS m"),
        }
)


@Entity
public class MeasurementSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ordinalNumber;

    @ManyToOne
    @JoinColumn(name = "PIPE_ID")
    private Pipe pipe;

    @Embedded
    private MeasurementModule measurementModule;

    @ManyToOne
    @JoinColumn(name = "APARTMENT_ID")
    private Apartment apartment;

    @OneToMany(mappedBy = "measurementSection", fetch = FetchType.LAZY)
    private List<Measurement> measurements;

    @Transient
    private SectionType sectionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public Pipe getPipe() {
        return pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    public MeasurementModule getMeasurementModule() {
        return measurementModule;
    }

    public void setMeasurementModule(MeasurementModule measurementModule) {
        this.measurementModule = measurementModule;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public SectionType getSectionType() {
        if (sectionType == null) {
            sectionType = defineSectionType();
        }
        return sectionType;
    }

    private SectionType defineSectionType() {
        boolean apartmentPresent = apartment != null && apartment.getNumber() != null;
        boolean measurementModulePresent = measurementModule != null && measurementModule.getFlow() != null;
        if (!apartmentPresent && measurementModulePresent) {
            return SectionType.MAIN_PIPE_SENSOR;
        } else if (apartmentPresent && measurementModulePresent) {
            return SectionType.APARTMENT_WITH_SENSOR;
        } else {
            return SectionType.APARTMENT_WITHOUT_SENSOR;
        }

    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeasurementSection)) return false;

        MeasurementSection section = (MeasurementSection) o;

        if (id != null ? !id.equals(section.id) : section.id != null) return false;
        if (ordinalNumber != null ? !ordinalNumber.equals(section.ordinalNumber) : section.ordinalNumber != null)
            return false;
        return pipe != null ? pipe.equals(section.pipe) : section.pipe == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ordinalNumber != null ? ordinalNumber.hashCode() : 0);
        result = 31 * result + (pipe != null ? pipe.hashCode() : 0);
        return result;
    }
}
