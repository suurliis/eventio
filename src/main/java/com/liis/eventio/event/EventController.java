package com.liis.eventio.event;

import com.liis.eventio.participant.Company;
import com.liis.eventio.participant.Participant;
import com.liis.eventio.participant.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;
    static Logger logger = Logger.getLogger(EventController.class.getName());

    @GetMapping("")
    public String showHomePage(Model model){
        List<Event> listEvents = eventService.findAllEvents();
        List<Event> pastEvents = listEvents.stream().filter(event -> event.getDate().before(new Date())).toList();
        List<Event> presentEvents =
                listEvents.stream().filter(event -> event.getDate().after(new Date())).toList();

        model.addAttribute("presentEvents", presentEvents);
        model.addAttribute("pastEvents", pastEvents);
        return "index";
    }

    @GetMapping("/events")
    public String showEventsList(Model model)
        {
            List<Event> listEvents = eventService.findAllEvents();
            List<Event> pastEvents = listEvents.stream().filter(event -> event.getDate().before(new Date())).toList();
            List<Event> presentEvents =
                    listEvents.stream().filter(event -> event.getDate().after(new Date())).toList();

            model.addAttribute("presentEvents", presentEvents);
            model.addAttribute("pastEvents", pastEvents);
            return "events";
        }

    @GetMapping("/events/new")
    public String showAddNewEventForm(Model model)
        {
            model.addAttribute("event", new Event());
            model.addAttribute("pageTitle", "Lisa uus üritus");
            model.addAttribute("pagePurpose", "Ürituse lisamine");
            return "events_form";
        }

    @PostMapping("/events/save")
    public String saveNewEvent(Event event, RedirectAttributes redirectAttributes)
        {
            eventService.save(event);
            redirectAttributes.addFlashAttribute("message", "Üritus on edukalt lisatud");
            return "redirect:/";
        }

    @GetMapping("/events/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes)
        {
            try {
                Event event = eventService.get(id);
                model.addAttribute("event", event);
                model.addAttribute("pageTitle", "Muuda üritust: " + event.getName());
                model.addAttribute("pagePurpose", "Ürituse lisamine");
                redirectAttributes.addFlashAttribute("message", "Üritus on edukalt salvestatud");
                return "events_form";
            } catch (EventNotFoundException e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());
                return "redirect:/";
            }
        }

    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable("id") Integer id, RedirectAttributes ra)
        {
            try {
                eventService.delete(id);
                ra.addFlashAttribute("message", "Üritus (ID: " + id + ") on kustutatud.");
            } catch (EventNotFoundException e) {
                ra.addFlashAttribute("message", e.getMessage());
            }
            return "redirect:/";
        }

    @GetMapping("/events/participants/{id}")
    public String showParticipants(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes)
        {
            try {
                Event event = eventService.get(id);
                List<Participant> participants = event.getParticipants();
                logger.log(Level.INFO, "Participants: " + participants);

                Predicate<Participant> peoplePredicate = person -> person instanceof Person;
                List<Participant> onlyPeople = participants.stream().filter(peoplePredicate).toList();
                logger.log(Level.INFO, "Participants: " + onlyPeople);

                Predicate<Participant> companyPredicate = company -> company instanceof Company;
                List<Participant> onlyCompanies = participants.stream().filter(companyPredicate).toList();
                logger.log(Level.INFO, "Participants: " + onlyCompanies);

                List<String> paymentMethods = Arrays.asList("sularaha", "pangaülekanne");
                model.addAttribute("paymentMethods", paymentMethods);

                model.addAttribute("people", onlyPeople);
                model.addAttribute("companies", onlyCompanies);
                model.addAttribute("event", event);

                model.addAttribute("person", new Person());
                model.addAttribute("company", new Company());

                return "event_participants";
            } catch (EventNotFoundException e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());
                return "redirect:/";
            }
        }

    @GetMapping("/events/old/participants/{id}")
    public String showParticipantsForPastEvent(@PathVariable("id") Integer id, Model model,
                                      RedirectAttributes redirectAttributes)
        {
            try {
                Event event = eventService.get(id);
                List<Participant> participants = event.getParticipants();
                logger.log(Level.INFO, "Participants: " + participants);

                Predicate<Participant> peoplePredicate = person -> person instanceof Person;
                List<Participant> onlyPeople = participants.stream().filter(peoplePredicate).toList();
                logger.log(Level.INFO, "Participants: " + onlyPeople);

                Predicate<Participant> companyPredicate = company -> company instanceof Company;
                List<Participant> onlyCompanies = participants.stream().filter(companyPredicate).toList();
                logger.log(Level.INFO, "Participants: " + onlyCompanies);


                model.addAttribute("people", onlyPeople);
                model.addAttribute("companies", onlyCompanies);
                model.addAttribute("event", event);

                return "past_event_participants";
            } catch (EventNotFoundException e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());
                return "redirect:/";
            }
        }
}
