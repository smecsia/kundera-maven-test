package me.smecsia.test.kundera.db.annotations;

import me.smecsia.test.kundera.db.common.BasicEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ilya Sadykov
 *         Date: 26.09.12
 *         Time: 18:18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityContext {
    public Class<? extends BasicEntity> value();
}
