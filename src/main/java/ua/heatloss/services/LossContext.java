package ua.heatloss.services;

import ua.heatloss.domain.Measurement;

import java.util.Date;
import java.util.List;

/**
 * Created by KIRIL on 06.11.2016.
 */
public class LossContext {

    private Date date;
    private Measurement mainMeasurement;
    private List<Measurement> pipeMeasurements;

    public LossContext(Measurement mainMeasurement, List<Measurement> pipeMeasurements) {
        this.mainMeasurement = mainMeasurement;
        this.pipeMeasurements = pipeMeasurements;
        date = mainMeasurement.getTimestamp();
    }

    public LossContext(Date date, Measurement mainMeasurement, List<Measurement> pipeMeasurements) {
        this.date = date;
        this.mainMeasurement = mainMeasurement;
        this.pipeMeasurements = pipeMeasurements;
    }

    public Measurement getMainMeasurement() {
        return mainMeasurement;
    }

    public void setMainMeasurement(Measurement mainMeasurement) {
        this.mainMeasurement = mainMeasurement;
    }

    public List<Measurement> getPipeMeasurements() {
        return pipeMeasurements;
    }

    public void setPipeMeasurements(List<Measurement> pipeMeasurements) {
        this.pipeMeasurements = pipeMeasurements;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
