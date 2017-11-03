package io.pivotal.springsamples;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository {
    Event save(Event event);
    Optional<Event> find(String id);

    List<Event> findInDateRange(LocalDate startInclusive, LocalDate endInclusive);
}
