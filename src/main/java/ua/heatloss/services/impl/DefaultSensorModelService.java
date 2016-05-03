package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.heatloss.dao.SensorModelDao;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.SensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;
import ua.heatloss.services.SensorModelService;

import java.util.List;

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
    public SensorModel findById(Long id) {
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

    @Override
    public void refresh(SensorModel entity) {
        sensorModelDao.refresh(entity);
    }

    @Override
    public List<TemperatureSensorModel> getTemperatureModelsList(Integer startPosition, Integer maxResults) {
        return sensorModelDao.getTemperatureModelsList(startPosition, maxResults);
    }

    @Override
    public List<FlowSensorModel> getFlowModelsList(Integer startPosition, Integer maxResults) {
        return sensorModelDao.getFlowModelsList(startPosition, maxResults);
    }

    @Override
    public Long getTemperatureModelsTotalCount() {
        return sensorModelDao.getTemperatureModelsTotalCount();
    }

    @Override
    public Long getFlowModelsTotalCount() {
        return sensorModelDao.getFlowModelsTotalCount();
    }
}
