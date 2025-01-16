package polish_community_group_11.polish_community.event.services;

import polish_community_group_11.polish_community.event.models.Event;
import java.sql.SQLException;
import java.util.List;

public interface EventService {

    List<Event> getAllEvents();
    Event getEventById(int id);
    void addNewEvent(Event newEvent, String email) throws SQLException;
    Event getEditEvent(Event event) throws SQLException;
}