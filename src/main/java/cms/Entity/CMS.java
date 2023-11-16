package cms.Entity;

import cms.LocalFileStorage.FileHandler;

import java.io.Serializable;
import java.util.List;

public class CMS {
    private List<User> users;
    private List<Conference> conferences;
    private List<Reservation> reservations;
    private List<Payment> payments;

    public CMS() {
    }

    public CMS(List<User> users, List<Conference> conferences, List<Reservation> reservations, List<Payment> payments) {
        this.users = users;
        this.conferences = conferences;
        this.reservations = reservations;
        this.payments = payments;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(List<Conference> conferences) {
        this.conferences = conferences;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public boolean registerUser(User user) {
        FileHandler file = new FileHandler("src/main/java/cms/LocalFileStorage/users.dat");
        try {
            List<User> users = file.readAllObjects();
            for (User u : users) {
                if (u.getUsername().equals(user.getUsername())) {
                    return false;
                }
            }
            users.add(user);
            file.saveObject((Serializable) users);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findUser(User user) {
        FileHandler file = new FileHandler("src/main/java/cms/LocalFileStorage/users.dat");
        try {
            List<User> users = file.readAllObjects();
            for (User u : users) {
                if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CMS cms = new CMS();

        User u1 = new Organizer("othman", "123", "Othman alibrahim", "othman@gmail.com");
        User u2 = new Author("ahmad", "123", "Ahmad alibrahim", "ahmed@mail.com");
        User u3 = new Reviewer("ali", "123",    "Ali alibrahim", "ali@mail.com");

        System.out.println(u1.getClass().getSimpleName());

//        cms.registerUser(u1);
//        cms.registerUser(u2);
//        cms.registerUser(u3);



    }
}
