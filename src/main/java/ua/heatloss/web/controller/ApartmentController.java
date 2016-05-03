package ua.heatloss.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;


@Controller
@RequestMapping("/apartment")
public class ApartmentController extends AbstractController {

    public static final String APARTMENT = "apartment";

    @RequestMapping(value = SLASH + CREATE)
    public String showAddApartmentForm(@RequestParam("houseId") House house, Model model) {
        Apartment apartment = new Apartment();
        model.addAttribute("apartment", apartment);
        return APARTMENT + DOT + CREATE;
    }


}
