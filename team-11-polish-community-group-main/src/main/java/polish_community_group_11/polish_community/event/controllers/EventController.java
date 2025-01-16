package polish_community_group_11.polish_community.event.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import polish_community_group_11.polish_community.event.models.viewmodel.EventForm;
import org.springframework.web.bind.annotation.*;
import polish_community_group_11.polish_community.event.models.Event;
import polish_community_group_11.polish_community.event.models.EventImpl;
import polish_community_group_11.polish_community.event.services.EventService;
import polish_community_group_11.polish_community.register.services.UserService;

import java.security.Principal;
import java.sql.SQLException;

@Controller
public class EventController {

    private EventService eventService;
    private UserService userService;

    @Autowired
    // Constructor-based dependency injection for EventRepository
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    // Controller method to handle GET requests for the "/event" URL
    @GetMapping("/event")
    public ModelAndView getAllEvents() {
        // Create a ModelAndView object to handle both the view (HTML) and data
        ModelAndView modelAndView = new ModelAndView("event/event");

        // Add the list of all events to the model object, which will be passed to the Thymeleaf template
        // 'eventService.getAllEvents()' fetches all the event data from the database
        modelAndView.addObject("events", eventService.getAllEvents());

        // Return the ModelAndView object, which contains the view name ("event/event") and the data (events)
        // This tells Spring to render the "event/event" Thymeleaf template and pass the "events" data
        return modelAndView;  // Thymeleaf template will use this data to render the event details
    }

    /**
     * Handles HTTP GET request for fetching event details by event ID.
     *
     * @param event_id the unique identifier of the event to fetch
     * @return ModelAndView object with the event details view and event data
     */
    @GetMapping("event/{event_id}") // Mapping URL pattern with the event ID as a path variable
    public ModelAndView getEventDetails(@PathVariable int event_id) {

        // Create a new ModelAndView object and associate it with the 'event-detail' view
        ModelAndView modelAndView = new ModelAndView("event/event-detail");

        // Add the event object (fetched using the event ID) to the model
        modelAndView.addObject("event", eventService.getEventById(event_id));

        // Return the populated ModelAndView object, which will render the event details view
        return modelAndView;
    }

    //Controller Method to load create new event page
    @GetMapping("event/add")
    public ModelAndView addEvent() {
        ModelAndView modelAndView = new ModelAndView("event/add-event");
        //Passing Form object for validation
        modelAndView.addObject("event", new EventForm());

        /* Form action string to check the type of action to perform and dynamically
         use the same form for both create and update */
        modelAndView.addObject("formAction", "/event/add");
        return modelAndView;
    }

    @PostMapping("event/add")
    public ModelAndView addEvent(@Valid @ModelAttribute("event") EventForm event,
                                 BindingResult bindingResult, Model model,
                                 Principal principal, RedirectAttributes redirectAttributes) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("event/add-event");
        if(bindingResult.hasErrors()) {
            modelAndView.addObject(model.asMap());

            /* Form action string to check the type of action to perform and dynamically
         use the same form for both create and update */
            modelAndView.addObject("formAction", "/event/add");
        }
        else{
            if(event==null){
                throw new NullPointerException("New event is empty or null");
            }
            eventService.addNewEvent(event.processEventForm(),principal.getName());
            redirectAttributes.addFlashAttribute("successMessage", "Event created successfully!");
            modelAndView.setViewName("redirect:/event");
        }
        return modelAndView;
    }

    //Update Events
    @GetMapping("event/edit/{id}")
    public ModelAndView editEvent(@PathVariable int id) {
        EventForm event = eventService.getEventById(id).processEventToEventForm();
        ModelAndView modelAndView = new ModelAndView("event/add-event");
        modelAndView.addObject("event", event);
        modelAndView.addObject("formAction", "/event/edit");
        return modelAndView;
    }

    @PostMapping("/event/edit")
    public ModelAndView editEvent(@Valid @ModelAttribute("event") EventForm event,
                                  BindingResult bindingResult, Model model) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("event/add-event");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(model.asMap());
            modelAndView.addObject("formAction", "/event/edit");
        } else {
            eventService.getEditEvent(event.processEventForm());
            modelAndView.setViewName("redirect:/event");
        }
        return modelAndView;
    }

}
