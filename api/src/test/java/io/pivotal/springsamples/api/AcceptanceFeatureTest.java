package io.pivotal.springsamples.api;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Value;

@Ignore("There is no deployed instance of the application to run against, but if there was, this class could run the feature test suite against it.")
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = AcceptanceFeatureTest.class,
//        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AcceptanceFeatureTest extends FeatureTest {
    @Value("${application.url:localhost:8080}") String applicationUrl;

    @Override
    protected String rootUrl() {
        return "http://" + applicationUrl;
    }

    @Override
    protected Integer upcomingWindow() {
        return 5; // whatever the upcoming window is configured to in Acceptance
    }
}
