package ua.heatloss.dao;

import java.util.List;


public interface CrudDao<T> {

    void create(final T entity);

    void update(final T entity);

    T findById(final Long id);

    List<T> getList(final Integer startPosition, final Integer maxResults);

    Long getTotalResultCount();

    void refresh(T entity);
}
