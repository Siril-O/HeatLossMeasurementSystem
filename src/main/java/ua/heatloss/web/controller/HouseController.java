package ua.heatloss.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.heatloss.dao.utils.DaoUtils;
import ua.heatloss.domain.Address;
import ua.heatloss.domain.House;
import ua.heatloss.services.HouseService;

import java.util.List;

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
        LOG.debug("Start Creating House:" + house);
        houseService.createHouse(house);
        return "redirect:";
    }

    @RequestMapping(value = "/addHouse")
    public String showAddHouseForm(Model model) {
        LOG.debug("Show House Form");
        House house = new House();
        Address address = new Address();
        house.setAddress(address);
        model.addAttribute("house", house);
        return "house.addHouseForm";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public String getAllHouses(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                               @RequestParam(value = "limit", required = false, defaultValue = DaoUtils.DEFAULT_LIMIT_STRING) Integer limit,
                               Model model) {
        final List<House> houses = houseService.getHouses(offset, limit);
        Long total = houseService.getHouseTotalResultCount();

        int paging = preparePaging(offset, limit, total, model);
        LOG.debug("Offset:" + offset + " Limit:" + limit + " Total:" + total + " pagesNumber:" + paging + " Houses:" + houses);

        model.addAttribute("houses", houses);
        return "house.paging.list";
    }

    private int preparePaging(Integer offset, Integer limit, Long total, Model model) {
        if (limit == null || limit == 0) {
            limit = DaoUtils.DEFAULT_LIMIT;
        }
        long pages = total / limit;
        int extraPage = (total % limit) != 0 ? 1 : 0;
        int pagesQuantity = (int) (pages + extraPage);

        model.addAttribute("pagesQuantity", pagesQuantity);
        model.addAttribute("offset", offset);
        model.addAttribute("limit", limit);
        return pagesQuantity;
    }

}
