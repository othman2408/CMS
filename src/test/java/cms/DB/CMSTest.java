package cms.DB;

import cms.Entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CMSTest {

    private DBConnector dbConnector;

    @BeforeEach
    void setup() {
        dbConnector = Mockito.mock(DBConnector.class);
    }

    @Test
    public void testRegisterUser() throws SQLException {

        User user = new Organizer("othman", "mypass1", "othman alibrahim", "othman@gmail.com");

        when(dbConnector.registerUser(user)).thenReturn(true);

        boolean isRegistered = dbConnector.registerUser(user);

        assertTrue(isRegistered);
    }

    @Test
    void testCheckForUser() throws SQLException {

        when(dbConnector.checkForUser(anyString())).thenReturn(true);

        boolean userExists = dbConnector.checkForUser("othman");

        assertTrue(userExists);
    }

    @Test
    void testLogin() throws SQLException {

        when(dbConnector.login("othman", "mypass1")).thenReturn(true);

        boolean isLoggedIn = dbConnector.login("othman", "mypass1");

        assertTrue(isLoggedIn);
    }

    @Test
    void testGetUser() throws SQLException {
        User expectedUser = new Organizer("othman", "mypass1", "othman alibrahim", "othman@gmail.com");
        when(dbConnector.getUser(anyString())).thenReturn(expectedUser);

        User retrievedUser = dbConnector.getUser("othman");

        assertEquals(expectedUser, retrievedUser);
    }

    @Test
    void testGetUserRole() throws SQLException {

        when(dbConnector.getUserRole(anyString())).thenReturn("Organizer");

        String userRole = dbConnector.getUserRole("othman");

        assertEquals("Organizer", userRole);
    }

    @Test
    void testGetReviewers() throws SQLException {

        List<Reviewer> reviewers = new ArrayList<>();
        reviewers.add(new Reviewer("reviewer1", "pass", "John Doe", "john@example.com"));
        reviewers.add(new Reviewer("reviewer2", "pass", "Jane Doe", "jane@example.com"));

        when(dbConnector.getReviewers()).thenReturn(reviewers);

        List<Reviewer> retrievedReviewers = dbConnector.getReviewers();

        assertEquals(2, retrievedReviewers.size());
    }

    @Test
    void testGetAvailableVenues() {

        List<Venue> availableVenues = new ArrayList<>();
        availableVenues.add(new Venue(1, "Venue A"));
        availableVenues.add(new Venue(2, "Venue B"));

        when(dbConnector.getAvailableVenues()).thenReturn(availableVenues);

        List<Venue> retrievedVenues = dbConnector.getAvailableVenues();

        assertEquals(2, retrievedVenues.size());
    }

    @Test
    void testGetVenue() {

        Venue venue = new Venue(1, "Venue A");

        when(dbConnector.getVenue("Venue A")).thenReturn(venue);

        Venue retrievedVenue = dbConnector.getVenue("Venue A");

        assertEquals("Venue A", retrievedVenue.getLocation());
    }

    @Test
    void testRegisterConference() {

            Conference conference = new Conference("ConferenceName", null, null, null, 1, 1);

            // List of potential reviewers
            List<String> potentialReviewers = new ArrayList<>();
            potentialReviewers.add("reviewer1");
            potentialReviewers.add("reviewer2");
            potentialReviewers.add("reviewer3");

            when(dbConnector.registerConference(conference, potentialReviewers)).thenReturn(true);

            boolean isRegistered = dbConnector.registerConference(conference, potentialReviewers);

            assertTrue(isRegistered);
    }

    @Test
    void testCheckForConference() {

        when(dbConnector.checkForConference("ConferenceName")).thenReturn(true);

        boolean exists = dbConnector.checkForConference("ConferenceName");

        assertTrue(exists);
    }

    @Test
    void testGetTotalNumberOfConferences() {

        when(dbConnector.getTotalNumberOfConferences()).thenReturn(5);

        int totalConferences = dbConnector.getTotalNumberOfConferences();

        assertEquals(5, totalConferences);
    }

    @Test
    void testAssignReviewerToConference() {
        Mockito.doNothing().when(dbConnector).assignReviewerToConference(1, 1);

        dbConnector.assignReviewerToConference(1, 1);
    }

    @Test
    void testGetOrganizerConferences() {

        List<Conference> conferences = new ArrayList<>();
        conferences.add(new Conference("Conference1", null, null, null, 1, 1));
        conferences.add(new Conference("Conference2", null, null, null, 1, 2));

        when(dbConnector.getOrganizerConferences("organizer")).thenReturn(conferences);

        List<Conference> retrievedConferences = dbConnector.getOrganizerConferences("organizer");

        assertEquals(2, retrievedConferences.size());
    }

    @Test
    void testGetOrganizerConfNo() throws SQLException {

        when(dbConnector.getOrganizerConfNo("organizer")).thenReturn(3);

        int confNo = dbConnector.getOrganizerConfNo("organizer");

        assertEquals(3, confNo);
    }

    @Test
    void testGetAllReviewer() throws SQLException {

        List<Reviewer> reviewers = new ArrayList<>();
        reviewers.add(new Reviewer("reviewer1", "pass", "John Doe", "john@example.com"));
        reviewers.add(new Reviewer("reviewer2", "pass", "Jane Doe", "jane@example.com"));

        when(dbConnector.getAllReviewer()).thenReturn(reviewers);

        List<Reviewer> retrievedReviewers = dbConnector.getAllReviewer();

        assertEquals(2, retrievedReviewers.size());
    }


}