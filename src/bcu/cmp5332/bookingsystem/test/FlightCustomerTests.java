package bcu.cmp5332.bookingsystem.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.Test;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;

/**
 * The FlightCustomerTests class contains JUnit tests for the Flight and Customer classes.
 */
public class FlightCustomerTests {

    /**
     * Tests the Flight class.
     * @throws IOException If an I/O error occurs.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    @Test
    public void testFlight() throws IOException, FlightBookingSystemException {
        Flight flight = new Flight(1, "F001", "London", "New York", LocalDate.of(2024, 3, 1), 200, 1000);
        assertNotNull(flight);
        assertEquals(1, flight.getId());
        assertEquals("F001", flight.getFlightNumber());
        assertEquals("London", flight.getOrigin());
        assertEquals("New York", flight.getDestination());
        assertEquals(LocalDate.of(2024, 3, 1), flight.getDepartureDate());
        assertEquals(200, flight.getNumberOfSeats());
        assertEquals(1000, flight.getPrice());
    }

    /**
     * Tests the Customer class.
     * @throws IOException If an I/O error occurs.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    @Test
    public void testCustomer() throws IOException, FlightBookingSystemException {
        Customer customer = new Customer(1, "John Doe", "1234567890", "john.doe@example.com");
        assertNotNull(customer);
        assertEquals(1, customer.getId());
        assertEquals("John Doe", customer.getName());
        assertEquals("1234567890", customer.getPhone());
        assertEquals("john.doe@example.com", customer.getEmail());
    }
}
