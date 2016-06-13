package ua.heatloss.services;


import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.modules.AbstractMeasurementModule;

import java.util.Date;
import java.util.List;

public interface MeasurementService extends CrudService<Measurement>{

    void createButch(List<Measurement> measurements);

    List<Measurement> findInTimePeriodForMeasurementModule(AbstractMeasurementModule module, Date startDate, Date endDate);

    List<Measurement> findInTimePeriodForHouse(House house, Date startDate, Date endDate);
}
