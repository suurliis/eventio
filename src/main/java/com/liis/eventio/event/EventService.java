package com.liis.eventio.event;

import com.liis.eventio.participant.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EventService {
    @Autowired
    private EventsRepository eventsRepository;

    public List<Event> findAllEvents()
        {
            return (List<Event>) eventsRepository.findAll();
        }

    static Logger logger = Logger.getLogger(EventService.class.getName());

    public Event addEvent(Event event)
        {
            logger.log(Level.INFO, "Added one new event: " + event);
            return event;
        }

    public void save(Event event)
        {
            logger.log(Level.INFO, "Added one new event: " + event);
            eventsRepository.save(event);
        }

    public Event get(Integer id) throws EventNotFoundException
        {
            return eventsRepository.findById(id).orElseThrow();
        }

    public void delete(Integer id) throws EventNotFoundException
        {
            Long count = eventsRepository.countById(id);
            if (count == null || count == 0) {
                throw new EventNotFoundException("Could not find any event with ID " + id);
            }
            eventsRepository.deleteById(id);
        }
}
