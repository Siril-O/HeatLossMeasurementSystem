package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.heatloss.dao.HouseDao;
import ua.heatloss.domain.House;
import ua.heatloss.services.HouseService;

import java.util.List;

@Service
public class DefaultHouseService implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Override
    public void create(House house) {
        houseDao.create(house);
    }

    @Override
    public void update(House house) {
        houseDao.update(house);
    }


    @Override
    public House findById(Long id) {
        return houseDao.findById(id);
    }


    @Override
    public List<House> getList(Integer startPosition, Integer maxResults) {
        return houseDao.getList(startPosition, maxResults);
    }

    @Override
    public long getTotalResultCount() {
        return houseDao.getTotalResultCount();
    }

    @Override
    public void refresh(House entity) {
        houseDao.refresh(entity);
    }
}
