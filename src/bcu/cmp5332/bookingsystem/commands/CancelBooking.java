package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CancelBooking implements Command {

    private final int customerId;
    private final int flightId;

    public CancelBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        Booking booking = customer.getBookingByFlightId(flightId);
        if (booking == null) {
            throw new FlightBookingSystemException("Booking for customer with ID " + customerId + " and flight with ID " + flightId + " not found.");
        }

        customer.removeBooking(booking);
        flight.removePassenger(customer);
        
        System.out.println("Booking was cancelled successfully.");

        // Remove booking data from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/data/bookings.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int bookingId = Integer.parseInt(parts[0]);
                int customerIdFromFile = Integer.parseInt(parts[1]);
                int flightIdFromFile = Integer.parseInt(parts[2]);
                if (bookingId != booking.getId() || customerIdFromFile != customer.getId() || flightIdFromFile != flight.getId()) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error writing to bookings.txt: " + e.getMessage());
        }
    }
}
