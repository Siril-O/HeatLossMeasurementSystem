package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.heatloss.dao.HouseDao;
import ua.heatloss.domain.House;
import ua.heatloss.services.HouseService;

import java.util.List;

@Service
public class DefaultHouseService implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Override
    @Transactional
    public void createHouse(House house) {
        houseDao.createHouse(house);
    }

    @Override
    @Transactional
    public void updateHouse(House house) {
        houseDao.updateHouse(house);
    }

    @Override
    public House findById(String id) {
        return houseDao.findById(id);
    }

    @Override
    public List<House> getHouses(Integer startPosition, Integer maxResults) {
        return houseDao.getHouses(startPosition, maxResults);
    }

    @Override
    public Long getHouseTotalResultCount() {
        return houseDao.getHouseTotalResultCount();
    }
}
