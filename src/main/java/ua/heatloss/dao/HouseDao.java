package ua.heatloss.dao;

import ua.heatloss.domain.House;

import java.util.List;


public interface HouseDao {

    void createHouse(final House house);

    void updateHouse(final House house);

    House findById(final String id);

    List<House> getHouses(final int startPosition,final int maxResults);

}
