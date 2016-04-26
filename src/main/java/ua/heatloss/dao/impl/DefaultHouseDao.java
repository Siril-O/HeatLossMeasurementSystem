package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.heatloss.dao.HouseDao;
import ua.heatloss.domain.House;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DefaultHouseDao implements HouseDao {

    @PersistenceContext
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
    public List<House> getHouses(int startPosition, int maxResults) {
        return null;
    }
}
