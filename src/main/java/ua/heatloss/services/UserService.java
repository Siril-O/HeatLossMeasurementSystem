package ua.heatloss.services;

import ua.heatloss.domain.user.User;


public interface UserService extends CrudService<User> {

    User getUserByEmail(String email);

}
