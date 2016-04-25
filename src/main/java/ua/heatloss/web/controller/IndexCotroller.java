package ua.heatloss.web.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexCotroller {

    private final static Logger LOG = LoggerFactory.getLogger(IndexCotroller.class);

    @RequestMapping(value = { "/" })
    public String test(Model model){
        LOG.debug("test method invoked");
        model.addAttribute("date", new Date());
        return "index";
    }

    @RequestMapping(value = { "/info" })
    public String info(Model model){
        LOG.debug("test method invoked");
        model.addAttribute("date", new Date());
        return "info";
    }
}
