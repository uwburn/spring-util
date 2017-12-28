package it.mgt.util.spring.repository;

import java.util.List;

public interface BaseRepository<T, K> {

    K getKey(T entity);

    void setKey(T entity, K key);

    void persist(T entity);

    T merge(T entity);

    T saveOrUpdate(T entity);

    void remove(T entity);

    void refresh(T entity);

    List<T> find();

    List<T> find(Integer page, Integer pageSize);

    T find(K id);

    Number count();
}
