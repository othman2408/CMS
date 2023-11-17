package cms.Entity;

import cms.DB.DBConnector;

import java.sql.SQLException;
import java.util.List;

public class CMS {
    private List<User> users;
    private List<Conference> conferences;
    private List<Reservation> reservations;
    private List<Payment> payments;

    private DBConnector dbConnector;


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

    public List<Reviewer> getReviewers() throws SQLException {
        dbConnector = new DBConnector();
        return dbConnector.getReviewers();
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


    public static void main(String[] args) {


    }
}
