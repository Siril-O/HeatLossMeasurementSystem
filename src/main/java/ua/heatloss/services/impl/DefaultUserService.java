package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.heatloss.dao.UserDao;
import ua.heatloss.domain.user.User;
import ua.heatloss.services.UserService;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void create(User user, String passwordConfirmation) {
        int users = userDao.countUsersWithEmail(user.getEmail());
        if (users == 0) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.create(user);
        }
    }

    @Override
    public void create(User entity) {
        userDao.create(entity);
    }

    @Override
    public void update(User entity) {
        userDao.update(entity);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> getList(Integer startPosition, Integer maxResults) {
        return userDao.getList(startPosition, maxResults);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public Long getTotalResultCount() {
        return userDao.getTotalResultCount();
    }

    @Override
    public void refresh(User entity) {
        userDao.refresh(entity);
    }
}
