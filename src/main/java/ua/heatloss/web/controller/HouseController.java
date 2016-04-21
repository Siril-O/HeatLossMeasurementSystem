package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.heatloss.dao.HouseDao;
import ua.heatloss.domain.House;

@Controller
@RequestMapping("house")
public class HouseController {

    @Autowired
    private HouseDao houseDao;

    @RequestMapping(method = RequestMethod.GET)
    public void getHouses(final Model model) {

    }

    @RequestMapping(method = RequestMethod.GET, value = "/gethouse")
    public String getHouse(@RequestParam(value = "id") String id, final Model model) {
        final House house = houseDao.findById(id);
        model.addAttribute("house", house);
        return "test";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createHouse(House house, Model model) {
        houseDao.createHouse(house);
        return "redirect:";
    }
}
