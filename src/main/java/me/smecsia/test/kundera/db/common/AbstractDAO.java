package me.smecsia.test.kundera.db.common;

import me.smecsia.test.kundera.db.annotations.EntityContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
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
    public T find(Object id) {
        return getEntityManager().find(entityContextClass, id);
    }

    @Override
    public void save(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void deleteAll() {
        getEntityManager().createQuery("delete  from " + entityContextClass.getSimpleName() + " t").executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> all() {
        return getEntityManager().createQuery("from " + entityContextClass.getSimpleName() + " t").getResultList();
    }
}
