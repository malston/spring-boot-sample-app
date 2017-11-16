package io.pivotal.springsamples.api;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = LocalFeatureTest.Config.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class LocalFeatureTest extends FeatureTest {

    @Value("${local.server.port}")
    private String port;

    private static Integer UPCOMING_EVENTS_WINDOW = 1;

    @Override
    protected String rootUrl() {
        return "http://localhost:" + port;
    }

    @Override
    protected Integer upcomingWindow() {
        return UPCOMING_EVENTS_WINDOW;
    }

    /* Instead of an inner configuration class like this, we could set up a completely separate class marked
     * with a relevant Spring profile. The advantage to putting the class here is that it makes it more obvious
     * where the test depends on particular values in the configuration (note how we were able to use the
     * UPCOMING_EVENTS_WINDOW constant), and it avoids having too many tests cluttering a common test profile
     * with competing needs.
     */
//    @Configuration
//    @Import(Application.class) // Take the production config from Application with the following overrides
    @TestConfiguration // This does the same thing, but unlike a nested @Configuration class which would be used instead
                       // of a your application's primary configuration, a nested @TestConfiguration class will be used
                       // in addition to your application's primary configuration.
    static class Config {
        @Bean
        public ConfigValues configValues() {
            return new ConfigValues() {
                @Override
                public Integer upcomingEventsWindow() {
                    return UPCOMING_EVENTS_WINDOW;
                }

                @Override
                public String someExternalServiceUrl() {
                    return "http://www.example.com/api";
                }
            };
        }
    }
}
