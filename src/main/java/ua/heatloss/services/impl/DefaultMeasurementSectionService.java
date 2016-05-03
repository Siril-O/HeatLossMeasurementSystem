package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.heatloss.dao.MeasurementSectionDao;
import ua.heatloss.domain.MeasurementSection;
import ua.heatloss.services.MeasurementSectionService;

import java.util.List;

@Service
public class DefaultMeasurementSectionService implements MeasurementSectionService {

    @Autowired
    private MeasurementSectionDao measurementModuleDao;

    @Override
    public void create(MeasurementSection entity) {
        measurementModuleDao.create(entity);
    }

    @Override
    public void update(MeasurementSection entity) {
        measurementModuleDao.update(entity);
    }

    @Override
    public MeasurementSection findById(Long id) {
        return measurementModuleDao.findById(id);
    }

    @Override
    public List<MeasurementSection> getList(Integer startPosition, Integer maxResults) {
        return measurementModuleDao.getList(startPosition, maxResults);
    }

    @Override
    public Long getTotalResultCount() {
        return measurementModuleDao.getTotalResultCount();
    }

    @Override
    public void refresh(MeasurementSection entity) {
        measurementModuleDao.refresh(entity);
    }
}
