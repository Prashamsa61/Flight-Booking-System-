package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The ShowCustomer class represents a command to display details of a specific customer.
 */
public class ShowCustomer implements Command {

    private final int customerId;

    /**
     * Constructs a new ShowCustomer object with the specified customer ID.
     *
     * @param customerId The ID of the customer to display.
     */
    public ShowCustomer(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Executes the command to display details of the customer with the specified ID.
     *
     * @param fbs The FlightBookingSystem object.
     * @throws FlightBookingSystemException If the customer with the specified ID is not found.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerByID(customerId);
        if (customer != null) {
            System.out.println(customer.getDetailsShort());
        } else {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }
    }
}
