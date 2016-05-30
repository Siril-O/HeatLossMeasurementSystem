package ua.heatloss.dao;

import ua.heatloss.domain.user.User;


public interface UserDao extends CrudDao<User>{
    User getUserByEmail(String email);
}
