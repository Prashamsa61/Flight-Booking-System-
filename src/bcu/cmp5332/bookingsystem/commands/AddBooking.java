package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The AddBooking class represents a command to add a booking to the flight booking system.
 */
public class AddBooking implements Command {

    private final int customerId;
    private final int flightId;

    /**
     * Initializes a new instance of the AddBooking class with the specified customer ID, flight ID, and booking date.
     *
     * @param customerId The ID of the customer.
     * @param flightId   The ID of the flight.
     * @param localDate  The booking date.
     */
    public AddBooking(int customerId, int flightId, LocalDate localDate) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    /**
     * Executes the command to add a booking to the flight booking system.
     *
     * @param fbs The FlightBookingSystem object.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        int maxId = 0;
        for (Booking booking : fbs.getBookings()) {
            if (booking.getId() > maxId) {
                maxId = booking.getId();
            }
        }

        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        Booking booking = new Booking(++maxId, customer, flight, LocalDate.now());
        customer.addBooking(booking);
        flight.addPassenger(customer);

        System.out.println("Booking was issued successfully to the customer.");

        // Write booking data to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/bookings.txt", true))) {
            writer.write(booking.getId() + "," + customer.getId() + "," + flight.getId() + "," + LocalDate.now());
            writer.newLine();
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error writing to bookings.txt: " + e.getMessage());
        }
    }
}
