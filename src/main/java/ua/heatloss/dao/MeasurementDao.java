package ua.heatloss.dao;


import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.modules.AbstractMeasurementModule;

import java.util.Date;
import java.util.List;

public interface MeasurementDao extends CrudDao<Measurement> {

    void createButch(List<Measurement> measurements);

    List<Measurement> findInTimePeriodForMeasurementSection(AbstractMeasurementModule section, Date startDate, Date endDate);
}
