package me.smecsia.test.kundera.db.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManagerFactory;

/**
 * FIXME: type in the purpose of this class here
 * Copyright (c) 2012 i-Free. All Rights Reserved.
 *
 * @author Ilya Sadykov
 *         Date: 27.09.12
 *         Time: 19:06
 */
public class AbstractNosqlDAO<T extends BasicEntity> extends AbstractDAO<T> implements NosqlDAO<T> {
    @Autowired
    @Qualifier("nosqlEntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

}
