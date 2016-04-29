package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.heatloss.dao.PipeDao;
import ua.heatloss.domain.Pipe;
import ua.heatloss.services.PipeService;

import java.util.List;

@Service
public class DefaultPipeService implements PipeService {

    @Autowired
    private PipeDao pipeDao;

    @Override
    public void create(Pipe entity) {
        pipeDao.create(entity);
    }

    @Override
    public void update(Pipe entity) {
        pipeDao.update(entity);
    }

    @Override
    public Pipe findById(Long id) {
        return pipeDao.findById(id);
    }

    @Override
    public List<Pipe> getList(Integer startPosition, Integer maxResults) {
        return pipeDao.getList(startPosition, maxResults);
    }

    @Override
    public Long getTotalResultCount() {
        return pipeDao.getTotalResultCount();
    }
}
