package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.MeasurementModuleDao;
import ua.heatloss.domain.modules.AbstractMeasurementModule;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DefaultMeasurementModuleDao extends AbstractDao<AbstractMeasurementModule> implements MeasurementModuleDao {
    @Transactional
    @Override
    public void create(AbstractMeasurementModule entity) {
        persist(entity, true);
    }

    @Transactional
    @Override
    public void update(AbstractMeasurementModule entity) {
        if (entity != null && entity.getId() != null) {
            persist(entity, false);
        }
    }

    @Override
    public AbstractMeasurementModule findById(Long id) {
        return findById(id, AbstractMeasurementModule.class);
    }

    @Override
    public List<AbstractMeasurementModule> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "AbstractMeasurementModule.find", AbstractMeasurementModule.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("AbstractMeasurementModule.getTotalCount");
    }

    @Override
    public void refresh(AbstractMeasurementModule entity) {
        em.refresh(entity);
    }
}
