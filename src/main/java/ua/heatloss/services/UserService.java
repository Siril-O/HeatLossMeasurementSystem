package ua.heatloss.services;

import ua.heatloss.domain.user.Customer;
import ua.heatloss.domain.user.User;


public interface UserService extends CrudService<User> {

    void create(User user, String passwordConfirmation);

    User getUserByEmail(String email);

    User getCurrentUser();

    Customer getCurrentCustomer();
}
