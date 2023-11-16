package cms.Entity;

import java.util.List;

public class Hotel {
    private String hotelName;
    private int reservationId;
    private double price;

    private List<Reservation> reservations;

    public Hotel() {
    }

    public Hotel(String hotelName, int reservationId, double price, List<Reservation> reservations) {
        this.hotelName = hotelName;
        this.reservationId = reservationId;
        this.price = price;
        this.reservations = reservations;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelName='" + hotelName + '\'' +
                ", reservationId=" + reservationId +
                ", price=" + price +
                ", reservations=" + reservations +
                '}';
    }
}
