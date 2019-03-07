package uk.gov.verify.eventsystem.loader.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import uk.gov.ida.eventemitter.Event;
import uk.gov.ida.eventemitter.EventDetailsKey;

import java.util.EnumMap;
import java.util.UUID;

public class LoadEvent implements Event {

    @JsonProperty
    private UUID eventId;

    @JsonProperty
    private DateTime timestamp = DateTime.now();

    @JsonProperty
    private String originatingService;

    @JsonProperty
    private String sessionId;

    @JsonProperty
    private String eventType;

    @JsonProperty
    private EnumMap<EventDetailsKey, String> details;

    public LoadEvent() {}

    public LoadEvent(UUID eventId, DateTime timestamp, String originatingService, String sessionId, String eventType, EnumMap<EventDetailsKey, String> details) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.originatingService = originatingService;
        this.sessionId = sessionId;
        this.eventType = eventType;
        this.details = details;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public DateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public EnumMap<EventDetailsKey, String> getDetails() {
        return details;
    }

    @Override
    public String getOriginatingService() {
        return originatingService;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

}
