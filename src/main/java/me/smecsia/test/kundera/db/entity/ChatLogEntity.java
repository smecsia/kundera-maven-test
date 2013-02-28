package me.smecsia.test.kundera.db.entity;

import me.smecsia.test.kundera.db.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Ilya Sadykov
 *         Date: 26.09.12
 *         Time: 17:20
 */
@Entity
@Table(name = "ChatLog", schema = "app@nosql")
public class ChatLogEntity extends AbstractEntity {
    @Id
    @Column(name = "ChatLogId")
    private String id;

    @Column(name = "createdAt")
    private Date createdAt;

    public ChatLogEntity() {
    }

    public String getId() {
        return id;
    }

    public ChatLogEntity(String id) {
        this.id = id;
        this.createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
