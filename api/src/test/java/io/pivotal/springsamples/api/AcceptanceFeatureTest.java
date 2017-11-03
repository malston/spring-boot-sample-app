package io.pivotal.springsamples.api;

import org.junit.Ignore;

@Ignore("There is no deployed instance of the application to run against, but if there was, this class could run the feature test suite against it.")
public class AcceptanceFeatureTest extends FeatureTest {
    @Override
    protected String rootUrl() {
        return "http://www.example.com/eventapi";
    }

    @Override
    protected Integer upcomingWindow() {
        return 5; // whatever the upcoming window is configured to in Acceptance
    }
}
