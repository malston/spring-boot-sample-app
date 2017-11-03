package io.pivotal.springsamples.sql;

import io.pivotal.springsamples.Event;
import io.pivotal.springsamples.EventRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SqlBackedEventRepository implements EventRepository {
    private EventJpaRepository jpaRepository;

    public SqlBackedEventRepository(EventJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Event save(Event event) {
        EventJpaEntity savedEntity = jpaRepository.save(new EventJpaEntity(
                UUID.randomUUID().toString(),
                event.getTitle(),
                event.getDate()
        ));

        event.setId(savedEntity.getId());

        return event;
    }

    @Override
    public Optional<Event> find(String id) {
        return Optional.ofNullable(jpaRepository.findOne(id))
                .map(this::fromEntity);
    }

    @Override
    public List<Event> findInDateRange(LocalDate startInclusive, LocalDate endInclusive) {
        return jpaRepository.findByDateBetween(startInclusive, endInclusive).stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    private Event fromEntity(EventJpaEntity entity) {
        return new Event(entity.getId(), entity.getTitle(), entity.getDate());
    }
}
