package ua.heatloss.services;

import java.util.List;


public interface CrudService<T> {

    void create(final T entity);

    void update(final T entity);

    T findById(final Long id);

    List<T> getList(final Integer startPosition, final Integer maxResults);

    long getTotalResultCount();

    void refresh(T entity);
}
