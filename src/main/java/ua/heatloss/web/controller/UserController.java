package ua.heatloss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.heatloss.domain.user.Employee;
import ua.heatloss.services.UserService;

@Controller
@RequestMapping(value = "user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register/employee", method = RequestMethod.POST)
    @ResponseBody
    public Employee registerEmploee(Employee employee, @RequestParam("passwordConfirmation") String confirmation) {
        userService.create(employee, confirmation);
        return employee;
    }
}

