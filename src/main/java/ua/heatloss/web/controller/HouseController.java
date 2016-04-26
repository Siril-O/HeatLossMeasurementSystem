package ua.heatloss.web.controller;

import ua.heatloss.domain.House;
import ua.heatloss.services.HouseService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("house")
public class HouseController {

    private static final Logger LOG = LoggerFactory.getLogger(HouseController.class);

    @Autowired
    private HouseService houseService;

    @RequestMapping(method = RequestMethod.GET)
    public void getHouses(final Model model) {

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{houseId}")
    public String getHouse(@PathVariable final String houseId, final Model model) {
        final House house = houseService.findById(houseId);
        model.addAttribute("house", house);
        return "info";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createHouse(House house, Model model) {
        LOG.debug("Start Creating House");
        houseService.createHouse(house);
        return "redirect:";
    }

    @RequestMapping(value = "/addHouse")
    public String showAddHouseForm(Model model) {
        LOG.debug("Show House Form");
        House house = new House();
        model.addAttribute("house", house);
        return "house.addHouseForm";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public String getAllHouses(@RequestParam(value = "startPosition", required = false) int startPosition,
                               @RequestParam(value = "limit", required = false) int limit, Model model) {
        final List<House> houses = houseService.getHouses(startPosition, limit);
        model.addAttribute("houses", houses);
        return "houses";
    }
}
