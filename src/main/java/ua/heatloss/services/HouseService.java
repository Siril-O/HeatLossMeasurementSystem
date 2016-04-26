package ua.heatloss.services;


import ua.heatloss.domain.House;

import java.util.List;

public interface HouseService {


    void createHouse(final House house);

    void updateHouse(final House house);

    House findById(final String id);

    List<House> getHouses(final Integer startPosition, final Integer maxResults);

}
