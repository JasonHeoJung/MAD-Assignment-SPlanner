package sg.edu.np.mad.splanner;

import java.util.Comparator;

public class Event implements Comparable<Event>{
    public String module;
    public String location;
    public String timing;

    public Event() {
    }

    public Event(String module, String location, String timing) {
        this.module = module;
        this.location = location;
        this.timing = timing;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    @Override
    public int compareTo(Event event) {
        return this.getTiming().compareTo(event.getTiming());
    }
}



