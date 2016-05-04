package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.heatloss.dao.ApartmentDao;
import ua.heatloss.domain.Apartment;
import ua.heatloss.services.ApartmentService;

import java.util.List;

@Service
public class DefaultApartmentService implements ApartmentService {

    @Autowired
    private ApartmentDao apartmentDao;

    @Override
    public void create(Apartment entity) {
        apartmentDao.create(entity);
    }

    @Override
    public void update(Apartment entity) {
        apartmentDao.update(entity);
    }

    @Override
    public Apartment findById(Long id) {
        return apartmentDao.findById(id);
    }

    @Override
    public List<Apartment> getList(Integer startPosition, Integer maxResults) {
        return apartmentDao.getList(startPosition, maxResults);
    }

    @Override
    public Long getTotalResultCount() {
        return apartmentDao.getTotalResultCount();
    }

    @Override
    public void refresh(Apartment entity) {
        apartmentDao.refresh(entity);
    }

    @Override
    public List<Apartment> findApartmentsByNumber(Integer number) {
        return apartmentDao.findApartmentsByNumber(number);
    }
}
