package ua.heatloss.web.controller.dto;

import java.util.Date;


public class HouseReportData {

    private Date date;
    private double loss;
    private double input;
    private double consumed;

    public HouseReportData() {
    }

    public HouseReportData(Date date, double loss, double input, double consumed) {
        this.date = date;
        this.loss = loss;
        this.input = input;
        this.consumed = consumed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public double getInput() {
        return input;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public double getConsumed() {
        return consumed;
    }

    public void setConsumed(double consumed) {
        this.consumed = consumed;
    }
}
