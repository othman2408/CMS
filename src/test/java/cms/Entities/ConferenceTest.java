package cms.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConferenceTest {

    private Conference conference;

    @BeforeEach
    public void setup() {
        // Initialize a Conference object before each test
        conference = new Conference();
    }

    @Test
    public void testConferenceAttributes() {
        // Test default values after object creation
        assertEquals(0, conference.getConferenceId());
        assertEquals(null, conference.getName());
        assertEquals(null, conference.getStartDate());
        assertEquals(null, conference.getEndDate());
        assertEquals(null, conference.getDeadline());
        // Add assertions for other attributes as needed
    }

    @Test
    public void testSettersAndGetters() {
        // Set values using setter methods
        conference.setConferenceId(1);
        conference.setName("Conference 1");
        conference.setStartDate(LocalDate.of(2023, 12, 1));
        conference.setEndDate(LocalDate.of(2023, 12, 5));
        conference.setDeadline(LocalDate.of(2023, 11, 25));
        // Add setter/getter assertions
        assertEquals(1, conference.getConferenceId());
        assertEquals("Conference 1", conference.getName());
        assertEquals(LocalDate.of(2023, 12, 1), conference.getStartDate());
        assertEquals(LocalDate.of(2023, 12, 5), conference.getEndDate());
        assertEquals(LocalDate.of(2023, 11, 25), conference.getDeadline());
        // Add assertions for other setter/getter pairs as needed
    }

    @Test
    public void testReviewersList() {
        // Test manipulation of reviewers list
        List<Reviewer> reviewers = new ArrayList<>();
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        reviewers.add(reviewer1);
        reviewers.add(reviewer2);

        conference.setReviewers(reviewers);

        // Check if the reviewers list is set properly
        assertEquals(2, conference.getReviewers().size());
        assertTrue(conference.getReviewers().contains(reviewer1));
        assertTrue(conference.getReviewers().contains(reviewer2));
    }




}