package polish_community_group_11.polish_community.event.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import polish_community_group_11.polish_community.event.models.Event;
import polish_community_group_11.polish_community.event.models.EventImpl;
import java.sql.SQLException;

import java.util.List;

@Repository // Marks this class as a Spring Data repository for database interaction
public class EventRepositoryImpl implements EventRepository {

    // JdbcTemplate instance for executing SQL queries
    private final JdbcTemplate jdbc;

    // RowMapper to map SQL result set rows to Event objects
    private RowMapper<Event> eventItemMapper;

    // Constructor that accepts JdbcTemplate and initializes the RowMapper
    public EventRepositoryImpl(JdbcTemplate aJdbc) {
        this.jdbc = aJdbc; // Initialize JdbcTemplate for executing queries
        setEventItemMapper(); // Set the RowMapper for mapping rows to Event objects
    }

    /**
     * Initializes the RowMapper for Event mapping.
     * This will map a result set from the database to an Event object.
     */
    public void setEventItemMapper() {
        try {
            // Creating a RowMapper to convert each row in the result set to an EventImpl instance
            eventItemMapper = (rs, i) -> new EventImpl(
                    rs.getInt("event_id"),
                    rs.getString("event_title"),
                    rs.getString("event_description"),
                    rs.getDate("event_date").toLocalDate(),
                    rs.getTime("event_time").toLocalTime(),
                    rs.getString("location"),
                    rs.getInt("user_id"),
                    rs.getString("event_poster_url"),
                    rs.getString("whyJoin"),
                    rs.getString("benefits")
            );
        }
        catch (Exception ex) {
            // Catch any exception while setting the RowMapper and print the error message
            System.out.println(ex.getMessage());
        }
    }


    // Method to retrieve all events from the database
    public List<Event> getAllEvents() {

        String sql = "select * from event order by event_id desc";
        return jdbc.query(sql, eventItemMapper);
    }


    /**
     * Fetches an event from the database by its event_id.
     *
     * @param id the unique identifier of the event to fetch
     * @return Event object containing the event data
     */
    public Event getEventById(int id) {
        // SQL query to fetch event details using the event_id
        String sql = "select * from event where event_id = ?";

        // Execute the query using JdbcTemplate and map the result to an Event object using the RowMapper
        Event event = jdbc.queryForObject(sql, eventItemMapper, id);

        // Return the fetched event
        return event;
    }

    public void addNewEvent(Event newEvent) throws SQLException, NullPointerException{
        if(newEvent==null){
            throw new NullPointerException("Event cannot be null");
        }
        String dbInsertSql =
                "insert into event " +
                        "(event_title, event_description, location, " +
                        "event_date, event_time,user_id, event_poster_url,whyJoin,benefits)" +
                        " values (?,?,?,?,?,?,?,?,?)";
        try{
            jdbc.update(dbInsertSql,
                    newEvent.getEvent_title(),
                    newEvent.getDescription(),
                    newEvent.getDescription(),
                    newEvent.getEvent_date(),
                    newEvent.getEvent_time(),
                    newEvent.getUser_id(),
                    newEvent.getImageUrl(),
                    newEvent.getWhyJoin(),
                    newEvent.getBenefits()
            );
        }
        catch (DataAccessException e) {
            throw new SQLException("Failed to insert new information record", e);
        }
    }

    // Updates selected record with new updated information
    public Event editEvent(Event event)throws SQLException {
        String updateSql = "UPDATE event " +
                "SET event_title = ?, event_description = ?, " +
                "event_date = ?, event_time = ?, " +
                "location = ?, user_id = ?, event_poster_url = ?, " +
                "whyJoin = ?, benefits = ? " +
                "WHERE event_id = ?";
        try {
            // jdbc.update() is a method that will execute the sql query
            // replaces the ? with the actual values from the news object
            int rowsAffected = jdbc.update(updateSql,
                    event.getEvent_title(),
                    event.getDescription(),
                    event.getEvent_date(),
                    event.getEvent_time(),
                    event.getLocation(),
                    event.getUser_id(),
                    event.getImageUrl(),
                    event.getWhyJoin(),
                    event.getBenefits(),
                    event.getEvent_id()
            );

            // error handling
            if (rowsAffected == 0) {
                throw new SQLException("No event item was updated. Check the ID provided.");
            }
        } catch (DataAccessException e) {
            throw new SQLException("Error updating event with ID: " + event.getEvent_id(), e);
        }
        return event;
    }
}
