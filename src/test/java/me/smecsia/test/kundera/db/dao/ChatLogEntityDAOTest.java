package me.smecsia.test.kundera.db.dao;

import me.smecsia.test.kundera.SpringAndCassandraSupportedTest;
import me.smecsia.test.kundera.db.entity.ChatLogEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertNotNull;

/**
 * Copyright (c) 2012 i-Free. All Rights Reserved.
 *
 * @author Ilya Sadykov
 *         Date: 02.10.12
 *         Time: 18:18
 */
public class ChatLogEntityDAOTest extends SpringAndCassandraSupportedTest {
    @Autowired
    private ChatLogEntityDAO dao;

    @Test
    public void testChatLog() {
        dao.deleteAll();
        ChatLogEntity chatLog = new ChatLogEntity(1L);
        dao.save(chatLog);
        assertNotNull("Entity should be found by standard find", dao.find(chatLog.getId()));
    }
}
