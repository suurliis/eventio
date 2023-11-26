package com.liis.eventio.participant;
import com.liis.eventio.event.Event;
import com.liis.eventio.event.EventNotFoundException;
import com.liis.eventio.event.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EventsRepository eventsRepository;

    public List<Participant> findAllParticipants()
        {
            return (List<Participant>) participantRepository.findAll();
        }

    static Logger logger = Logger.getLogger(ParticipantService.class.getName());

    public Participant addParticipant(Participant participant)
        {
            logger.log(Level.INFO, "Added one new participant: " + participant);
            return participant;
        }

    public void saveParticipant(Participant participant)
        {
            logger.log(Level.INFO, "Added one new participant: " + participant);
            participantRepository.save(participant);
        }

    public Person addPerson(Person person, Integer id)
        {
            Event event = eventsRepository.findById(id).orElseThrow();

            logger.log(Level.INFO, "Added one new person: " + person);
            return person;
        }

    public void savePerson(Person person)
        {
            logger.log(Level.INFO, "Added one new person: " + person);
            participantRepository.save(person);
        }

    public void saveCompany(Company company)
        {
            logger.log(Level.INFO, "Added one new company: " + company);
            participantRepository.save(company);
        }

    public Participant get(Integer id) throws ParticipantNotFoundException
        {
            return participantRepository.findById(id).orElseThrow();
        }

    public Event addParticipantToEvent(Participant participant, Integer eventId) throws EventNotFoundException,
            ParticipantNotFoundException
        {
            if (!eventsRepository.existsById(eventId)) {
                throw new EventNotFoundException("Event with Id " + eventId + " not found");
            }
            Event event =
                    eventsRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event with Id " + eventId + " not found"));
            Participant newParticipant =
                    participantRepository.findById(participant.participantId).orElseThrow(() -> new ParticipantNotFoundException(
                            "Participant with id " + participant.participantId +
                            " was not found"));
            List<Participant> participants = event.getParticipants();
            participants.add(newParticipant);
            event.setParticipants(participants);
            return eventsRepository.save(event);
        }

    public void delete(Integer id)
        {
        }
}
