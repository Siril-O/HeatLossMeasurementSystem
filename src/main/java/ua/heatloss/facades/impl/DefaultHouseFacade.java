package ua.heatloss.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.heatloss.domain.House;
import ua.heatloss.domain.Pipe;
import ua.heatloss.facades.HouseFacade;
import ua.heatloss.services.HouseService;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultHouseFacade implements HouseFacade {

    @Autowired
    private HouseService houseService;

    @Override
    public void createHouse(House house, Integer pipesAmount) {
        List<Pipe> pipes = new ArrayList<>(pipesAmount);
        for (int i = 0; i < pipesAmount; i++) {
            Pipe pipe = new Pipe();
            pipe.setOrdinalNumber(i + 1);
            pipes.add(pipe);
        }
        house.setPipes(pipes);
        houseService.create(house);
    }
}
