package uk.gov.verify.eventsystem.loader.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;

public class EventLoaderConfiguration extends Configuration {

    @Valid
    @JsonProperty
    private EventEmitterConfiguration eventEmitterConfiguration;

    public EventEmitterConfiguration getEventEmitterConfiguration() {
        return eventEmitterConfiguration;
    }
}
