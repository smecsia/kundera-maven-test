package me.smecsia.test.kundera.db.dao;

import me.smecsia.test.kundera.SpringAndCassandraSupportedTest;
import me.smecsia.test.kundera.db.entity.Stats;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author Ilya Sadykov
 *         Date: 02.10.12
 *         Time: 18:18
 */
@Transactional
public class StatsDAOTest extends SpringAndCassandraSupportedTest {
    @Autowired
    private StatsDAO dao;

    @Test
    public void testStats() {
        List<Stats> newStats = new ArrayList<Stats>();
        for (int i = 0; i < 10; ++i) {
            Stats stats = new Stats("profile", 1L, "someevent", "some data " + i);
            newStats.add(stats);
            dao.save(stats);
        }
        Stats readStats = dao.find(newStats.get(0).getStatId());
        assertNotNull(readStats);
    }
}
