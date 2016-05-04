package ua.heatloss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.heatloss.dao.AbstractDao;
import ua.heatloss.dao.ApartmentDao;
import ua.heatloss.domain.Apartment;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class DefaultAppartmentDao extends AbstractDao<Apartment> implements ApartmentDao {

    @Override
    @Transactional
    public void create(Apartment apartment) {
        persist(apartment, true);
    }

    @Override
    @Transactional
    public void update(Apartment apartment) {
        if (apartment != null && apartment.getId() != null) {
            persist(apartment, false);
        }
    }

    @Override
    public Apartment findById(final Long id) {
        return findById(id, Apartment.class);
    }

    @Override
    public List<Apartment> getList(Integer startPosition, Integer maxResults) {
        return getList(startPosition, maxResults, "Apartment.find", Apartment.class);
    }

    @Override
    public Long getTotalResultCount() {
        return getTotalResultCount("Apartment.findTotalResultCount");
    }

    @Override
    public void refresh(Apartment entity) {
        em.refresh(entity);
    }

    @Override
    public List<Apartment> findApartmentsByNumber(Integer number) {
        TypedQuery<Apartment> query = em.createNamedQuery("Apartment.findByNumberLike", Apartment.class);
        query.setFirstResult(checkStartPosition(null));
        query.setMaxResults(checkMaxResults(null));
        query.setParameter("value", number);
        return query.getResultList();
    }
}
