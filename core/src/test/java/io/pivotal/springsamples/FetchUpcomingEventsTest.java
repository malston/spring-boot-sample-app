package io.pivotal.springsamples;

import io.pivotal.springsamples.fakes.InMemoryEventRepository;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class FetchUpcomingEventsTest {

    private EventRepository eventRepository = new InMemoryEventRepository();

    @Test
    public void givenSomePast_someUpcoming_andSomeFarFutureEvents_suppliesOnlyUpcomingEvents() throws Exception {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfterTomorrow = today.plusDays(2);

        eventRepository.save(new Event("Today", yesterday));
        Event eventToday = eventRepository.save(new Event("Today", today));
        Event eventTomorrow = eventRepository.save(new Event("Today", tomorrow));
        Event eventDayAfterTomorrow = eventRepository.save(new Event("Today", dayAfterTomorrow));

        assertThat(
                new FetchUpcomingEvents(eventRepository, 1).perform(today, events1 -> events1),
                containsInAnyOrder(eventToday, eventTomorrow)
        );

        assertThat(
                new FetchUpcomingEvents(eventRepository, 2).perform(today, events -> events),
                containsInAnyOrder(eventToday, eventTomorrow, eventDayAfterTomorrow)
        );
    }
}