package org.irisi.laboeasyseek.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class GenericDao<T> {

    private Class<T> type;

    private EntityManager em;
    private EntityTransaction et;

    public GenericDao() {
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();
    }

    public GenericDao(Class<T> type) {
        this.type = type;
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();
    }

    public void save(T entity) {
        EntityManager em = getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(entity);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
    }

    public void update(T entity) {
        EntityManager em = getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(entity);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
    }

    public void delete(T entity) {
        EntityManager em = getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(entity);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
    }

    public T findById(Long id) {
        EntityManager em = getEntityManager();
        return em.find(type, id);
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public List<T> findAll(String search) {
        EntityManager em = getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        if (search != null && !search.isEmpty()) {
            Predicate predicateTitle = criteriaBuilder.like(root.get("title"), "%" + search + "%", '\\');
            Predicate predicateDescription = criteriaBuilder.like(root.get("description"), "%" + search + "%", '\\');
            Predicate predicateContent = criteriaBuilder.like(root.get("content"), "%" + search + "%", '\\');
            Predicate predicateVersion = criteriaBuilder.like(root.get("version"), "%" + search + "%", '\\');
            Predicate predicateSearch = criteriaBuilder.or(predicateTitle, predicateDescription, predicateContent, predicateVersion);
            criteriaQuery.where(predicateSearch);
        }
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public EntityManager getEntityManager() {
        return em;
    }
}