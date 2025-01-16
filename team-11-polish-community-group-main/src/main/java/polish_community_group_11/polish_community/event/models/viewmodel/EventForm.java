package polish_community_group_11.polish_community.event.models.viewmodel;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import polish_community_group_11.polish_community.event.models.Event;
import polish_community_group_11.polish_community.event.models.EventImpl;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventForm {
    private int event_id;
    @NotEmpty(message = "Please enter the event title")
    private String event_title;
    @NotEmpty(message = "Please enter the event description")
    private String description;
    @FutureOrPresent(message = "The date cannot be in the past")
    @NotNull(message = "Please enter the event date")
    private LocalDate event_date;
    @NotNull(message = "Please enter the time of the event")
    private LocalTime event_time;
    @NotEmpty(message = "Please provide the location of event")
    private String location;
    private int user_id;
    @NotEmpty(message = "Please provide event image url")
    @Pattern(regexp = ".*\\.(png|jpg|jpeg|gif).*", message = "Must be a valid image (png, jpg, jpeg, gif).")
    @URL(message = "Not a valid image url")
    private String imageUrl;
    private String whyJoin;
    private String benefits;

    public Event processEventForm(){

        if (event_title == null || event_title.isEmpty()) {
            throw new IllegalArgumentException("Event title is required");
        }
        if (event_date == null) {
            throw new IllegalArgumentException("Event date is required");
        }
        if (event_time == null) {
            throw new IllegalArgumentException("Event time is required");
        }
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Location is required");
        }

        Event event = new EventImpl(
                event_id, event_title, description, event_date, event_time,
                location, user_id, imageUrl, whyJoin, benefits
        );
        return event;
    }
}
