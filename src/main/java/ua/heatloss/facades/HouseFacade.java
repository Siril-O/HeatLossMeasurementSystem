package ua.heatloss.facades;

import org.springframework.stereotype.Component;
import ua.heatloss.domain.House;

public interface HouseFacade {

   void createHouse(House house, Integer pipesAmount);
}
