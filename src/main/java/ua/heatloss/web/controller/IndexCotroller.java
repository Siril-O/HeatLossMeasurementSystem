package ua.heatloss.web.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexCotroller {

    @RequestMapping(value = { "/" })
    public String index(Model model){
        model.addAttribute("date", new Date());
        return "index";
    }

}
