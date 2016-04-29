package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.heatloss.dao.MeasurementModuleDao;
import ua.heatloss.domain.MeasurementModule;
import ua.heatloss.services.MeasurementModuleService;

import java.util.List;

@Service
public class DefaultMeasurementModuleService implements MeasurementModuleService {

    @Autowired
    private MeasurementModuleDao measurementModuleDao;

    @Override
    public void create(MeasurementModule entity) {
        measurementModuleDao.create(entity);
    }

    @Override
    public void update(MeasurementModule entity) {
        measurementModuleDao.update(entity);
    }

    @Override
    public MeasurementModule findById(String id) {
        return measurementModuleDao.findById(id);
    }

    @Override
    public List<MeasurementModule> getList(Integer startPosition, Integer maxResults) {
        return measurementModuleDao.getList(startPosition, maxResults);
    }

    @Override
    public Long getTotalResultCount() {
        return measurementModuleDao.getTotalResultCount();
    }
}
