package polish_community_group_11.polish_community.event.dao;

import polish_community_group_11.polish_community.event.models.Event;

import java.sql.SQLException;
import java.util.List;

public interface EventRepository {

     List<Event> getAllEvents();

     Event getEventById(int id);
     void addNewEvent(Event newEvent) throws SQLException;
     Event editEvent(Event event)throws SQLException;
}
