package cms.DB;

import cms.Entity.User;

import java.sql.*;

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


    public static void main(String[] args) throws SQLException {
        DBConnector dbConnector = new DBConnector();

        System.out.println(dbConnector.getUserRole("othman"));
    }

}
