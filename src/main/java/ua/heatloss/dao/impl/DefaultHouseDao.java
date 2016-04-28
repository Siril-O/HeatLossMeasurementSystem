package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.heatloss.dao.HouseDao;
import ua.heatloss.dao.utils.DaoUtils;
import ua.heatloss.domain.House;

import javax.persistence.*;
import java.util.List;

@Repository
public class DefaultHouseDao implements HouseDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;


    @Override
    @Transactional
    public void createHouse(final House house) {
        if (house != null) {
            em.persist(house);
        }
    }

    @Override
    @Transactional
    public void updateHouse(final House house) {
        if (house != null && house.getId() != null) {
            em.merge(house);
        }
    }

    @Override
    public House findById(final String id) {
        if (id != null) {
            return em.find(House.class, id);
        }
        return null;
    }

    @Override
    public List<House> getHouses(Integer startPosition, Integer maxResults) {
        TypedQuery<House> query = em.createNamedQuery("House.findHouses", House.class);
        query.setFirstResult(DaoUtils.checkStartPosition(startPosition));
        query.setMaxResults(DaoUtils.checkMaxResults(maxResults));
        return query.getResultList();
    }

    @Override
    public Long getHouseTotalResultCount() {
        final Query queryTotal = em.createNamedQuery("House.findHousesTotalResultCount");
        return (long) queryTotal.getSingleResult();
    }
}
