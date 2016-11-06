package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.heatloss.dao.MeasurementModuleDao;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.services.MeasurementModuleService;

import java.util.List;

@Service
public class DefaultMeasurementModuleService implements MeasurementModuleService {

    @Autowired
    private MeasurementModuleDao measurementModuleDao;

    @Override
    public void create(AbstractMeasurementModule entity) {
        measurementModuleDao.create(entity);
    }

    @Override
    public void update(AbstractMeasurementModule entity) {
        measurementModuleDao.update(entity);
    }

    @Override
    public AbstractMeasurementModule findById(Long id) {
        return measurementModuleDao.findById(id);
    }

    @Override
    public List<AbstractMeasurementModule> getList(Integer startPosition, Integer maxResults) {
        return measurementModuleDao.getList(startPosition, maxResults);
    }

    @Override
    public long getTotalResultCount() {
        return measurementModuleDao.getTotalResultCount();
    }

    @Override
    public void refresh(AbstractMeasurementModule entity) {
        measurementModuleDao.refresh(entity);
    }
}
