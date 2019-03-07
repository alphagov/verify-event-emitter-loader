package uk.gov.verify.eventsystem.loader.eventgenerators;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sourceforge.argparse4j.inf.Namespace;
import org.joda.time.DateTime;
import uk.gov.ida.eventemitter.Event;
import uk.gov.ida.eventemitter.EventDetailsKey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SingleEventFromNamespace implements Generator{

    private Namespace namespace;

    public SingleEventFromNamespace(Namespace namespace){
        this.namespace = namespace;
    }

    @Override
    public List<Event> getEvents(ObjectMapper objectMapper) {
        List<Event> events = new ArrayList<>();
        events.add(new Event() {
            @Override
            public UUID getEventId() {
                return UUID.randomUUID();
            }

            @Override
            public DateTime getTimestamp() {
                String timestamp = namespace.getString("timestamp");
                if ( timestamp == null ){
                    return DateTime.now();
                }else{
                    return DateTime.parse(timestamp);
                }
            }

            @Override
            public String getEventType() {
                return namespace.getString("eventType");
            }

            @Override
            public EnumMap<EventDetailsKey, String> getDetails(){
                EnumMap<EventDetailsKey, String> result = new EnumMap<EventDetailsKey, String>(EventDetailsKey.class);
                try {
                    Map<String, String> parsed = objectMapper.readValue(namespace.getString("details"), Map.class);
                    for(Map.Entry<String, String> entry: parsed.entrySet()){
                        result.put(Enum.valueOf(EventDetailsKey.class, entry.getKey()), entry.getValue());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            public String getOriginatingService() {
                return namespace.getString("originatingService");
            }

            @Override
            public String getSessionId() {
                String sessionId = namespace.getString("sessionId");
                return sessionId.isEmpty() ? UUID.randomUUID().toString() : sessionId;
            }
        });
        return events;
    }
}
