package ua.heatloss.dao.impl;

import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.SensorDao;
import ua.heatloss.domain.sensors.Sensor;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;


public class DefaultSensorDao extends AbstractDao<Sensor> implements SensorDao {

    @Transactional
    @Override
    public void create(Sensor sensor) {
        persist(sensor, true);
    }

    @Transactional
    @Override
    public void update(Sensor sensor) {
        if (sensor != null && sensor.getId() != null) {
            persist(sensor, false);
        }
    }

    @Override
    public Sensor findById(String id) {
        return findById(id, Sensor.class);
    }

    @Override
    public List<Sensor> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "Sensor.findSensors", Sensor.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("Sensor.getTotalCount");
    }
}
