package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.heatloss.domain.House;
import ua.heatloss.domain.Location;
import ua.heatloss.domain.PipeSystem;
import ua.heatloss.facades.HouseFacade;
import ua.heatloss.services.HouseService;
import ua.heatloss.web.utils.Paging;
import ua.heatloss.web.utils.PagingUtils;

import java.util.List;

@Controller
@RequestMapping("/house")
public class HouseController extends AbstractController {

    private static final String key = "";

    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseFacade houseFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/{houseId}")
    public String getHouseInfo(@PathVariable final Long houseId, final Model model) {
        final House house = houseService.findById(houseId);
        model.addAttribute(HOUSE, house);
        return "model";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createHouse(House house, Model model, @RequestParam("amountOfPipes") Integer pipesAmount) {
        houseFacade.createHouse(house, pipesAmount);
        return REDIRECT + SLASH + HOUSE + SLASH + LIST;
    }

    @RequestMapping(value = SLASH + CREATE)
    public String showAddHouseForm(Model model) {
        House house = new House();
        Location location = new Location();
        house.setLocation(location);
        model.addAttribute("house", house);
        model.addAttribute("pipeTypes", PipeSystem.values());
        return "admin." + HOUSE + "." + CREATE;
    }

    @RequestMapping(method = RequestMethod.GET, value = SLASH + LIST)
    public String getAllHouses(Paging paging, Model model) {
        final List<House> houses = houseService.getList(paging.getOffset(), paging.getLimit());
        Long total = houseService.getTotalResultCount();
        PagingUtils.preparePaging(paging, total, model);
        model.addAttribute("houses", houses);
        model.addAttribute("APIkey", key);
        return "admin." + HOUSE + ".list";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllHousesOnMap(Paging paging, Model model) {
        final List<House> houses = houseService.getList(paging.getOffset(), paging.getLimit());
        Long total = houseService.getTotalResultCount();
        PagingUtils.preparePaging(paging, total, model);
        model.addAttribute("houses", houses);
        return "admin." + HOUSE + ".map";
    }


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = SLASH + MANAGE)
    public String manageHouse(@RequestParam("houseId") House house, Model model, Paging paging) {
        houseService.refresh(house);
        model.addAttribute("house", house);
        PagingUtils.prepareJSPaging(paging, (long) house.getApartments().size(), model);
        return "admin." + HOUSE + "." + MANAGE;
    }

}
