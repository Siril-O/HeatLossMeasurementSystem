package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.heatloss.domain.user.Employee;
import ua.heatloss.domain.user.User;
import ua.heatloss.services.UserService;


@Controller
public class IndexController extends AbstractController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/"})
    public String showIndexPage(RedirectAttributes attributes) {

        User user = userService.getCurrentUser();
        if (user instanceof Employee) {
            return REDIRECT + HOUSE;
        } else {
            return "energy/apartment";
        }
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
