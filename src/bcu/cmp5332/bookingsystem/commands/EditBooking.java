package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The EditBooking class represents a command to edit a booking in the flight booking system.
 */
public class EditBooking implements Command {

    private final int customerId;
    private final int flightId;
    private final LocalDate newBookingDate;

    /**
     * Initializes a new instance of the EditBooking class with the specified customer ID, flight ID, and new booking date.
     *
     * @param customerId     The ID of the customer.
     * @param flightId       The ID of the flight.
     * @param newBookingDate The new booking date.
     */
    public EditBooking(int customerId, int flightId, LocalDate newBookingDate) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.newBookingDate = newBookingDate;
    }

    /**
     * Executes the command to edit a booking in the flight booking system.
     *
     * @param fbs The FlightBookingSystem object.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer not found.");
        }
        
        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight not found.");
        }
        
        // Find the booking that matches the customer ID and flight ID
        Booking booking = fbs.getBookingByCustomerAndFlightId(customerId, flightId);
        if (booking == null) {
            throw new FlightBookingSystemException("Booking not found for the given customer and flight.");
        }
        
        // Check if the new booking date is valid
        if (newBookingDate.isBefore(LocalDate.now()) || newBookingDate.isAfter(flight.getDepartureDate())) {
            throw new FlightBookingSystemException("Invalid booking date.");
        }
        
        // Update the booking date
        booking.setBookingDate(newBookingDate);
        
        // Store the updated booking information in the file
        try {
            FlightBookingSystemData.store(fbs);
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error updating bookings file.");
        }
    }
}
