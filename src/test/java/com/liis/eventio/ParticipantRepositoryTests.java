package com.liis.eventio;

import com.liis.eventio.event.Event;
import com.liis.eventio.event.EventsRepository;
import com.liis.eventio.participant.Company;
import com.liis.eventio.participant.Participant;
import com.liis.eventio.participant.ParticipantRepository;
import com.liis.eventio.participant.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ParticipantRepositoryTests {
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private EventsRepository eventRepository;
    private final Integer eventId = 1;
    private final Integer participantId = 1;

    private final Integer participantId2 = 2;

    @Test
    public void testAddNewCompany(){
        Company company = new Company("Vaibavabrik", "8927428472942", 5, "sularaha", "pole mitte midagi öelda");
        Company savedCompany = participantRepository.save(company);

        Assertions.assertNotNull(savedCompany);
        Assertions.assertFalse(savedCompany.getName().isEmpty());
        Assertions.assertFalse(savedCompany.getRegistryCode().isEmpty());
        Assertions.assertFalse(savedCompany.getPaymentMethod().isEmpty());
        Assertions.assertTrue(savedCompany.getParticipantId() > 0);
    }

    @Test
    public void testAddNewPerson(){
        Person person = new Person("Mari", "Maasikas", "49002280355", "sula", "mitte midagi");
        Person savedPerson = participantRepository.save(person);

        Assertions.assertNotNull(savedPerson);
        Assertions.assertFalse(savedPerson.getFirstName().isEmpty());
        Assertions.assertFalse(savedPerson.getLastName().isEmpty());
        Assertions.assertFalse(savedPerson.getPersonalCode().isEmpty());
        Assertions.assertTrue(savedPerson.getParticipantId() > 0);
    }

    @Test
    public void testAddParticipantToEvent(){
        Event event = eventRepository.findById(eventId).orElse(null);
        Participant participant = participantRepository.findById(participantId).orElse(null);
        List<Participant> participantList = event.getParticipants();
        participantList.add(participant);
        event.setParticipants(participantList);
        Event updatedEvent = eventRepository.save(event);

        Assertions.assertFalse(updatedEvent.getParticipants().isEmpty());
    }

    @Test
    public void testAddPrivatePersonParticipantToEvent(){
        Event event = eventRepository.findById(eventId).orElse(null);
        Participant participant = participantRepository.findById(participantId2).orElse(null);
        List<Participant> participantList = event.getParticipants();
        participantList.add(participant);
        event.setParticipants(participantList);
        Event updatedEvent = eventRepository.save(event);

        Assertions.assertFalse(updatedEvent.getParticipants().isEmpty());
    }
}
