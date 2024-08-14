package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Represents a data manager for handling bookings data.
 */
public class BookingDataManager implements DataManager {

    private final static String RESOURCE = "./resources/data/bookings.txt";
    private final static String SEPARATOR = ",";

    /**
     * Loads bookings data from the specified file into the flight booking system.
     * @param fbs The FlightBookingSystem object.
     * @throws IOException If an I/O error occurs.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                int id = Integer.parseInt(properties[0]);
                int customerId = properties[1].isEmpty() ? 0 : Integer.parseInt(properties[1]);
                int flightId = properties[2].isEmpty() ? 0 : Integer.parseInt(properties[2]);
                LocalDate date = LocalDate.parse(properties[3]);
                Customer customer = fbs.getCustomerByID(customerId);
                Flight flight = fbs.getFlightByID(flightId);
                if (customer != null && flight != null) {
                    Booking booking = new Booking(id, customer, flight, date);
                    customer.addBooking(booking);
                    flight.addPassenger(customer);
                }
            }
        }
    }

   

    /**
     * Stores bookings data from the flight booking system into the specified file.
     * @param fbs The FlightBookingSystem object.
     * @throws IOException If an I/O error occurs.
     */
@Override
public void storeData(FlightBookingSystem fbs) throws IOException {
    try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
        for (Customer customer : fbs.getCustomers()) {
            for (Booking booking : customer.getBookings()) {
                // Check if the booking already exists
                if (!bookingExists(out, booking)) {
                    out.print(booking.getId() + SEPARATOR);
                    out.print(customer.getId() + SEPARATOR);
                    out.print(booking.getFlight().getId() + SEPARATOR);
                    out.print(booking.getBookingDate() + SEPARATOR);
                    out.println();
                }
            }
        }
    }
}


   
    /**
     * Checks if a booking already exists in the specified PrintWriter.
     * @param out The PrintWriter object.
     * @param booking The Booking object to check.
     * @return True if the booking exists, otherwise false.
     */
private boolean bookingExists(PrintWriter out, Booking booking) {
    // Implement this method to check if the booking already exists in the file
    return false;
}
}


