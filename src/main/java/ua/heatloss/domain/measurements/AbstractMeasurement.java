package ua.heatloss.domain.measurements;

import ua.heatloss.domain.sensors.AbstractSensor;

import java.util.Date;


public abstract class AbstractMeasurement {

    private Date time;
    private AbstractSensor sensor;
}
