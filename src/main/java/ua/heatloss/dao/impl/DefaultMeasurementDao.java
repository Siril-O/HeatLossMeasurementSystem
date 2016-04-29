package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.MeasurementDao;
import ua.heatloss.domain.Measurement;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DefaultMeasurementDao extends AbstractDao<Measurement> implements MeasurementDao {

    @Transactional
    @Override
    public void create(Measurement entity) {
        persist(entity, true);
    }

    @Transactional
    @Override
    public void update(Measurement entity) {
        if (entity != null && entity.getId() != null) {
            persist(entity, false);
        }
    }

    @Override
    public Measurement findById(String id) {
        return findById(id, Measurement.class);
    }

    @Override
    public List<Measurement> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "Measurement.find", Measurement.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("Measurement.getTotalCount");
    }
}

