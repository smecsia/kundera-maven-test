package me.smecsia.test.kundera.db.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ilya Sadykov
 *         Date: 27.09.12
 *         Time: 19:06
 */
public class AbstractNosqlDAO<T extends BasicEntity> extends AbstractDAO<T> implements NosqlDAO<T> {
    @PersistenceContext(unitName = "nosql")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
