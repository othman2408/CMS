package cms.Entities;

public class Reviewer extends User{

    public Reviewer() {
    }

    public Reviewer(int id, String username, String password, String name, String email) {
        super(id, username, password, name, email);
    }
    public Reviewer(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

}
