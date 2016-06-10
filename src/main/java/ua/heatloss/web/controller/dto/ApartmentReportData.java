package ua.heatloss.web.controller.dto;

import java.util.Date;
import java.util.Map;

public class ApartmentReportData extends ReportData {

    private Map<Date, Double> result;

    public ApartmentReportData(Date startDate, Date endDate, Map<Date, Double> result) {
        super(startDate, endDate);
        this.result = result;
    }

    public Map<Date, Double> getResult() {
        return result;
    }

    public void setResult(Map<Date, Double> result) {
        this.result = result;
    }
}
