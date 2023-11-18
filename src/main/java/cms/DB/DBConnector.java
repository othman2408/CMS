package cms.DB;

import cms.Entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {
     public String dburl = null;
     public String user = null;
     public String pass = null;




    Statement stmt;
    Connection conn;
    ResultSet rs;

    public DBConnector() throws SQLException {
        conn = DriverManager.getConnection(dburl, user, pass);
        stmt = conn.createStatement();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(dburl, username, password);
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public boolean registerUser(User user) throws SQLException {
        // Ensure the statement is initialized before using it
        if (stmt != null) {
            try {
                // Use PreparedStatement to avoid SQL injection
                String sql = "INSERT INTO USERS (USERNAME, PASSWORD, NAME, EMAIL, ROLE) VALUES (?, ?, ?, ?, ?)";
                java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);

                // Set parameters
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setString(5, user.getClass().getSimpleName());

                // Execute the update
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                throw e; // Re-throw the exception for handling in the calling code
            }
        } else {
            // Handle the case where the statement is not initialized
            return false;
        }
    }

    public boolean checkForUser(String username) throws SQLException {
        // Ensure the statement is initialized before using it
        if (stmt != null) {
            try {
                // Use PreparedStatement to avoid SQL injection
                String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
                java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);

                // Set parameters
                preparedStatement.setString(1, username);

                // Execute the query
                rs = preparedStatement.executeQuery();

                // Check if the result set is empty
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e; // Re-throw the exception for handling in the calling code
            }
        }
        return false;
    }

    // login method
    public boolean login(String username, String password) throws SQLException {
        // Ensure the statement is initialized before using it
        if (stmt != null) {
            try {
                // Use PreparedStatement to avoid SQL injection
                String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
                java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);

                // Set parameters
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                // Execute the query
                rs = preparedStatement.executeQuery();

                // Check if the result set is empty
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e; // Re-throw the exception for handling in the calling code
            }
        }
        return false;
    }

    public User getUser(String username) throws SQLException {
        // Ensure the statement is initialized before using it
        if (stmt != null) {
            try {
                String role = getUserRole(username);

                // Use PreparedStatement to avoid SQL injection
                String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
                java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);

                // Set parameters
                preparedStatement.setString(1, username);

                // Execute the query
                rs = preparedStatement.executeQuery();

                // Check if the result set is empty
                if (rs.next()) {
                    switch (role) {
                        case "Organizer":
                            return new Organizer(rs.getInt("USER_ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("NAME"), rs.getString("EMAIL"));
                        case "Reviewer":
                            return new Reviewer(rs.getInt("USER_ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("NAME"), rs.getString("EMAIL"));
                        case "Author":
                            return new Author(rs.getInt("USER_ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("NAME"), rs.getString("EMAIL"));
                    }
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e; // Re-throw the exception for handling in the calling code
            }
        }
        return null;
    }

    public String getUserRole(String username) throws SQLException {
        // Ensure the statement is initialized before using it
        if (stmt != null) {
            try {
                // Use PreparedStatement to avoid SQL injection
                String sql = "SELECT ROLE FROM USERS WHERE USERNAME = ?";
                java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);

                // Set parameters
                preparedStatement.setString(1, username);

                // Execute the query
                rs = preparedStatement.executeQuery();

                // Check if the result set is empty
                if (rs.next()) {
                    return rs.getString("ROLE");
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e; // Re-throw the exception for handling in the calling code
            }
        }
        return null;
    }

    public List<Reviewer> getReviewers() {
        List<Reviewer> reviewers = new ArrayList<>();

        String sql = "SELECT * FROM USERS WHERE ROLE = 'Reviewer'";

        if(stmt != null) {
            try {
                rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    reviewers.add(new Reviewer(rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("NAME"), rs.getString("EMAIL")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return reviewers;
    }

    public List<Venue> getVenues() {
        List<Venue> venues = new ArrayList<>();

        String sql = "SELECT * FROM Venue";

        if(stmt != null) {
            try {
                rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    venues.add(new Venue(rs.getInt("VENUE_ID"), rs.getString("LOCATION")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return venues;
    }

    public Venue getVenue(String location) {
        String sql = "SELECT * FROM Venue WHERE location = ?";

        if(stmt != null) {
            try {
                java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, location);

                rs = preparedStatement.executeQuery();

                if(rs.next()) {
                    return new Venue(rs.getInt("VENUE_ID"), rs.getString("LOCATION"));
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public boolean registerConference(Conference conference) {
        int totalConferences = getTotalNumberOfConferences();

        String sql = "INSERT INTO Conference (name, start_date, end_date, deadline, conference_code, organizer_id, venue_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        if (stmt != null) {
            try {
                java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, conference.getName());
                preparedStatement.setString(2, String.valueOf(Date.valueOf(conference.getStartDate())));
                preparedStatement.setString(3, String.valueOf(Date.valueOf(conference.getEndDate())));
                preparedStatement.setString(4, String.valueOf(Date.valueOf(conference.getDeadline())));
                preparedStatement.setString(5, "CONF00" + totalConferences);
                preparedStatement.setInt(6, conference.getOrganizerId());
                preparedStatement.setInt(7, conference.getVenueId());

                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return false;
    }

    public boolean checkForConference(String name) {
        String sql = "SELECT * FROM Conference WHERE name = ?";

        if(stmt != null) {
            try {
                java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, name);

                rs = preparedStatement.executeQuery();

                if(rs.next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public int getTotalNumberOfConferences() {
        String sql = "SELECT COUNT(*) FROM Conference";

        if(stmt != null) {
            try {
                rs = stmt.executeQuery(sql);
                if(rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public void assignReviewerToConference(int conferenceId, int reviewerId) {
        String sql = "INSERT INTO Reviewer_Conference (conference_id, reviewer_id ) VALUES (?, ?)";

        if(stmt != null) {
            try {
                java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setInt(1, reviewerId);
                preparedStatement.setInt(2, conferenceId);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) throws SQLException {
        DBConnector dbConnector = new DBConnector();

        System.out.println(dbConnector.getUser("rev1").getId());


    }

}
