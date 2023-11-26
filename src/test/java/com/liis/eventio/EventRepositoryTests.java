package com.liis.eventio;

import com.liis.eventio.event.Event;
import com.liis.eventio.event.EventsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class EventRepositoryTests {
    @Autowired
    private EventsRepository eventsRepository;
    private final Integer eventId = 1;

    @Test
    public void testAddNewEvent(){
        Event event = new Event();
        event.setName("Kassinäitus test");
        event.setLocation("Planeet Maa");
        event.setDate(new Date());
        Event savedEvent = eventsRepository.save(event);

        Assertions.assertNotNull(savedEvent);
        Assertions.assertTrue(savedEvent.getId() >= 1);
    }

    @Test
    public void testEventsUpdate(){
        Event event = eventsRepository.findById(eventId).orElse(null);
        event.setName("Testi trall");
        eventsRepository.save(event);

        Event updatedEvent = eventsRepository.findById(1).orElse(null);
        Assertions.assertEquals(updatedEvent.getName(), "Testi trall");
    }

    @Test
    public void testGet() {
        Event event = eventsRepository.findById(eventId).orElse(null);
        Assertions.assertTrue(event.getId() >= 1);
    }
}
