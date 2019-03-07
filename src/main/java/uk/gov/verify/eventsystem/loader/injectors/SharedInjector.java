package uk.gov.verify.eventsystem.loader.injectors;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import io.dropwizard.setup.Environment;
import uk.gov.ida.eventemitter.EventEmitterModule;
import uk.gov.verify.eventsystem.loader.modules.EventLoaderModule;
import uk.gov.verify.eventsystem.loader.configuration.EventLoaderConfiguration;

public class SharedInjector {

    public static Injector instance(Environment environment, EventLoaderConfiguration configuration){
        ImmutableSet.Builder<Module> modulesBuilder =
            ImmutableSet.<Module>builder()
                .add(new EventEmitterModule())
                .add(new EventLoaderModule())
                .add(new Module() {
                    @Override
                    public void configure(final Binder binder) {
                        binder.bind(Environment.class).toInstance(environment);
                        binder.bind(EventLoaderConfiguration.class).toInstance(configuration);
                    }
                });
        return Guice.createInjector(Stage.PRODUCTION, modulesBuilder.build());
    }
}
