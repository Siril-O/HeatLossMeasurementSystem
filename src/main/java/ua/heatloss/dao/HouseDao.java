package ua.heatloss.dao;

import ua.heatloss.domain.House;


public interface HouseDao {

    void createHouse(final House house);

    void updateHouse(final House house);

    House findById(final String id);

}
