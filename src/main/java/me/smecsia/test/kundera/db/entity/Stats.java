package me.smecsia.test.kundera.db.entity;

import me.smecsia.test.kundera.db.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import static me.smecsia.test.kundera.util.UUIDUtil.timeUUID;

/**
 * @author Ilya Sadykov
 */
@Entity
@Table(name = "Stats", schema = "app@nosql")
public class Stats extends AbstractEntity {

    @EmbeddedId
    protected StatId statId;

    @Column(name = "data")
    protected String data;

    public Stats(String subject, Long id, String event, String data) {
        this(subject, id, event);
        this.data = data;
    }

    public Stats(String subject, Long id, String event) {
        this.statId = new StatId(subject, String.valueOf(id), event, timeUUID());
    }

    public Stats() {
    }

    public void setData(String data) {
        this.data = data;
    }

    public StatId getStatId() {
        return statId;
    }

    public String getData() {
        return data;
    }
}
