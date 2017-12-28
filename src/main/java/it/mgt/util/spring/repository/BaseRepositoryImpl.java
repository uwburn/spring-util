package it.mgt.util.spring.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class BaseRepositoryImpl<T, K> implements BaseRepository<T, K> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<T> entityClass;

    public BaseRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void persist(T entity) {
        em.persist(entity);
    }

    @Override
    public T merge(T entity) {
        return  em.merge(entity);
    }

    @Override
    public T saveOrUpdate(T entity) {
        if (getKey(entity) != null)
            return em.merge(entity);

        em.persist(entity);
        return entity;
    }

    @Override
    public void remove(T entity) {
        em.remove(entity);
    }

    @Override
    public void refresh(T entity) {
        em.refresh(entity);
    }

    @Override
    public List<T> find() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> rootEntry = cq.from(entityClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public List<T> find(Integer page, Integer pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> rootEntry = cq.from(entityClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);

        if (page != null && pageSize != null) {
            allQuery.setFirstResult(page * pageSize);
            allQuery.setMaxResults(pageSize);
        }

        return allQuery.getResultList();
    }

    @Override
    public T find(K id) {
        return em.find(entityClass, id);
    }

    @Override
    public Number count() {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Number> cq = qb.createQuery(Number.class);
        cq.select(qb.count(cq.from(entityClass)));
        return em.createQuery(cq).getSingleResult();
    }
}
