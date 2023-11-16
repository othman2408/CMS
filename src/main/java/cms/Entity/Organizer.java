package cms.Entity;

public class Organizer extends User{
    public Organizer() {
    }

    public Organizer(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }
}
