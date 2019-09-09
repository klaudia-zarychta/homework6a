package com.infoshareacademy.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class GenericDao<T, K> {
    private final Class<T> type;

    @PersistenceContext
    private EntityManager entityManager;

    public GenericDao(Class<T> type) {
        super();
        this.type = type;
    }

    public void save(T entity) {
        entityManager.persist(entity);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public T findById(K val) {
        return entityManager.find(type, val);
    }

    public void delete(K val) {
        if (val != null) {
            entityManager.remove(val);
        }
    }

    public List<T> findAll() {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(type);
        Root<T> root = cq.from(type);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }


}