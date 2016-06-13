package ua.heatloss.domain;

import org.hibernate.annotations.Type;
import ua.heatloss.domain.modules.AbstractMeasurementModule;

import javax.persistence.*;
import java.util.Date;

@NamedQueries(
        {
                @NamedQuery(name = "Measurement.find", query = "SELECT m FROM Measurement AS m"),
                @NamedQuery(name = "Measurement.findTotalResultCount", query = "SELECT count(m.id) FROM Measurement AS m"),
                @NamedQuery(name = "Measurement.findInTimePeriodForMeasurementModule",
                        query = "SELECT m FROM Measurement AS m WHERE m.measurementModule.id =:moduleId " + Measurement.IN_DATES_CONDITION),
                @NamedQuery(name = "Measurement.findInTimePeriodForHousePipes",
                        query = "SELECT m FROM Measurement AS m WHERE m.measurementModule.class = :type AND" +
                                " m.measurementModule.pipe.id IN (:pipes) " + Measurement.IN_DATES_CONDITION),
                @NamedQuery(name = "Measurement.findInTimePeriodForHouse",
                        query = "SELECT m FROM Measurement AS m WHERE m.measurementModule.class IN (:types) AND" +
                                " ((m.measurementModule.pipe.id IN (:pipes)) OR (m.measurementModule.house.id = :houseId)) "
                                + Measurement.IN_DATES_CONDITION)

        }
)

@Entity
public class Measurement {

    static final String IN_DATES_CONDITION = "AND m.timestamp between :startDate AND :endDate";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEASUREMENT_ID")
    private Long id;

    @Type(type = "timestamp")
    @Column(name = "TIME")
    private Date timestamp;

    private Double inputValue;
    private Double flowValue;
    private Double outputValue;

    @ManyToOne
    @JoinColumn(name = "MEASUREMENT_MODULE_ID")
    private AbstractMeasurementModule measurementModule;

    public Measurement() {
    }

    public Measurement(Date timestamp, Double inputValue, Double flowValue, Double outputValue) {
        this.timestamp = timestamp;
        this.inputValue = inputValue;
        this.flowValue = flowValue;
        this.outputValue = outputValue;
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

    public AbstractMeasurementModule getMeasurementModule() {
        return measurementModule;
    }

    public void setMeasurementModule(AbstractMeasurementModule measurementModule) {
        this.measurementModule = measurementModule;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measurement)) return false;

        Measurement that = (Measurement) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (inputValue != null ? !inputValue.equals(that.inputValue) : that.inputValue != null) return false;
        if (flowValue != null ? !flowValue.equals(that.flowValue) : that.flowValue != null) return false;
        return outputValue != null ? outputValue.equals(that.outputValue) : that.outputValue == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (inputValue != null ? inputValue.hashCode() : 0);
        result = 31 * result + (flowValue != null ? flowValue.hashCode() : 0);
        result = 31 * result + (outputValue != null ? outputValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", inputValue=" + inputValue +
                ", flowValue=" + flowValue +
                ", outputValue=" + outputValue +
                ", measurementModule=" + measurementModule +
                '}';
    }
}
