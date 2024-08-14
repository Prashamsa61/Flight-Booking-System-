package bcu.cmp5332.bookingsystem.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The AddCustomer class represents a command to add a customer to the flight booking system.
 */
public class AddCustomer implements Command {

    private final String name;
    private final String phone;
    private final String email;

    /**
     * Initializes a new instance of the AddCustomer class with the specified name, phone, and email.
     *
     * @param name  The name of the customer.
     * @param phone The phone number of the customer.
     * @param email The email address of the customer.
     */
    public AddCustomer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Executes the command to add a customer to the flight booking system.
     *
     * @param fbs The FlightBookingSystem object.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        int maxId = 0;
        if (fbs.getCustomers().size() > 0) {
            int lastIndex = fbs.getCustomers().size() - 1;
            maxId = fbs.getCustomers().get(lastIndex).getId();
        }

        Customer customer = new Customer(++maxId, name, phone, email);
        fbs.addCustomer(customer);
        System.out.println("Customer #" + customer.getId() + " added.");

        // Write customer data to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/customers.txt", true))) {
            writer.write(customer.getId() + "," + customer.getName() + "," + customer.getPhone() + "," + customer.getEmail());
            writer.newLine();
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error writing to customers.txt: " + e.getMessage());
        }
    }
}
