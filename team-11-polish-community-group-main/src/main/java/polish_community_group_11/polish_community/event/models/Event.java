package polish_community_group_11.polish_community.event.models;

import polish_community_group_11.polish_community.event.models.viewmodel.EventForm;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Event {
    public int getEvent_id();
    public void setEvent_id(int event_id);
    public String getEvent_title();
    public void setEvent_title(String name);
    public String getDescription();
    public void setDescription(String description);
    public LocalDate getEvent_date();
    public void setEvent_date(LocalDate event_date);
    public LocalTime getEvent_time();
    public void setEvent_time(LocalTime event_time);
    public String getLocation();
    public void setLocation(String location);
    public int getUser_id();
    public void setUser_id(int user_id);
    public String getImageUrl();
    public void setImageUrl(String imageUrl);
    public String getWhyJoin();

    public void setWhyJoin(String whyJoin);

    public String getBenefits();

    public void setBenefits(String benefits);
    EventForm processEventToEventForm();
}
