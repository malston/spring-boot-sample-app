package io.pivotal.springsamples;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;

public abstract class EventRepositoryTest {

    protected abstract EventRepository repository();

    @Test
    public void creatingAndFetchingEvents() throws Exception {
        Event savedEvent = repository().save(new Event("Board game night", LocalDate.of(2016, 11, 5)));

        Event fetchedEvent = repository().find(savedEvent.getId()).get();

        assertThat(fetchedEvent.getTitle(), equalTo("Board game night"));
        assertThat(fetchedEvent.getDate(), equalTo(LocalDate.of(2016, 11, 5)));
    }

    @Test
    public void fetchingEventsInDateRange() throws Exception {

        LocalDate start = LocalDate.of(2020, 3, 1);
        Event event1 = repository().save(new Event("Event 1", start.plusDays(1)));
        Event event2 = repository().save(new Event("Event 2", start.plusDays(2)));
        Event event3 = repository().save(new Event("Event 3", start.plusDays(3)));
        Event event4 = repository().save(new Event("Event 4", start.plusDays(4)));
        Event event5 = repository().save(new Event("Event 5", start.plusDays(5)));

        assertThat(
                repository().findInDateRange(start, start.plusDays(5)),
                containsInAnyOrder(event1, event2, event3, event4, event5)
        );

        assertThat(
                repository().findInDateRange(start, start.plusDays(4)),
                containsInAnyOrder(event1, event2, event3, event4)
        );

        assertThat(
                repository().findInDateRange(start.plusDays(2), start.plusDays(4)),
                containsInAnyOrder(event2, event3, event4)
        );
    }
}