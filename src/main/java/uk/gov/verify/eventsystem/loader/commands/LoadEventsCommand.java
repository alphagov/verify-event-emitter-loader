package uk.gov.verify.eventsystem.loader.commands;

import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.cli.EnvironmentCommand;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.joda.time.DateTime;
import uk.gov.ida.eventemitter.Event;
import uk.gov.ida.eventemitter.EventEmitter;
import uk.gov.verify.eventsystem.loader.configuration.EventLoaderConfiguration;
import uk.gov.verify.eventsystem.loader.domain.LoadEvent;
import uk.gov.verify.eventsystem.loader.eventgenerators.EventsFromFileGenerator;
import uk.gov.verify.eventsystem.loader.injectors.SharedInjector;

import java.util.UUID;

public class LoadEventsCommand extends EnvironmentCommand<EventLoaderConfiguration> {

    public LoadEventsCommand(Application application) {
        super(application,"load", "Load events from file");
    }

    @Override
    public void configure(Subparser subparser) {
        subparser.addArgument("-f", "--file")
                .dest("data")
                .type(String.class)
                .nargs("?")
                .required(true)
                .help("The file containing the events to load");
        subparser.addArgument("-c", "--config")
                .dest("file")
                .type(String.class)
                .nargs("?")
                .required(true)
                .help("The file containing the application configuration");
        subparser.addArgument("--preserve")
                .dest("preserve")
                .type(Boolean.class)
                .setDefault(false)
                .required(false)
                .help("If true the timestamp and event ID in supplied in the file will be preserved");
    }

    @Override
    public void run(Environment environment, Namespace namespace, EventLoaderConfiguration configuration) throws Exception {
        Injector injector = SharedInjector.instance(environment, configuration);
        EventEmitter eventEmitter = injector.getInstance(EventEmitter.class);

        EventsFromFileGenerator generator = new EventsFromFileGenerator(namespace.getString("data"));
        boolean preserve = namespace.getBoolean("preserve");
        for(Event event: generator.getEvents(environment.getObjectMapper())) {
            if (!preserve) {
                ((LoadEvent) event).setEventId(UUID.randomUUID());
                ((LoadEvent) event).setTimestamp(DateTime.now());
            }
            eventEmitter.record(event);
        }
    }
}