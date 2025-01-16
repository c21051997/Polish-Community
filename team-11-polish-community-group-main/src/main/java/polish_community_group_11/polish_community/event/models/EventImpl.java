package polish_community_group_11.polish_community.event.models;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import polish_community_group_11.polish_community.event.models.viewmodel.EventForm;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class EventImpl implements Event{
    private int event_id;
    private String event_title;
    private String description;
    private LocalDate event_date;
    private LocalTime event_time;
    private String location;
    private int user_id;
    private String imageUrl;
    private String whyJoin;
    private String benefits;

    public EventForm processEventToEventForm(){

        EventForm eventForm = new EventForm(
             event_id, event_title, description, event_date, event_time,
             location, user_id, imageUrl, whyJoin, benefits
     );
     return eventForm;
    }
}
