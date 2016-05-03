package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.SensorModelDao;
import ua.heatloss.domain.sensors.model.FlowSensorModel;
import ua.heatloss.domain.sensors.model.SensorModel;
import ua.heatloss.domain.sensors.model.TemperatureSensorModel;

import javax.transaction.Transactional;
import java.util.List;

@Repository
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
    public SensorModel findById(Long id) {
        return findById(id, SensorModel.class);
    }

    @Override
    public List<SensorModel> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "SensorModel.find", SensorModel.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("SensorModel.getTotalCount");
    }

    @Override
    public void refresh(SensorModel entity) {
        em.refresh(entity);
    }


    @Override
    public List<TemperatureSensorModel> getTemperatureModelsList(final Integer startPosition, final Integer maxResults) {
        return getList(startPosition, maxResults, "TemperatureSensorModel.find", TemperatureSensorModel.class);
    }

    @Override
    public List<FlowSensorModel> getFlowModelsList(final Integer startPosition, final Integer maxResults) {
        return getList(startPosition, maxResults, "FlowSensorModel.find", FlowSensorModel.class);
    }

    @Override
    public Long getTemperatureModelsTotalCount() {
        return getTotalResultCount("TemperatureSensorModel.getTotalCount");
    }

    @Override
    public Long getFlowModelsTotalCount() {
        return getTotalResultCount("FlowSensorModel.getTotalCount");
    }
}
