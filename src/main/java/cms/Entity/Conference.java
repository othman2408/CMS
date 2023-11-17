package cms.Entity;

import java.awt.print.Paper;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Conference implements Serializable {
    private int conferenceId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate deadline;

    private String conferenceCode;
    private List<Reviewer> reviewers;
    private List<Venue> venues;
    private List<Paper> papers;

    public Conference() {
    }

    public Conference( String name, LocalDate startDate, LocalDate endDate, LocalDate deadline, String conferenceCode, List<Reviewer> reviewers, List<Venue> venues, List<Paper> papers) {
        this.conferenceId = conferenceId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deadline = deadline;
        this.conferenceCode = conferenceCode;
        this.reviewers = reviewers;
        this.venues = venues;
        this.papers = papers;
    }

    public int getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getConferenceCode() {
        return conferenceCode;
    }

    public void setConferenceCode(String conferenceCode) {
        this.conferenceCode = conferenceCode;
    }

    public List<Reviewer> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<Reviewer> reviewers) {
        this.reviewers = reviewers;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "conferenceId=" + conferenceId +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", deadline=" + deadline +
                ", conferenceCode='" + conferenceCode + '\'' +
                ", reviewers=" + reviewers +
                ", venues=" + venues +
                ", papers=" + papers +
                '}';
    }
}
