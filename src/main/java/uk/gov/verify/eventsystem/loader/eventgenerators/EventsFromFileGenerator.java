package uk.gov.verify.eventsystem.loader.eventgenerators;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.ida.eventemitter.Event;
import uk.gov.verify.eventsystem.loader.domain.LoadEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EventsFromFileGenerator implements Generator{

    private String filename;

    public EventsFromFileGenerator(String filename){
        this.filename = filename;
    }

    @Override
    public List<Event> getEvents(ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(new File(filename), new TypeReference<List<LoadEvent>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
