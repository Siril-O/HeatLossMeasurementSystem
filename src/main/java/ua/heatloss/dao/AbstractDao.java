package ua.heatloss.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public abstract class AbstractDao<T> {

    public final static int DEFAULT_START_POSITION = 0;
    public final static int DEFAULT_LIMIT = 5;
    public final static String DEFAULT_LIMIT_STRING = "5";

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

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    protected EntityManager em;

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


    protected T findById(final String id, Class<T> type) {
        if (id != null) {
            return em.find(type, id);
        }
        return null;
    }

    protected List<T> getList(Integer startPosition, Integer maxResults, String queryString, Class<T> type) {
        TypedQuery<T> query = em.createNamedQuery(queryString, type);
        query.setFirstResult(checkStartPosition(startPosition));
        query.setMaxResults(checkMaxResults(maxResults));
        return query.getResultList();
    }

    protected Long getTotalResultCount(String queryString) {
        final Query queryTotal = em.createNamedQuery("House.findHousesTotalResultCount");
        return (long) queryTotal.getSingleResult();
    }

}
