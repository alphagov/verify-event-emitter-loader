package uk.gov.verify.eventsystem.loader.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.setup.Environment;
import uk.gov.ida.eventemitter.Configuration;
import uk.gov.verify.eventsystem.loader.configuration.EventLoaderConfiguration;

import javax.inject.Singleton;

public class EventLoaderModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    private ObjectMapper getObjectMapper(Environment environment) {
        return environment.getObjectMapper();
    }

    @Provides
    private Configuration getEventEmitterConfiguration(final EventLoaderConfiguration configuration) {
        return configuration.getEventEmitterConfiguration();
    }
}
