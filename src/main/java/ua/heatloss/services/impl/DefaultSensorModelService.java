package ua.heatloss.services.impl;

import ua.heatloss.dao.SensorModelDao;
import ua.heatloss.domain.sensors.model.SensorModel;
import ua.heatloss.services.SensorModelService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultSensorModelService implements SensorModelService {


    @Autowired
    private SensorModelDao sensorModelDao;

    @Override
    public void create(SensorModel entity) {
        sensorModelDao.create(entity);
    }

    @Override
    public void update(SensorModel entity) {
        sensorModelDao.update(entity);
    }

    @Override
    public SensorModel findById(String id) {
        return sensorModelDao.findById(id);
    }

    @Override
    public List<SensorModel> getList(Integer startPosition, Integer maxResults) {
        return sensorModelDao.getList(startPosition, maxResults);
    }

    @Override
    public Long getTotalResultCount() {
        return sensorModelDao.getTotalResultCount();
    }
}
