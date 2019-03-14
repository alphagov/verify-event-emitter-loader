package uk.gov.verify.eventsystem.loader.eventgenerators;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.ida.eventemitter.Event;

import java.util.List;

interface Generator {
    List<Event> getEvents(ObjectMapper objectMapper);
}