package me.smecsia.test.kundera.db.dao;

import me.smecsia.test.kundera.db.annotations.EntityContext;
import me.smecsia.test.kundera.db.common.AbstractNosqlDAO;
import me.smecsia.test.kundera.db.entity.ChatLogEntity;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2012 i-Free. All Rights Reserved.
 *
 * @author Ilya Sadykov
 *         Date: 26.09.12
 *         Time: 17:21
 */
@Service
@EntityContext(ChatLogEntity.class)
public class ChatLogEntityDAO extends AbstractNosqlDAO<ChatLogEntity> {

}
