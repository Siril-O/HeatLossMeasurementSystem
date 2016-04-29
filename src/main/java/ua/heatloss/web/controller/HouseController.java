package ua.heatloss.web.controller;

import ua.heatloss.dao.AbstractDao;
import ua.heatloss.domain.Address;
import ua.heatloss.domain.House;
import ua.heatloss.services.HouseService;
import ua.heatloss.web.utils.PagingUtils;
import ua.heatloss.web.utils.WebConstants;

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
    public static final String HOUSE = "house";

    @Autowired
    private HouseService houseService;

    @RequestMapping(method = RequestMethod.GET, value = "/{houseId}")
    public String getHouse(@PathVariable final String houseId, final Model model) {
        final House house = houseService.findById(houseId);
        model.addAttribute(HOUSE, house);
        return "model";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createHouse(House house, Model model) {
        LOG.debug("Start Creating House:" + house);
        houseService.create(house);
        return WebConstants.REDIRECT;
    }

    @RequestMapping(value = WebConstants.SLASH + WebConstants.CREATE)
    public String showAddHouseForm(Model model) {
        LOG.debug("Show House Form");
        House house = new House();
        Address address = new Address();
        house.setAddress(address);
        model.addAttribute("house", house);
        return HOUSE + "." + WebConstants.CREATE;
    }

    @RequestMapping(method = RequestMethod.GET, value = WebConstants.SLASH + WebConstants.LIST)
    public String getAllHouses(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                               @RequestParam(value = "limit", required = false, defaultValue = AbstractDao.DEFAULT_LIMIT_STRING) Integer limit,
                               Model model) {
        final List<House> houses = houseService.getList(offset, limit);
        Long total = houseService.getTotalResultCount();

        int paging = PagingUtils.preparePaging(offset, limit, total, model);
        LOG.debug("Offset:" + offset + " Limit:" + limit + " Total:" + total + " pagesNumber:" + paging + " Houses:" + houses);

        model.addAttribute("houses", houses);
        return HOUSE + WebConstants.PAGED_LIST;
    }


}
