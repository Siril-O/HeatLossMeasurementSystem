package ua.heatloss.services.impl;

import ua.heatloss.dao.MeasurementDao;
import ua.heatloss.domain.House;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.Pipe;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.services.MeasurementService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void refresh(Measurement entity) {
        measurementDao.refresh(entity);
    }

    @Override
    public void createButch(List<Measurement> measurements) {
        measurementDao.createButch(measurements);
    }

    @Override
    public List<Measurement> findInTimePeriodForMeasurementModule(AbstractMeasurementModule section, Date startDate, Date endDate) {
        return measurementDao.findInTimePeriodForMeasurementSection(section, startDate, endDate);
    }

    @Override
    public List<Measurement> findInTimePeriodForHouse(House house, Date startDate, Date endDate) {
        return measurementDao.findInTimePeriodForHouse(house, startDate, endDate);
    }

    @Override
    public List<Measurement> findInTimePeriodForHousePipes(List<Pipe> pipes, Date startDate, Date endDate) {
        return measurementDao.findInTimePeriodForHousePipes(pipes, startDate, endDate);
    }

}
