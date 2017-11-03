package io.pivotal.springsamples.api;

public class EnvironmentVariableConfigValues implements ConfigValues {
    @Override
    public Integer upcomingEventsWindow() {
        return Integer.parseInt(requiredEnvValue("UPCOMING_EVENTS_WINDOW"));
    }

    @Override
    public String someExternalServiceUrl() {
        return requiredEnvValue("EXTERNAL_SERVICE_URL");
    }

    private String requiredEnvValue(String variableName) {
        String rawValue = System.getenv(variableName);

        if(rawValue == null || "".equals(rawValue)) {
            throw new RuntimeException("Required environment variable " + variableName + " was blank!");
        }

        return rawValue;
    }
}
