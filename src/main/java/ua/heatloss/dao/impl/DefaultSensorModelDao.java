package ua.heatloss.dao.impl;

import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.SensorModelDao;
import ua.heatloss.domain.sensors.Sensor;
import ua.heatloss.domain.sensors.model.SensorModel;

import java.util.List;

import javax.transaction.Transactional;


public class DefaultSensorModelDao extends AbstractDao<SensorModel> implements SensorModelDao {

    @Transactional
    @Override
    public void create(SensorModel entity) {
        persist(entity, true);
    }

    @Transactional
    @Override
    public void update(SensorModel entity) {
        if (entity != null && entity.getId() != null) {
            persist(entity, false);
        }
    }

    @Override
    public SensorModel findById(String id) {
        return findById(id, SensorModel.class);
    }

    @Override
    public List<SensorModel> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "SensorModel.findSensors", SensorModel.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("SensorModel.getTotalCount");
    }
}
