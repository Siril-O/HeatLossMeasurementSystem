package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.UserDao;
import ua.heatloss.domain.user.User;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DefaultUserDao extends AbstractDao<User> implements UserDao {


    @Override
    @Transactional
    public void create(User entity) {
        persist(entity, true);
    }

    @Override
    public void update(User entity) {
        if (entity != null && entity.getId() != null) {
            persist(entity, false);
        }
    }

    @Override
    public User findById(Long id) {
        return findById(id, User.class);
    }

    @Override
    public List<User> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "User.find", User.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("User.getTotalCount");
    }

    @Override
    public void refresh(User entity) {
        em.refresh(entity);
    }

    @Override
    public User getUserByEmail(final String email) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public int countUsersWithEmail(final String email) {
        Query query = em.createNamedQuery("User.countWithEmail");
        query.setParameter("email", email);
        return (int) query.getSingleResult();
    }
}
