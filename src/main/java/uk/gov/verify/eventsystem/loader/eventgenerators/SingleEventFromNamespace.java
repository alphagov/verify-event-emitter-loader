package uk.gov.verify.eventsystem.loader.eventgenerators;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sourceforge.argparse4j.inf.Namespace;
import org.joda.time.DateTime;
import uk.gov.ida.eventemitter.Event;
import uk.gov.ida.eventemitter.EventDetailsKey;
import uk.gov.verify.eventsystem.loader.domain.LoadEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.UUID;

public class SingleEventFromNamespace implements Generator{

    private Namespace namespace;

    public SingleEventFromNamespace(Namespace namespace){
        this.namespace = namespace;
    }

    @Override
    public List<Event> getEvents(ObjectMapper objectMapper) {
        List<Event> events = new ArrayList<>();
        DateTime timestamp = DateTime.now();
        String timestampArg = namespace.getString("timestamp");
        String sessionId = namespace.getString("sessionId");
        if ( timestampArg != null ){
            timestamp = DateTime.parse(timestampArg);
        }
        try {
            events.add(new LoadEvent(
                    UUID.randomUUID(),
                    timestamp,
                    namespace.getString("originatingService"),
                    sessionId.isEmpty() ? UUID.randomUUID().toString() : sessionId,
                    namespace.getString("eventType"),
                    objectMapper.readValue(namespace.getString("details"), new TypeReference<EnumMap<EventDetailsKey, String>>() {} )
                )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }
}
