package ua.heatloss.services.impl;

import ua.heatloss.dao.HouseDao;
import ua.heatloss.domain.House;
import ua.heatloss.services.HouseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultHouseService implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Override
    public void createHouse(House house) {
        houseDao.createHouse(house);
    }

    @Override
    public void updateHouse(House house) {
        houseDao.updateHouse(house);
    }

    @Override
    public House findById(String id) {
        return houseDao.findById(id);
    }

    @Override
    public List<House> getHouses(int startPosition, int maxResults) {
        return houseDao.getHouses(startPosition, maxResults);
    }
}
