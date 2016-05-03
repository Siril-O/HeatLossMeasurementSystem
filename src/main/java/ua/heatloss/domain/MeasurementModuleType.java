package ua.heatloss.domain;


public enum MeasurementModuleType {

    STANDART("Standart", 3), EXTENDED("Extended", 5);

    private String name;
    private int sensorsQuantity;

    MeasurementModuleType(String name, int sensorsQuantity) {
        this.name = name;
        this.sensorsQuantity = sensorsQuantity;
    }

    public int getSensorsQuantity() {
        return sensorsQuantity;
    }

    public void setSensorsQuantity(int sensorsQuantity) {
        this.sensorsQuantity = sensorsQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
