package ua.heatloss.domain;

import ua.heatloss.domain.sensors.Sensor;

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

    @OneToMany(mappedBy = "measurementSection", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Sensor> sensors;

    @ManyToOne
    @JoinColumn(name = "APARTMENT_ID")
    private Apartment apartment;

    @Transient
    private SectionType sectionType;

    @Transient
    private MeasurementModuleType moduleType;

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

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
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
        if (apartment == null && (sensors == null || sensors.isEmpty())) {
            return SectionType.NOT_APARTMENT_WITHOUT_SENSOR;
        } else if (apartment == null && !(sensors == null || sensors.isEmpty())) {
            return SectionType.NOT_APARTMENT_WITH_SENSOR;
        } else if (apartment != null && !(sensors == null || sensors.isEmpty())) {
            return SectionType.APARTMENT_WITH_SENSOR;
        } else {
            return SectionType.APARTMENT_WITHOT_SENSOR;
        }
    }

    public MeasurementModuleType getModuleType() {
        if (moduleType == null) {
            moduleType = defineModuleType();
        }
        return moduleType;
    }

    private MeasurementModuleType defineModuleType() {
        if (sensors != null && !sensors.isEmpty()) {
            if (MeasurementModuleType.STANDART.getSensorsQuantity() == sensors.size()) {
                return MeasurementModuleType.STANDART;
            } else {
                return MeasurementModuleType.EXTENDED;
            }
        } else {
            return null;
        }
    }

    public void setModuleType(MeasurementModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

}
