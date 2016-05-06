package ua.heatloss.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@NamedQueries(
        {
                @NamedQuery(name = "Measurement.find", query = "SELECT m FROM Measurement AS m"),
                @NamedQuery(name = "Measurement.findTotalResultCount", query = "SELECT count(m.id) FROM Measurement AS m"),
                @NamedQuery(name = "Measurement.findInTimePeriodForMeasurementSection",
                        query = "SELECT m FROM Measurement AS m WHERE m.measurementSection.id =:sectionId AND m.timestamp between :startDate AND :endDate")

        }
)

@Entity
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEASUREMENT_ID")
    private Long id;

    @Type(type = "timestamp")
    @Column(name = "TIME")
    private Date timestamp;

    private Double inputAdditionalValue;
    private Double inputValue;
    private Double flowValue;
    private Double outputValue;
    private Double outputAdditionalValue;

    @ManyToOne
    @JoinColumn(name = "MEASUREMENT_SECTION_ID")
    private MeasurementSection measurementSection;

    public Measurement() {
    }

    public Measurement(Date timestamp, Double inputAdditionalValue, Double inputValue, Double flowValue, Double outputValue, Double outputAdditionalValue) {
        this.timestamp = timestamp;
        this.inputAdditionalValue = inputAdditionalValue;
        this.inputValue = inputValue;
        this.flowValue = flowValue;
        this.outputValue = outputValue;
        this.outputAdditionalValue = outputAdditionalValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getInputAdditionalValue() {
        return inputAdditionalValue;
    }

    public void setInputAdditionalValue(Double inputAdditionalValue) {
        this.inputAdditionalValue = inputAdditionalValue;
    }

    public Double getInputValue() {
        return inputValue;
    }

    public void setInputValue(Double inputValue) {
        this.inputValue = inputValue;
    }

    public Double getFlowValue() {
        return flowValue;
    }

    public void setFlowValue(Double flowValue) {
        this.flowValue = flowValue;
    }

    public Double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(Double outputValue) {
        this.outputValue = outputValue;
    }

    public Double getOutputAdditionalValue() {
        return outputAdditionalValue;
    }

    public void setOutputAdditionalValue(Double outputAdditionalValue) {
        this.outputAdditionalValue = outputAdditionalValue;
    }

    public MeasurementSection getMeasurementSection() {
        return measurementSection;
    }

    public void setMeasurementSection(MeasurementSection measurementSection) {
        this.measurementSection = measurementSection;
    }
}
