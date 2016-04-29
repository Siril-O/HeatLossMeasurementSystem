package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.heatloss.dao.MeasurementDao;
import ua.heatloss.domain.Measurement;
import ua.heatloss.services.MeasurementService;

import java.util.List;

@Service
public class DefaultMeasurementService implements MeasurementService {


    @Autowired
    private MeasurementDao measurementDao;

    @Override
    public void create(Measurement entity) {
        measurementDao.create(entity);
    }

    @Override
    public void update(Measurement entity) {
        measurementDao.update(entity);
    }

    @Override
    public Measurement findById(Long id) {
        return measurementDao.findById(id);
    }

    @Override
    public List<Measurement> getList(Integer startPosition, Integer maxResults) {
        return measurementDao.getList(startPosition, maxResults);
    }

    @Override
    public Long getTotalResultCount() {
        return measurementDao.getTotalResultCount();
    }
}
