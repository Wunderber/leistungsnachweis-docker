package data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dataset {

    @SerializedName("events")
    private List<Event> events;

    // Getter and Setter

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
