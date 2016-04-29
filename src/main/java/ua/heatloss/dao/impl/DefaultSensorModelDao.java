package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.SensorModelDao;
import ua.heatloss.domain.sensors.model.SensorModel;

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
}
