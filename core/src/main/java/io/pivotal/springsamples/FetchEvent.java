package io.pivotal.springsamples;

import java.util.Optional;

public class FetchEvent {
    private EventRepository eventRepository;

    public FetchEvent(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public <T> T perform(String id, ResultHandler<T> resultHandler) {
        Optional<Event> event = eventRepository.find(id);

        if(event.isPresent()) {
            return resultHandler.foundEvent(event.get());
        } else {
            return resultHandler.eventNotFound(id);
        }
    }

    public interface ResultHandler<T> {
        T foundEvent(Event event);
        T eventNotFound(String nonexistentEventId);
    }
}
