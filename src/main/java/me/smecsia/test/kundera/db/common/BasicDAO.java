package me.smecsia.test.kundera.db.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Copyright (c) 2012 i-Free. All Rights Reserved.
 *
 * @author Ilya Sadykov
 *         Date: 26.09.12
 *         Time: 18:33
 */
public interface BasicDAO<T> {
    void save(T entity);

    T find(Object id);

    List<T> all();

    void deleteAll();

    EntityManagerFactory getEntityManagerFactory();

    EntityManager getEntityManager();
}
