package uk.gov.verify.eventsystem.loader.commands;

import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.cli.EnvironmentCommand;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import uk.gov.ida.eventemitter.Event;
import uk.gov.ida.eventemitter.EventEmitter;
import uk.gov.verify.eventsystem.loader.configuration.EventLoaderConfiguration;
import uk.gov.verify.eventsystem.loader.eventgenerators.SingleEventFromNamespace;
import uk.gov.verify.eventsystem.loader.injectors.SharedInjector;

public class CreateEventCommand extends EnvironmentCommand<EventLoaderConfiguration> {

    public CreateEventCommand(Application application) {
        super(application,"create", "Create a single event and send to event system");
    }

    @Override
    public void configure(Subparser subparser) {
        subparser.addArgument("-c", "--config")
                .dest("file")
                .type(String.class)
                .nargs("?")
                .required(true)
                .help("The file containing the application configuration");
        subparser.addArgument("-t", "--eventType")
                .dest("eventType")
                .type(String.class)
                .required(true)
                .help("The value of the eventType field event");
        subparser.addArgument("-d", "--details")
                .dest("details")
                .type(String.class)
                .required(true)
                .help("The value of the details field of the event, represent as JSON");
        subparser.addArgument("-s", "--sessionId")
                .dest("sessionId")
                .type(String.class)
                .required(true)
                .help("The value of the sessionId field of the event");
        subparser.addArgument("-o", "--originatingService")
                .dest("originatingService")
                .type(String.class)
                .required(true)
                .help("The value of the originatingService field of the event");
        subparser.addArgument("--timestamp")
                .dest("timestamp")
                .type(String.class)
                .required(false)
                .help("The value of the timestamp field of the event");

    }

    @Override
    public void run(Environment environment, Namespace namespace, EventLoaderConfiguration configuration) throws Exception {
        Injector injector = SharedInjector.instance(environment, configuration);
        EventEmitter eventEmitter = injector.getInstance(EventEmitter.class);

        SingleEventFromNamespace generator = new SingleEventFromNamespace(namespace);

        for(Event event: generator.getEvents(environment.getObjectMapper())) {
            eventEmitter.record(event);
        }
    }
}