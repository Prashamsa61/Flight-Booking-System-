package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The FlightBookingSystemData class provides methods for loading and storing flight booking system data.
 * It contains static methods for loading and storing data from/to text files.
 */
public class FlightBookingSystemData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>();
    
    // runs only once when the object gets loaded to memory
    static {
        dataManagers.add(new FlightDataManager());
        
        /* Uncomment the two lines below when the implementation of their 
        loadData() and storeData() methods is complete */
        dataManagers.add(new CustomerDataManager());
        dataManagers.add(new BookingDataManager());
    }
    
    /**
     * Loads flight booking system data from text files.
     *
     * @return The flight booking system with loaded data.
     * @throws FlightBookingSystemException If an error occurs while loading the data.
     * @throws IOException If an I/O error occurs while loading the data.
     */
    public static FlightBookingSystem load() throws FlightBookingSystemException, IOException {

        FlightBookingSystem fbs = new FlightBookingSystem();
        for (DataManager dm : dataManagers) {
            dm.loadData(fbs);
        }
        return fbs;
    }

    /**
     * Stores flight booking system data to text files.
     *
     * @param fbs The flight booking system to be stored.
     * @throws IOException If an I/O error occurs while storing the data.
     */
    public static void store(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("resources/data/customers.txt"))) {
            for (Customer customer : fbs.getCustomers()) {
                writer.println(customer.getId() + "," + customer.getName() + "," + customer.getPhone() + "," + customer.getEmail());
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("resources/data/flights.txt"))) {
            for (Flight flight : fbs.getFlights()) {
                writer.println(flight.getId() + "," + flight.getFlightNumber() + "," + flight.getOrigin() + "," + flight.getDestination() + "," + flight.getDepartureDate() + "," + flight.getNumberOfSeats() + "," + flight.getPrice());
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("resources/data/bookings.txt"))) {
            for (Booking booking : fbs.getBookings()) {
                writer.println(booking.getId() + "," + booking.getCustomer().getId() + "," + booking.getFlight().getId() + "," + booking.getBookingDate());
            }
        }
    }

    


}
