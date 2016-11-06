package ua.heatloss.services.impl;

import ua.heatloss.dao.UserDao;
import ua.heatloss.domain.user.Customer;
import ua.heatloss.domain.user.Role;
import ua.heatloss.domain.user.User;
import ua.heatloss.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void create(User user, String passwordConfirmation) {
        long users = userDao.countUsersWithEmail(user.getEmail());
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
    public long getTotalResultCount() {
        return userDao.getTotalResultCount();
    }

    @Override
    public void refresh(User entity) {
        userDao.refresh(entity);
    }

    @Override
    public User getCurrentUser() {
        return getCurrentUserInternal();
    }

    private User getCurrentUserInternal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return getUserByEmail(email);
    }

    @Override
    public Customer getCurrentCustomer() {
        User user = getCurrentUserInternal();
        if (Role.ROLE_CUSTOMER ==user.getRole()) {
            return (Customer) user;
        } else {
            return null;
        }
    }

    @Override
    public boolean hasRole(Role role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().
                filter(a -> a.getAuthority().equals(role.name())).findAny().isPresent();
    }
}
