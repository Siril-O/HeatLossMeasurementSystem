package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.services.ApartmentService;


@Controller
@RequestMapping("/apartment")
public class ApartmentController extends AbstractController {

    public static final String APARTMENT = "apartment";

    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping(value = SLASH + CREATE)
    public String showAddApartmentForm(@RequestParam("houseId") House house, Model model) {
        Apartment apartment = new Apartment();
        apartment.setHouse(house);
        model.addAttribute("apartment", apartment);
        return ADMIN + DOT + APARTMENT + DOT + CREATE;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createApartment(Apartment apartment, Model model, RedirectAttributes redirectAttributes) {
        apartmentService.create(apartment);
        return redirectToManageHouse(apartment.getHouse(), redirectAttributes);
    }

}
