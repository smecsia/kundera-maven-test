package me.smecsia.test.kundera.db.entity;

import me.smecsia.test.kundera.db.common.AbstractEntity;

import javax.persistence.*;

/**
 * Copyright (c) 2012 i-Free. All Rights Reserved.
 *
 * @author Ilya Sadykov
 *         Date: 26.09.12
 *         Time: 17:20
 */
@Entity
@Table(name = "ChatLog", schema = "aimy@nosql")
public class ChatLogEntity extends AbstractEntity {
    @Id
    @Column(name = "ChatLogId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public ChatLogEntity() {
    }

    public Long getId() {
        return id;
    }

    public ChatLogEntity(Long id) {
        this.id = id;
    }
}
