package cms.Entity;

public class Author extends User{

    public Author() {
    }

    public Author(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }
}
