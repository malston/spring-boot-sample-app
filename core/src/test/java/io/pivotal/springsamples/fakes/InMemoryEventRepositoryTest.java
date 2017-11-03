package io.pivotal.springsamples.fakes;

import io.pivotal.springsamples.EventRepository;
import io.pivotal.springsamples.EventRepositoryTest;

public class InMemoryEventRepositoryTest extends EventRepositoryTest {

    private EventRepository eventRepository = new InMemoryEventRepository();

    @Override
    protected EventRepository repository() {
        return eventRepository;
    }
}