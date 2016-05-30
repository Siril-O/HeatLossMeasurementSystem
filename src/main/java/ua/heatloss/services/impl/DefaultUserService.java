package ua.heatloss.services.impl;

import ua.heatloss.dao.UserDao;
import ua.heatloss.domain.user.User;
import ua.heatloss.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserDao userDao;

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
