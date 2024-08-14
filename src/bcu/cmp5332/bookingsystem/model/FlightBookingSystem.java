package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.commands.ViewBooking;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * The FlightBookingSystem class represents the main system that manages flights, customers, and bookings.
 */
public class FlightBookingSystem {
    
    private final LocalDate systemDate = LocalDate.parse("2020-11-11");
    
    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private final Map<Integer, Booking> bookings = new TreeMap<>();

    /**
     * Gets the system date.
     * @return The system date.
     */
    public LocalDate getSystemDate() {
        return systemDate;
    }
  
    /**
     * Gets the list of flights.
     * @return The list of flights.
     */
    public List<Flight> getFlights() {
    	List<Flight> out = new ArrayList<>();
        for (Flight flight : flights.values()) {
            if (flight.getDepartureDate().isAfter(systemDate)) {
                out.add(flight);
            }
        }
        return Collections.unmodifiableList(out);
    }
    
    /**
     * Gets the list of customers.
     * @return The list of customers.
     */
    public List<Customer> getCustomers() {
        List<Customer> out = new ArrayList<>(customers.values());
        return Collections.unmodifiableList(out);
    }
    
    /**
     * Gets the list of bookings.
     * @return The list of bookings.
     */
    public List<Booking> getBookings() {
        List<Booking> out = new ArrayList<>(bookings.values());
        return Collections.unmodifiableList(out);
    }

    /**
     * Gets a flight by its ID.
     * @param id The flight ID.
     * @return The flight with the specified ID.
     * @throws FlightBookingSystemException If the flight with the specified ID is not found.
     */
    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        return flights.get(id);
    }

    /**
     * Gets a customer by their ID.
     * @param id The customer ID.
     * @return The customer with the specified ID.
     * @throws FlightBookingSystemException If the customer with the specified ID is not found.
     */
    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        if (!customers.containsKey(id)) {
            throw new FlightBookingSystemException("There is no customer with that ID.");
        }
        return customers.get(id);
    }

    /**
     * Gets a booking by its ID.
     * @param id The booking ID.
     * @return The booking with the specified ID.
     * @throws FlightBookingSystemException If the booking with the specified ID is not found.
     */
    public Booking getBookingByID(int id) throws FlightBookingSystemException {
        if (!bookings.containsKey(id)) {
            throw new FlightBookingSystemException("There is no booking with that ID.");
        }
        return bookings.get(id);
    }

    /**
     * Adds a flight to the system.
     * @param flight The flight to be added.
     * @throws FlightBookingSystemException If there is a duplicate flight ID or a flight with the same number and departure date already exists in the system.
     */
    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber()) 
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with same "
                        + "number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }

    /**
     * Adds a customer to the system.
     * @param customer The customer to be added.
     * @throws FlightBookingSystemException If there is a duplicate customer ID.
     */
    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Duplicate customer ID.");
        }
        customers.put(customer.getId(), customer);
    }
    
    
    /**
     * Adds a booking to the system.
     * @param booking The booking to be added.
     * @throws FlightBookingSystemException If the customer or flight associated with the booking is not found, or if there is a duplicate booking ID.
     */
    public void addBooking(Booking booking) throws FlightBookingSystemException {
        // Get the customer and flight associated with the booking
        Customer customer = booking.getCustomer();
        Flight flight = booking.getFlight();
        
        // Check if the customer and flight exist in the system
        if (customer == null || flight == null) {
            throw new FlightBookingSystemException("Customer or Flight not found.");
        }
        
        // Check if the booking ID already exists
        if (bookings.containsKey(booking.getId())) {
            throw new IllegalArgumentException("Duplicate booking ID.");
        }
        
        // Add the booking to the system
        bookings.put(booking.getId(), booking);
    }

    /**
     * Gets the bookings for a specific customer and flight.
     * @param customer The customer.
     * @param flight The flight.
     * @return The list of bookings for the specified customer and flight.
     */
    public List<Booking> getBookingsByCustomerAndFlight(Customer customer, Flight flight) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getCustomer().equals(customer) && booking.getFlight().equals(flight)) {
                result.add(booking);
            }
        }
        return result;
    }

    /**
     * Deletes a flight from the system.
     * @param flightId The ID of the flight to be deleted.
     * @throws FlightBookingSystemException If the flight is not found.
     */
    public void deleteFlight(int flightId) throws FlightBookingSystemException {
        Flight flight = getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight not found.");
        }
        List<Booking> flightBookings = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getFlight().equals(flight)) {
                flightBookings.add(booking);
            }
        }
        for (Booking booking : flightBookings) {
            bookings.remove(booking.getId());
        }
        flights.remove(flightId);
    }
    
    /**
     * Cancels a booking.
     * @param customerId The ID of the customer.
     * @param flightId The ID of the flight.
    */

    public void cancelBooking(int customerId, int flightId) throws FlightBookingSystemException {
        // Create an instance of the CancelBooking class
        CancelBooking cancelBooking = new CancelBooking(customerId, flightId);

        // Call the cancelBooking method of the CancelBooking instance
        cancelBooking.execute(this);
    }

    /**
     * Views bookings for a specific customer and flight.
     * @param customerId The ID of the customer.
     * @param flightId The ID of the flight.
     * @throws FlightBookingSystemException If there is an error viewing the bookings.
     */
    public void viewBookings(int customerId, int flightId) throws FlightBookingSystemException {
        ViewBooking viewBooking = new ViewBooking(customerId, flightId);
        viewBooking.execute(this);
    }

    /**
     * Deletes a customer from the system.
     * @param customerId The ID of the customer to be deleted.
     * @throws FlightBookingSystemException If the customer is not found or there is an error deleting the customer.
     */
    public void deleteCustomer(int customerId) throws FlightBookingSystemException {
        Customer customer = getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer not found.");
        }
        customers.remove(customerId);

        // Open the customers.txt file for reading
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/data/customers.txt"))) {
            // Create a StringBuilder to store the updated contents of the customers.txt file
            StringBuilder updatedContents = new StringBuilder();

            // Read each line from the customers.txt file
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into fields
                String[] fields = line.split(",");

                // If the customer ID matches the one you want to delete, skip that line
                if (fields.length > 0 && Integer.parseInt(fields[0].trim()) == customerId) {
                    continue;
                }

                // Append the line to the updatedContents StringBuilder
                updatedContents.append(line).append(System.lineSeparator());
            }

            // Close the reader
            reader.close();

            // Write the updated contents to the customers.txt file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/customers.txt"))) {
                writer.write(updatedContents.toString());
            }
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error deleting customer from file.");
        }
    }

    /**
     * Gets a booking by customer and flight ID.
     * @param customerId The ID of the customer.
     * @param flightId The ID of the flight.
     * @return The booking with the specified customer and flight ID.
     */
    public Booking getBookingByCustomerAndFlightId(int customerId, int flightId) {
        for (Booking booking : bookings.values()) {
            if (booking.getCustomer().getId() == customerId && booking.getFlight().getId() == flightId) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Updates a booking with a new booking date.
     * @param bookingId The ID of the booking to be updated.
     * @param newBookingDate The new booking date.
     * @throws FlightBookingSystemException If the booking or flight is not found.
     */
    public void updateBooking(int bookingId, LocalDate newBookingDate) throws FlightBookingSystemException {
        Booking booking = getBookingByID(bookingId);
        if (booking == null) {
            throw new FlightBookingSystemException("Booking not found.");
        }
        Flight flight = booking.getFlight();
        if (flight == null) {
            throw new FlightBookingSystemException("Flight not found.");
        }

        // Calculate the rebook fee based on the number of days left for the flight to depart
        int daysLeft = (int) ChronoUnit.DAYS.between(systemDate, flight.getDepartureDate());
        int rebookFee = calculateRebookFee(daysLeft);

        // Apply the rebook fee
        booking.setBookingDate(newBookingDate);
        booking.setPrice(booking.getPrice() + rebookFee);
    }

    /**
     * Calculates the rebook fee based on the number of days left for the flight to depart.
     * @param daysLeft The number of days left for the flight to depart.
     * @return The rebook fee.
     */
    private int calculateRebookFee(int daysLeft) {
        // Implement your logic to calculate the rebook fee based on the number of days left
        // For example, you can use a fixed amount per day or a percentage of the booking price
        int rebookFee = 0;

        if (daysLeft >= 30) {
            rebookFee = 50; // Rebook fee for flights more than 30 days from departure
        } else if (daysLeft >= 15) {
            rebookFee = 100; // Rebook fee for flights between 30 and 15 days from departure
        } else if (daysLeft >= 7) {
            rebookFee = 150; // Rebook fee for flights
        } else if (daysLeft >= 3) {
            rebookFee = 200; // Rebook fee for flights between 7 and 3 days from departure
        } else {
            rebookFee = 250; // Rebook fee for flights less than 3 days from departure
        }

        return rebookFee;
    }

    /**
     * Gets the bookings for a specific flight.
     * @param flight The flight.
     * @return The list of bookings for the specified flight.
     */
    public List<Booking> getBookingsByFlight(Flight flight) {
        List<Booking> bookingsByFlight = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getFlight().getId() == flight.getId()) {
                bookingsByFlight.add(booking);
            }
        }
        return bookingsByFlight;
    }
}
