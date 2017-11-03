package io.pivotal.springsamples.sql;

import io.pivotal.springsamples.EventRepository;
import io.pivotal.springsamples.EventRepositoryTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SqlBackedEventRepositoryTest extends EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Override
    protected EventRepository repository() {
        return eventRepository;
    }

    @Configuration
    @EnableAutoConfiguration // discovers EventJpaRepository (and h2) on the classpath and makes it available
    static class Config { // must not be private
        @Bean
        public EventRepository eventRepository(EventJpaRepository jpaRepository) {
            return new SqlBackedEventRepository(jpaRepository);
        }
    }
}