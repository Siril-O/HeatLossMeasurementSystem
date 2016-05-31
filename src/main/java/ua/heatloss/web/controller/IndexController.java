package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.heatloss.domain.House;
import ua.heatloss.services.HouseService;

import java.util.List;


@Controller
public class IndexController extends AbstractController {

    private static final String key = "";
    @Autowired
    private HouseService houseService;

    @RequestMapping(value = {"/"})
    public String index(Model model) {
        List<House> houses = houseService.getList(0, 1000);
        model.addAttribute("houses", houses);
        model.addAttribute("APIkey", key);
        return "index";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accesssDenied() {
        return "error.403";
    }

    @RequestMapping(value = "/login")
    public String loginForm() {
        return "login";
    }
}
