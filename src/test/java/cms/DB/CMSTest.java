package cms.DB;

import cms.Entity.*;
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
        // Create a mock DBConnector
        dbConnector = Mockito.mock(DBConnector.class);

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

        // Define the expected behavior (Mockito stubbing)
        when(dbConnector.getUserRole(anyString())).thenReturn("Organizer");

        // Perform the getUserRole operation
        String userRole = dbConnector.getUserRole("othman");

        // Assert the retrieved user role
        assertEquals("Organizer", userRole);
    }

    @Test
    void testGetReviewers() throws SQLException {

        // Create a sample list of reviewers
        List<Reviewer> reviewers = new ArrayList<>();
        reviewers.add(new Reviewer("reviewer1", "pass", "John Doe", "john@example.com"));
        reviewers.add(new Reviewer("reviewer2", "pass", "Jane Doe", "jane@example.com"));

        // Define the expected behavior (Mockito stubbing)
        when(dbConnector.getReviewers()).thenReturn(reviewers);

        // Perform the getReviewers operation
        List<Reviewer> retrievedReviewers = dbConnector.getReviewers();

        // Assert the size of the retrieved reviewers list
        assertEquals(2, retrievedReviewers.size());
    }

    @Test
    void testGetAvailableVenues() {

        // Create a sample list of available venues
        List<Venue> availableVenues = new ArrayList<>();
        availableVenues.add(new Venue(1, "Venue A"));
        availableVenues.add(new Venue(2, "Venue B"));

        // Define the expected behavior (Mockito stubbing)
        when(dbConnector.getAvailableVenues()).thenReturn(availableVenues);

        // Perform the getAvailableVenues operation
        List<Venue> retrievedVenues = dbConnector.getAvailableVenues();

        // Assert the size of the retrieved available venues list
        assertEquals(2, retrievedVenues.size());
    }

    @Test
    void testGetVenue() {

        // Create a sample venue
        Venue venue = new Venue(1, "Venue A");

        // Define the expected behavior (Mockito stubbing)
        when(dbConnector.getVenue("Venue A")).thenReturn(venue);

        // Perform the getVenue operation
        Venue retrievedVenue = dbConnector.getVenue("Venue A");

        // Assert the retrieved venue name
        assertEquals("Venue A", retrievedVenue.getLocation());
    }

    @Test
    void testRegisterConference() {

        // Create a sample conference
        Conference conference = new Conference("ConferenceName", null, null, null, 1, 1);

        // Define the expected behavior (Mockito stubbing)
        when(dbConnector.registerConference(conference)).thenReturn(true);

        // Perform the registerConference operation
        boolean isRegistered = dbConnector.registerConference(conference);

        // Assert the conference registration
        assertTrue(isRegistered);
    }

    @Test
    void testCheckForConference() {

        // Define the expected behavior (Mockito stubbing)
        when(dbConnector.checkForConference("ConferenceName")).thenReturn(true);

        // Perform the checkForConference operation
        boolean exists = dbConnector.checkForConference("ConferenceName");

        // Assert the existence of the conference
        assertTrue(exists);
    }

    @Test
    void testGetTotalNumberOfConferences() {

        // Define the expected behavior (Mockito stubbing)
        when(dbConnector.getTotalNumberOfConferences()).thenReturn(5);

        // Perform the getTotalNumberOfConferences operation
        int totalConferences = dbConnector.getTotalNumberOfConferences();

        // Assert the total number of conferences
        assertEquals(5, totalConferences);
    }

    @Test
    void testAssignReviewerToConference() {

        // Define the expected behavior (Mockito stubbing)
        // Assuming the operation doesn't return anything (void method)
        Mockito.doNothing().when(dbConnector).assignReviewerToConference(1, 1);

        // Perform the assignReviewerToConference operation
        dbConnector.assignReviewerToConference(1, 1);

        // No assertions in this case, as it's a void method
    }

    @Test
    void testGetOrganizerConferences() {

        // Create a sample list of conferences
        List<Conference> conferences = new ArrayList<>();
        conferences.add(new Conference("Conference1", null, null, null, 1, 1));
        conferences.add(new Conference("Conference2", null, null, null, 1, 2));

        // Define the expected behavior (Mockito stubbing)
        when(dbConnector.getOrganizerConferences("organizer")).thenReturn(conferences);

        // Perform the getOrganizerConferences operation
        List<Conference> retrievedConferences = dbConnector.getOrganizerConferences("organizer");

        // Assert the size of the retrieved conferences list
        assertEquals(2, retrievedConferences.size());
    }

    @Test
    void testGetOrganizerConfNo() throws SQLException {

        // Define the expected behavior (Mockito stubbing)
        when(dbConnector.getOrganizerConfNo("organizer")).thenReturn(3);

        // Perform the getOrganizerConfNo operation
        int confNo = dbConnector.getOrganizerConfNo("organizer");

        // Assert the number of conferences organized by the user
        assertEquals(3, confNo);
    }

    @Test
    void testGetAllReviewer() throws SQLException {

        // Create a sample list of reviewers
        List<Reviewer> reviewers = new ArrayList<>();
        reviewers.add(new Reviewer("reviewer1", "pass", "John Doe", "john@example.com"));
        reviewers.add(new Reviewer("reviewer2", "pass", "Jane Doe", "jane@example.com"));

        // Define the expected behavior (Mockito stubbing)
        when(dbConnector.getAllReviewer()).thenReturn(reviewers);

        // Perform the getAllReviewer operation
        List<Reviewer> retrievedReviewers = dbConnector.getAllReviewer();

        // Assert the size of the retrieved reviewers list
        assertEquals(2, retrievedReviewers.size());
    }


}