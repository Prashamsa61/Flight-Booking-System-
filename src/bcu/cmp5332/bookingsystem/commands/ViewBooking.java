package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The ViewBooking class represents a command to view bookings for a specific customer on a specific flight.
 */
public class ViewBooking implements Command {

    private final int customerId;
    private final int flightId;

    /**
     * Constructs a new ViewBooking object with the specified customer ID and flight ID.
     *
     * @param customerId The ID of the customer.
     * @param flightId   The ID of the flight.
     */
    public ViewBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    /**
     * Executes the command to view bookings for the specified customer on the specified flight.
     *
     * @param fbs The FlightBookingSystem object.
     * @throws FlightBookingSystemException If the customer or flight is not found, or if there are no bookings for the specified customer and flight.
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

        List<Booking> bookings = readBookingsFromFile("resources/data/bookings.txt", fbs);
        if (bookings.isEmpty()) {
            throw new FlightBookingSystemException("No booking found for customer with ID " + customerId + " and flight with ID " + flightId);
        }

        // Display the bookings in the console
        System.out.println("Bookings for customer " + customer.getName() + " on flight " + flight.getFlightNumber() + ":");
//        for (Booking booking : bookings) {
//            System.out.println(booking);
//        }
    }

    /**
     * Reads bookings from the specified file and returns a list of bookings for the specified customer and flight.
     *
     * @param filename The name of the file to read bookings from.
     * @param fbs      The FlightBookingSystem object.
     * @return A list of bookings for the specified customer and flight.
     * @throws FlightBookingSystemException If there is an error reading the file or parsing the bookings.
     */
    private List<Booking> readBookingsFromFile(String filename, FlightBookingSystem fbs) throws FlightBookingSystemException {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int bookingId = Integer.parseInt(parts[0]);
                int customerId = Integer.parseInt(parts[1]);
                int flightId = Integer.parseInt(parts[2]);
                LocalDate bookingDate = LocalDate.parse(parts[3]);

                if (customerId == this.customerId && flightId == this.flightId) {
                    Customer customer = fbs.getCustomerByID(customerId);
                    Flight flight = fbs.getFlightByID(flightId);
                    Booking booking = new Booking(bookingId, customer, flight, bookingDate);
                    bookings.add(booking);
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new FlightBookingSystemException("Error reading bookings from file: " + e.getMessage());
        }
        return bookings;
    }
}
