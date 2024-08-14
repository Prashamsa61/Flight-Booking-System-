package bcu.cmp5332.bookingsystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Customer class represents a customer in the flight booking system.
 * It contains information about the customer such as their ID, name, phone number, email, balance, and list of bookings.
 */
public class Customer {

    private int id;
    private String name;
    private String phone;
    private String email;
    private int balance; // Added balance field
    private final List<Booking> bookings = new ArrayList<>();

    /**
     * Constructs a new Customer object with the specified parameters.
     *
     * @param id The unique identifier for the customer.
     * @param name The name of the customer.
     * @param phone The phone number of the customer.
     * @param email The email address of the customer.
     */
    public Customer(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Returns the unique identifier for the customer.
     *
     * @return The customer ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the customer.
     *
     * @param id The customer ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name The name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone The phone number of the customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the email address of the customer.
     *
     * @return The email address of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the customer.
     *
     * @param email The email address of the customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the list of bookings made by the customer.
     *
     * @return The list of bookings made by the customer.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking to the list of bookings made by the customer.
     *
     * @param booking The booking to be added.
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Returns a short string representation of the customer's details.
     *
     * @return A short string representation of the customer's details.
     */
    public String getDetailsShort() {
        return "Customer #" + id + " - " + name + " - " + phone + " - " + email;
    }

    /**
     * Returns the booking made by the customer for the specified flight ID.
     *
     * @param flightId The ID of the flight.
     * @return The booking made by the customer for the specified flight ID.
     */
    public Booking getBookingByFlightId(int flightId) {
        for (Booking booking : bookings) {
            if (booking.getFlight().getId() == flightId) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Returns the balance of the customer.
     *
     * @return The balance of the customer.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the customer.
     *
     * @param balance The balance of the customer.
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Removes a booking from the list of bookings made by the customer.
     *
     * @param booking The booking to be removed.
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

}
