package me.smecsia.test.kundera.db.common;

import me.smecsia.test.kundera.db.annotations.EntityContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import java.util.List;

/**
 * Copyright (c) 2012 i-Free. All Rights Reserved.
 *
 * @author Ilya Sadykov
 *         Date: 26.09.12
 *         Time: 18:14
 */
@Lazy
@Service
@Transactional
public abstract class AbstractDAO<T extends BasicEntity> implements BasicDAO<T> {

    final protected Class<T> entityContextClass;

    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    protected AbstractDAO() {
        if (getClass().getAnnotation(EntityContext.class) != null) {
            entityContextClass = (Class<T>) (getClass().getAnnotation(EntityContext.class)).value();
        } else {
            entityContextClass = null;
        }
    }

    protected AbstractDAO(Class<T> entityContextClass) {
        this.entityContextClass = entityContextClass;
    }

    @Override
    public synchronized EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = getEntityManagerFactory().createEntityManager();
            entityManager.setFlushMode(FlushModeType.COMMIT);
        }
        return entityManager;
    }

    @Override
    public T find(Object id) {
        return entityManager.find(entityContextClass, id);
    }

    @Override
    public void save(T entity) {
        EntityTransaction t = getEntityManager().getTransaction();
        t.begin();
        getEntityManager().persist(entity);
        t.commit();
    }

    @Override
    public void deleteAll() {
        EntityTransaction t = getEntityManager().getTransaction();
        t.begin();
        getEntityManager().createQuery("delete  from " + entityContextClass.getSimpleName() + " t").executeUpdate();
        t.commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> all() {
        return getEntityManager().createQuery("from " + entityContextClass.getSimpleName() + " t").getResultList();
    }
}
