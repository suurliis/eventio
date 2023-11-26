package com.liis.eventio.participant;

import com.liis.eventio.event.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;
    static Logger logger = Logger.getLogger(ParticipantController.class.getName());

    @GetMapping("/participants")
    public String showParticipantsList(Model model)
        {
            List<Participant> listParticipants = participantService.findAllParticipants();
            model.addAttribute("participants", listParticipants);
            return "participants";
        }

    @GetMapping("/participant/person/new")
    public String showAddNewParticipantForm(Model model)
        {
            model.addAttribute("person", new Person());
            model.addAttribute("pageTitle", "Lisa uus füüsiline isik");
            return "person_form";
        }

    @GetMapping("/participant/company/new")
    public String showAddNewCompanyForm(Model model)
        {
            model.addAttribute("company", new Company());
            model.addAttribute("pageTitle", "Lisa uus ettevõte");
            return "company_form";
        }

    @PostMapping("/person/save")
    public String saveNewPerson(Person person, RedirectAttributes redirectAttributes)
        {
            participantService.savePerson(person);
            redirectAttributes.addFlashAttribute("message", "Füüsiline isik (perekonnanimi: " + person.getLastName() + ") on " +
                    "salvestatud.");
            return "index";
        }

    @PostMapping("/company/save")
    public String saveNewCompany(Company company, RedirectAttributes redirectAttributes)
        {
            participantService.saveCompany(company);
            redirectAttributes.addFlashAttribute("message", "Ettevõte (ID: " + company.participantId + ") on salvestatud.");
            return "index";
        }

    @PostMapping("/events/participants/person/{id}")
    public String saveNewPersonToEvent(@PathVariable("id") Integer id,
                                            Person person, RedirectAttributes redirectAttributes, final ModelMap model) {
        participantService.saveParticipant(person);
        logger.log(Level.INFO, "Added new participant: " + person);
        try {
            participantService.addParticipantToEvent(person, id);
            logger.log(Level.INFO, "Added new participant to event: " + id);
        } catch (EventNotFoundException | ParticipantNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
        model.clear();
        return "index";
    }

    @PostMapping("/events/participants/company/{id}")
    public String saveNewCompanyToEvent(@PathVariable("id") Integer id,
                                       Company company, RedirectAttributes redirectAttributes, final ModelMap model) {
        participantService.saveParticipant(company);
        logger.log(Level.INFO, "Added new participant: " + company);
        try {
            participantService.addParticipantToEvent(company, id);
            logger.log(Level.INFO, "Added new participant to event: " + id);
        } catch (EventNotFoundException | ParticipantNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
        model.clear();
        return "index";
    }

    @GetMapping("/person/edit/{id}")
    public String showEditFormPerson(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes)
        {
            List<String> paymentMethods = Arrays.asList("sularaha", "pangaülekanne");
            model.addAttribute("paymentMethods", paymentMethods);
            try {
                Person person = (Person) participantService.get(id);
                model.addAttribute("person", person);
                model.addAttribute("pageTitle", "Edit Person: " + person.getLastName());
                redirectAttributes.addFlashAttribute("message", "Füüsiline isik (perekonnanimi: " + id + ") on " +
                        "salvestatud.");
                return "person_form";
            } catch (ParticipantNotFoundException e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());
                return "redirect:/";
            }
        }

    @GetMapping("/person/delete/{id}")
    public String deletePerson(@PathVariable("id") Integer id, RedirectAttributes ra)
        {
            participantService.delete(id);
            ra.addFlashAttribute("message", "Füüsiline isik (perekonnanimi:" + id + ") on kustutatud.");
            return "redirect:/";
        }

    @GetMapping("/company/edit/{id}")
    public String showEditFormCompany(@PathVariable("id") Integer id, Model model,
                                      RedirectAttributes redirectAttributes)
        {
            List<String> paymentMethods = Arrays.asList("sularaha", "pangaülekanne");
            model.addAttribute("paymentMethods", paymentMethods);
            try {
                Company company = (Company) participantService.get(id);
                model.addAttribute("company", company);
                model.addAttribute("pageTitle", "Muuda ettevõtet: " + company.getName());
                redirectAttributes.addFlashAttribute("message", "Ettevõte (ID: " + id + ") on salvestatud.");
                return "company_form";
            } catch (ParticipantNotFoundException e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());
                return "redirect:/";
            }
        }

    @GetMapping("/company/delete/{id}")
    public String deleteCompany(@PathVariable("id") Integer id, RedirectAttributes ra)
        {
            participantService.delete(id);
            ra.addFlashAttribute("message", "Ettevõte (ID: " + id + ") on kustutatud.");
            return "redirect:/";
        }
}