package io.pivotal.springsamples;

import io.pivotal.springsamples.fakes.InMemoryEventRepository;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class FetchEventTest {

    private EventRepository eventRepository = new InMemoryEventRepository();
    private FetchEvent fetchEvent = new FetchEvent(eventRepository);

    @Test
    public void givenEventExists_supplyIt() {
        Event existingEvent = eventRepository.save(new Event("Town Hall", LocalDate.now().plusDays(7)));

        Event event = fetchEvent.perform(existingEvent.getId(), new FetchEvent.ResultHandler<Event>() {
            @Override
            public Event foundEvent(Event event) {
                return event;
            }

            @Override
            public Event eventNotFound(String nonexistentEventId) {
                return null;
            }
        });

        assertThat(event, equalTo(existingEvent));
    }

    @Test
    public void givenEventDoesNotExist_reportError() {

        String reportedMissingId = fetchEvent.perform("nonsense-id", new FetchEvent.ResultHandler<String>() {
            @Override
            public String foundEvent(Event event) {
                return null;
            }

            @Override
            public String eventNotFound(String nonexistentEventId) {
                return nonexistentEventId;
            }
        });

        assertThat(reportedMissingId, equalTo("nonsense-id"));
    }
}