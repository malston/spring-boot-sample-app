package io.pivotal.springsamples.fakes;

import io.pivotal.springsamples.Event;
import io.pivotal.springsamples.EventRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryEventRepository implements EventRepository {

    private Map<String, Event> repo = new HashMap<>();

    @Override
    public Event save(Event event) {
        event.setId(UUID.randomUUID().toString());
        repo.put(event.getId(), event);
        return event;
    }

    @Override
    public Optional<Event> find(String id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public List<Event> findInDateRange(LocalDate startInclusive, LocalDate endInclusive) {
        return repo.values().stream()
                .filter(event -> event.getDate().isAfter(startInclusive.minusDays(1)))
                .filter(event -> event.getDate().isBefore(endInclusive.plusDays(1)))
                .collect(Collectors.toList());
    }
}
