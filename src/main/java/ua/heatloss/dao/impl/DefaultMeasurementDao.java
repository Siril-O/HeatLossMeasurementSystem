package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.MeasurementDao;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.modules.AbstractMeasurementModule;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class DefaultMeasurementDao extends AbstractDao<Measurement> implements MeasurementDao {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
    public Measurement findById(Long id) {
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

    @Override
    public void refresh(Measurement entity) {
        em.refresh(entity);
    }

    @Transactional
    @Override
    public void createButch(List<Measurement> measurements) {
        int counter = 0;
        for (Measurement measurement : measurements) {
            em.persist(measurement);
            counter++;
            if (counter % 50 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    @Override
    public List<Measurement> findInTimePeriodForMeasurementSection(AbstractMeasurementModule module, Date startDate, Date endDate) {
        TypedQuery<Measurement> query = em.createNamedQuery("Measurement.findInTimePeriodForMeasurementModule", Measurement.class);
        query.setParameter("moduleId", module.getId());
        query.setParameter("startDate",startDate,TemporalType.TIMESTAMP);
        query.setParameter("endDate",endDate, TemporalType.TIMESTAMP);
        return query.getResultList();
    }
}

