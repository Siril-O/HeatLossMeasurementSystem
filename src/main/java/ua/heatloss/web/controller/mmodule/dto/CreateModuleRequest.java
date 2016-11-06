package ua.heatloss.web.controller.mmodule.dto;

import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.domain.MeasurementModuleType;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.MeasurementsGroup;

/**
 * Created by KIRIL on 06.11.2016.
 */
public class CreateModuleRequest {

    private MeasurementsGroup measurementsGroup;
    private String pipeId;
    private String houseId;
    private String apartmentId;
    private MeasurementModuleType moduleType;

    public MeasurementsGroup getMeasurementsGroup() {
        return measurementsGroup;
    }

    public void setMeasurementsGroup(MeasurementsGroup measurementsGroup) {
        this.measurementsGroup = measurementsGroup;
    }

    public String getPipeId() {
        return pipeId;
    }

    public void setPipeId(String pipeId) {
        this.pipeId = pipeId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public MeasurementModuleType getModuleType() {
        return moduleType;
    }

    public void setModuleType(MeasurementModuleType moduleType) {
        this.moduleType = moduleType;
    }
}
