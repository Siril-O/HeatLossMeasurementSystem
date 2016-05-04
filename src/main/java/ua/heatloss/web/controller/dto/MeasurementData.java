package ua.heatloss.web.controller.dto;

import java.util.Map;


public class MeasurementData {

    private Map<Long, Double> readings;

    public Map<Long, Double> getReadings() {
        return readings;
    }

    public void setReadings(Map<Long, Double> readings) {
        this.readings = readings;
    }
}
