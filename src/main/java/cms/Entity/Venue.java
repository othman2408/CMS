package cms.Entity;

import java.util.List;

public class Venue {
    private int venueId;
    private String location;
    private List<Conference> hostedConferences;

    public Venue() {
    }

    public Venue(int venueId, String location) {
        this.venueId = venueId;
        this.location = location;
    }

    public Venue(int venueId, String location, List<Conference> hostedConferences) {
        this.venueId = venueId;
        this.location = location;
        this.hostedConferences = hostedConferences;
    }


    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Conference> getHostedConferences() {
        return hostedConferences;
    }

    public void setHostedConferences(List<Conference> hostedConferences) {
        this.hostedConferences = hostedConferences;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "venueId=" + venueId +
                ", location='" + location + '\'' +
                ", hostedConferences=" + hostedConferences +
                '}';
    }
}
