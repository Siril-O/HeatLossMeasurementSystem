package ua.heatloss.web.controller;

import ua.heatloss.domain.House;
import ua.heatloss.services.HouseService;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class IndexCotroller extends AbstractController{

    private static final String key ="";
    @Autowired
    private HouseService houseService;

    @RequestMapping(value = { "/" })
    public String index(Model model){
        List<House> houses = houseService.getList(0, 1000);
        model.addAttribute("houses", houses);
        model.addAttribute("APIkey", key);
        return "index";
    }

}
