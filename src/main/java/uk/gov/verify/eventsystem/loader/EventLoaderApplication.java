package uk.gov.verify.eventsystem.loader;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.gov.verify.eventsystem.loader.commands.CreateEventCommand;
import uk.gov.verify.eventsystem.loader.configuration.EventLoaderConfiguration;

public class EventLoaderApplication extends Application<EventLoaderConfiguration> {

    public static void main(String[] args) throws Exception {
        new EventLoaderApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<EventLoaderConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
        bootstrap.addCommand(new CreateEventCommand(this));
    }

    @Override
    public void run(EventLoaderConfiguration configuration, Environment environment) throws Exception {

    }
}
