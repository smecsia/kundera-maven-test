package me.smecsia.test.kundera.db.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Ilya Sadykov
 */
@Embeddable
public class StatId implements Serializable {
    @Column
    private String subject;

    @Column
    private String id;

    @Column
    private String event;

    @Column
    private UUID timeUUID;

    public StatId(String subject, String id, String event, UUID timeUUID) {
        this.subject = subject;
        this.id = id;
        this.event = event;
        this.timeUUID = timeUUID;
    }

    public StatId() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getTimeUUID() {
        return timeUUID;
    }

    public void setTimeUUID(UUID timeUUID) {
        this.timeUUID = timeUUID;
    }
}
