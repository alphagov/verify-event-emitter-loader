package uk.gov.verify.eventsystem.loader.generators;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sourceforge.argparse4j.inf.Namespace;
import org.joda.time.DateTime;
import org.junit.Test;
import uk.gov.ida.eventemitter.Event;
import uk.gov.ida.eventemitter.EventDetailsKey;
import uk.gov.verify.eventsystem.loader.eventgenerators.SingleEventFromNamespace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleEventFromNamespaceTest {

    private static final String SESSION_ID = "a-random-session-id";
    private static final String EVENT_TYPE = "session_event";
    private static final String ORIGINATING_SERVICE = "originating-service";
    private static final String DETAILS = "{ \"session_event_type\": \"idp_authn_success\" }";
    private static final String TIMESTAMP = "2019-01-01T06:30:00.000Z";

    @Test
    public void testCorrectEventIsGeneratedFromNamespace(){
        SingleEventFromNamespace generator = new SingleEventFromNamespace(createTestNamespace(false));
        List<Event> events = generator.getEvents(new ObjectMapper());

        assertThat(events.size()).isEqualTo(1);
        assertThat(events.get(0).getEventType()).isEqualTo(EVENT_TYPE);
        assertThat(events.get(0).getOriginatingService()).isEqualTo(ORIGINATING_SERVICE);
        assertThat(events.get(0).getSessionId()).isEqualTo(SESSION_ID);
        assertThat(events.get(0).getTimestamp()).isNotNull();
        assertThat(events.get(0).getDetails()).containsKey(EventDetailsKey.session_event_type);
        assertThat(events.get(0).getDetails().get(EventDetailsKey.session_event_type)).isEqualTo("idp_authn_success");
    }

    @Test
    public void testCorrectEventIsGeneratedFromNamespaceWithTimestampSpecified(){
        SingleEventFromNamespace generator = new SingleEventFromNamespace(createTestNamespace(true));
        List<Event> events = generator.getEvents(new ObjectMapper());

        assertThat(events.size()).isEqualTo(1);
        assertThat(events.get(0).getEventType()).isEqualTo(EVENT_TYPE);
        assertThat(events.get(0).getOriginatingService()).isEqualTo(ORIGINATING_SERVICE);
        assertThat(events.get(0).getSessionId()).isEqualTo(SESSION_ID);
        assertThat(events.get(0).getTimestamp()).isEqualTo(DateTime.parse(TIMESTAMP));
        assertThat(events.get(0).getDetails()).containsKey(EventDetailsKey.session_event_type);
        assertThat(events.get(0).getDetails().get(EventDetailsKey.session_event_type)).isEqualTo("idp_authn_success");
    }

    private Namespace createTestNamespace(boolean includeTimestamp){
        Map<String, Object> values = new HashMap<>();
        values.put("eventType", EVENT_TYPE);
        values.put("sessionId", SESSION_ID);
        values.put("originatingService", ORIGINATING_SERVICE);
        values.put("details", DETAILS);
        if (includeTimestamp) values.put("timestamp", TIMESTAMP);

        return new Namespace(values);
    }
}
