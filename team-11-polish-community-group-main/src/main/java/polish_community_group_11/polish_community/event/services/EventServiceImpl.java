package polish_community_group_11.polish_community.event.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polish_community_group_11.polish_community.event.dao.EventRepository;
import polish_community_group_11.polish_community.event.models.Event;
import polish_community_group_11.polish_community.register.services.UserService;

import java.sql.SQLException;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService=userService;
    }

    public List<Event> getAllEvents(){
        return eventRepository.getAllEvents();
    }
    public Event getEventById(int id){
        return eventRepository.getEventById(id);
    }
    public void addNewEvent(Event newEvent, String email) throws SQLException {
        newEvent.setUser_id(userService.findUserIdByEmail(email));
        eventRepository.addNewEvent(newEvent);
    }

    public Event getEditEvent(Event event) throws SQLException {
        return eventRepository.editEvent(event);
    }
}
