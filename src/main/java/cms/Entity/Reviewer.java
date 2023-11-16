package cms.Entity;

public class Reviewer extends User{

    public Reviewer() {
    }

    public Reviewer(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

}
