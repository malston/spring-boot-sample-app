package io.pivotal.springsamples.sql;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
class EventJpaEntity implements Serializable {

    private String title;
    private LocalDate date;

    @Id
    private String id;

    EventJpaEntity(String id, String title, LocalDate date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    EventJpaEntity() {}

    String getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    LocalDate getDate() {
        return date;
    }
}
