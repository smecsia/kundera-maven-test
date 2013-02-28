package me.smecsia.test.kundera.db.dao;

import me.smecsia.test.kundera.db.annotations.EntityContext;
import me.smecsia.test.kundera.db.common.AbstractNosqlDAO;
import me.smecsia.test.kundera.db.entity.Stats;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ilya Sadykov
 *         Date: 26.09.12
 *         Time: 17:21
 */
@Service
@EntityContext(Stats.class)
public class StatsDAO extends AbstractNosqlDAO<Stats> {

}
