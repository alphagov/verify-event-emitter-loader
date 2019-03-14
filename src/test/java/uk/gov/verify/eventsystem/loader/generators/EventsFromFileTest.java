package uk.gov.verify.eventsystem.loader.generators;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Test;
import uk.gov.ida.eventemitter.Event;
import uk.gov.ida.eventemitter.EventDetailsKey;
import uk.gov.verify.eventsystem.loader.eventgenerators.EventsFromFileGenerator;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class EventsFromFileTest {

    private static final String EVENT_TYPE = "session_event";
    private static final String ORIGINATING_SERVICE = "policy";
    private static final String SESSION_ID = "b6289a9f-1b01-49ec-b3be-cd0aa501e280";

    @Test
    public void testCorrectEventIsGeneratedFromNamespace(){
        ClassLoader classLoader = getClass().getClassLoader();
        EventsFromFileGenerator generator = new EventsFromFileGenerator(classLoader.getResource("test-data.json").getFile());
        List<Event> events = generator.getEvents(new ObjectMapper());

        assertThat(events.size()).isEqualTo(2);
        assertThat(events.get(0).getEventId()).isEqualTo(UUID.fromString("b27fb3f0-c955-429d-9a53-e3ece111fd20"));
        assertThat(events.get(0).getEventType()).isEqualTo(EVENT_TYPE);
        assertThat(events.get(0).getOriginatingService()).isEqualTo(ORIGINATING_SERVICE);
        assertThat(events.get(0).getSessionId()).isEqualTo(SESSION_ID);
        assertThat(events.get(0).getTimestamp()).isEqualTo(new DateTime(1546324200000L));
        assertThat(events.get(0).getDetails()).containsKey(EventDetailsKey.session_event_type);
        assertThat(events.get(0).getDetails().get(EventDetailsKey.session_event_type)).isEqualTo("session_started");

        assertThat(events.get(1).getEventId()).isEqualTo(UUID.fromString("9b940355-36db-44b4-bee8-b13de18ae150"));
        assertThat(events.get(1).getEventType()).isEqualTo(EVENT_TYPE);
        assertThat(events.get(1).getOriginatingService()).isEqualTo(ORIGINATING_SERVICE);
        assertThat(events.get(1).getSessionId()).isEqualTo(SESSION_ID);
        assertThat(events.get(1).getTimestamp()).isEqualTo(new DateTime(1546324220000L));
        assertThat(events.get(1).getDetails()).containsKey(EventDetailsKey.session_event_type);
        assertThat(events.get(1).getDetails().get(EventDetailsKey.session_event_type)).isEqualTo("idp_selected");
    }
}
