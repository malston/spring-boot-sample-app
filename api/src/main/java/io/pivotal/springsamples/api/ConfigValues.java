package io.pivotal.springsamples.api;

/* The ConfigValues interface defines all the values that parts of the application will use that
 * are drawn from environmental configuration. Production implementations of this interface can
 * pull these values from Spring application properties, environment variables, or other sources
 * (Spring has plenty of mechanisms for accessing property values).
 *
 * The benefit to defining an interface like this, and having the application always pull values
 * from an implementation of the interface instead of accessing them directly, is that it provides
 * a canonical list of all the environmental config necessary to start up the application
 * (which helps in those moments of "Why is my new contract test falling over?......Ah, we forgot
 * to set a property in application-contracttest.properties").
 *
 * It also reduces the number of Spring techniques you need to be familiar with to quickly
 * construct a configuration environment for a test; rather than worrying about how the config
 * values are fetched in real life, you can always provide a hard-coded implementation of the
 * interface with values that suit your test.
 */
public interface ConfigValues {
    Integer upcomingEventsWindow();
    String someExternalServiceUrl();
}
