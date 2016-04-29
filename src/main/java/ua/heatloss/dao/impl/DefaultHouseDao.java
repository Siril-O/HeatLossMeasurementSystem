package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.HouseDao;
import ua.heatloss.domain.House;

import java.util.List;

@Repository
public class DefaultHouseDao extends AbstractDao<House> implements HouseDao {

    @Override
    @Transactional
    public void create(House house) {
        persist(house, true);
    }

    @Override
    @Transactional
    public void update(House house) {
        if (house != null && house.getId() != null) {
            persist(house, false);
        }
    }

    @Override
    public House findById(final String id) {
        return findById(id, House.class);
    }

    @Override
    public List<House> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "House.find", House.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("House.findTotalResultCount");
    }

}
