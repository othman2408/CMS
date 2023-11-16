package cms.DB;

import cms.Entity.Organizer;
import cms.Entity.User;

import java.sql.*;
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
        Connection conn = DriverManager.getConnection(dburl, username, password);
        return conn;
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


    public static void main(String[] args) {
        User u1 = new Organizer("dsds", "123", "Othman alibrahim", "dsd");

        try {
            DBConnector db = new DBConnector();
            db.registerUser(u1);
        } catch (SQLException throwables) {
            System.out.println("Error: " + throwables.getMessage());
        }
    }

}
