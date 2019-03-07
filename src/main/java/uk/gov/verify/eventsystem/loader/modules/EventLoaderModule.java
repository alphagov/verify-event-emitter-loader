package uk.gov.verify.eventsystem.loader.modules;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import io.dropwizard.setup.Environment;
import uk.gov.ida.eventemitter.Configuration;
import uk.gov.verify.eventsystem.loader.configuration.EventEmitterConfiguration;
import uk.gov.verify.eventsystem.loader.configuration.EventLoaderConfiguration;
import uk.gov.verify.eventsystem.loader.encyption.KeyDecryptor;

import javax.inject.Inject;
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
    @Singleton
    private AWSKMS getAmazonKMSClient(){
        return AWSKMSClientBuilder.defaultClient();
    }

    @Provides
    private Configuration getEventEmitterConfiguration(final EventLoaderConfiguration configuration, KeyDecryptor keyDecryptor) {
        EventEmitterConfiguration config = configuration.getEventEmitterConfiguration();
        config.setKeyDecryptor(keyDecryptor);
        return config;
    }

}
