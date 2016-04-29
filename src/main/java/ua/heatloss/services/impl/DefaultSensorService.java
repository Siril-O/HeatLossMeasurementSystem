package ua.heatloss.services.impl;

import ua.heatloss.dao.SensorDao;
import ua.heatloss.domain.sensors.Sensor;
import ua.heatloss.services.SensorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultSensorService implements SensorService {

    @Autowired
    private SensorDao sensorDao;

    @Override
    public void create(Sensor entity) {
        sensorDao.create(entity);
    }

    @Override
    public void update(Sensor entity) {
        sensorDao.update(entity);
    }

    @Override
    public Sensor findById(String id) {
        return sensorDao.findById(id);
    }

    @Override
    public List<Sensor> getList(Integer startPosition, Integer maxResults) {
        return sensorDao.getList(startPosition, maxResults);
    }

    @Override
    public Long getTotalResultCount() {
        return sensorDao.getTotalResultCount();
    }
}
