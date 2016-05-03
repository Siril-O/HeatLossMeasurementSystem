package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.MeasurementSectionDao;
import ua.heatloss.domain.MeasurementSection;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DefaultMeasurementSectionDao extends AbstractDao<MeasurementSection> implements MeasurementSectionDao {
    @Transactional
    @Override
    public void create(MeasurementSection entity) {
        persist(entity, true);
    }

    @Transactional
    @Override
    public void update(MeasurementSection entity) {
        if (entity != null && entity.getId() != null) {
            persist(entity, false);
        }
    }

    @Override
    public MeasurementSection findById(Long id) {
        return findById(id, MeasurementSection.class);
    }

    @Override
    public List<MeasurementSection> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "MeasurementSection.find", MeasurementSection.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("MeasurementSection.getTotalCount");
    }

    @Override
    public void refresh(MeasurementSection entity) {
        em.refresh(entity);
    }
}
