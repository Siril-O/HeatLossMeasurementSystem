package ua.heatloss.dao;

import javax.persistence.*;
import java.util.List;

public abstract class AbstractDao<T> {

    public final static int DEFAULT_START_POSITION = 0;
    public final static int DEFAULT_LIMIT = 5;
    public final static String DEFAULT_LIMIT_STRING = "5";

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    protected EntityManager em;

    public static int checkStartPosition(final Integer startPosition) {
        if (startPosition == null) {
            return DEFAULT_START_POSITION;
        }
        return startPosition;
    }

    public static int checkMaxResults(final Integer maxResults) {
        if (maxResults == null) {
            return DEFAULT_LIMIT;
        }
        return maxResults;
    }

    protected T persist(T entity, boolean create) {
        if (entity == null) {
            return null;
        }
        if (create) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
        return entity;
    }


    protected T findById(final Long id, Class<? extends T> type) {
        if (id != null) {
            return em.find(type, id);
        }
        return null;
    }

    protected <H extends T> List<H> getList(Integer startPosition, Integer maxResults, String queryString, Class<H> type) {
        TypedQuery<H> query = em.createNamedQuery(queryString, type);
        query.setFirstResult(checkStartPosition(startPosition));
        query.setMaxResults(checkMaxResults(maxResults));
        return query.getResultList();
    }



    protected Long getTotalResultCount(String queryString) {
        final Query queryTotal = em.createNamedQuery(queryString);
        return (long) queryTotal.getSingleResult();
    }


}
