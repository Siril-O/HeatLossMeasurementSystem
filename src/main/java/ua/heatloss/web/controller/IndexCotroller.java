package ua.heatloss.web.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class IndexCotroller extends AbstractController{

    @RequestMapping(value = { "/" })
    public String index(){
        return REDIRECT + "/report/energy";
    }

}
