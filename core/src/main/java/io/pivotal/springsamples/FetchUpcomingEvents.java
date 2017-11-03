package io.pivotal.springsamples;

import java.time.LocalDate;
import java.util.List;

public class FetchUpcomingEvents {
    private EventRepository eventRepository;
    private int upcomingWindow;

    public FetchUpcomingEvents(EventRepository eventRepository, int upcomingWindow) {
        this.eventRepository = eventRepository;
        this.upcomingWindow = upcomingWindow;
    }

    public <T> T perform(LocalDate today, ResultHandler<T> handler) {
        return handler.foundEvents(eventRepository.findInDateRange(today, today.plusDays(upcomingWindow)));
    }

    public interface ResultHandler<T> {
        T foundEvents(List<Event> events);
    }
}
