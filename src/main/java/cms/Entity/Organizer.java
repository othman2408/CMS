package cms.Entity;

import cms.DB.DBConnector;

import java.sql.SQLException;

public class Organizer extends User{
    public Organizer() {
    }

    public Organizer(int id, String username, String password, String name, String email) {
        super(id, username, password, name, email);
    }

    public Organizer(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    @Override
    public boolean login(String username, String password) throws SQLException {
        DBConnector dbConnector = new DBConnector();
        return dbConnector.login(username, password);
    }
}
