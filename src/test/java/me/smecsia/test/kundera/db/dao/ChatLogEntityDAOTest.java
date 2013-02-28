package me.smecsia.test.kundera.db.dao;

import me.smecsia.test.kundera.SpringAndCassandraSupportedTest;
import me.smecsia.test.kundera.db.entity.ChatLogEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Ilya Sadykov
 *         Date: 02.10.12
 *         Time: 18:18
 */
@Transactional
public class ChatLogEntityDAOTest extends SpringAndCassandraSupportedTest {
    @Autowired
    private ChatLogEntityDAO dao;

    @Test
    public void testChatLog() {
        ChatLogEntity chatLog = new ChatLogEntity("1");
        dao.save(chatLog);
        ChatLogEntity foundEntity = dao.find(chatLog.getId());
        assertNotNull("Entity should be found by standard find", foundEntity);
        assertEquals("Created at must be equal to source created at", chatLog.getCreatedAt(), foundEntity.getCreatedAt());
    }
}
