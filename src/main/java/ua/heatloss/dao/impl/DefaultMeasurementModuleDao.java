package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.MeasurementModuleDao;
import ua.heatloss.domain.MeasurementModule;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DefaultMeasurementModuleDao extends AbstractDao<MeasurementModule> implements MeasurementModuleDao {
    @Transactional
    @Override
    public void create(MeasurementModule entity) {
        persist(entity, true);
    }

    @Transactional
    @Override
    public void update(MeasurementModule entity) {
        if (entity != null && entity.getId() != null) {
            persist(entity, false);
        }
    }

    @Override
    public MeasurementModule findById(String id) {
        return findById(id, MeasurementModule.class);
    }

    @Override
    public List<MeasurementModule> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "MeasurementModule.find", MeasurementModule.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("MeasurementModule.getTotalCount");
    }
}
