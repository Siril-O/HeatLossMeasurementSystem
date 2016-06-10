package ua.heatloss.web.controller.dto;

import java.util.Date;
import java.util.List;


public class HouseReportData extends ReportData {

    private List<HouseReportDataEntry> reportEntries;

    public HouseReportData(Date startDate, Date endDate, List<HouseReportDataEntry> reportEntries) {
        super(startDate, endDate);
        this.reportEntries = reportEntries;
    }


    public List<HouseReportDataEntry> getReportEntries() {
        return reportEntries;
    }

    public void setReportEntries(List<HouseReportDataEntry> reportEntries) {
        this.reportEntries = reportEntries;
    }
}
