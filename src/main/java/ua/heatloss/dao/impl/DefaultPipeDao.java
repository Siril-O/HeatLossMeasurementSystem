package ua.heatloss.dao.impl;


import org.springframework.stereotype.Repository;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.PipeDao;
import ua.heatloss.domain.Pipe;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DefaultPipeDao extends AbstractDao<Pipe> implements PipeDao {


    @Transactional
    @Override
    public void create(Pipe entity) {
        persist(entity, true);
    }

    @Transactional
    @Override
    public void update(Pipe entity) {
        if (entity != null && entity.getId() != null) {
            persist(entity, false);
        }
    }

    @Override
    public Pipe findById(String id) {
        return findById(id, Pipe.class);
    }

    @Override
    public List<Pipe> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "Measurement.find", Pipe.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("Measurement.getTotalCount");
    }
}
