package ua.heatloss.web.utils;


public class Term {

    private String label;
    private int value;

    public Term() {
    }

    public Term(int value, String label) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Term{" +
                "label=" + label +
                ", value='" + value + '\'' +
                '}';
    }
}
