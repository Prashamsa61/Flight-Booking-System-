package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ListCustomers class represents a command to list all customers in the flight booking system.
 */
public class ListCustomers implements Command {

    /**
     * Executes the command to list all customers in the flight booking system.
     *
     * @param flightBookingSystem The FlightBookingSystem object.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Customer> customers = readCustomersFromFile("resources/data/customers.txt");
        for (Customer customer : customers) {
            System.out.println(customer.getDetailsShort());
        }
        System.out.println(customers.size() + " customer(s)");
    }

    /**
     * Reads customers from a file and returns a list of Customer objects.
     *
     * @param filename The name of the file to read customers from.
     * @return A list of Customer objects.
     * @throws FlightBookingSystemException If there is an error reading customers from the file.
     */
    private List<Customer> readCustomersFromFile(String filename) throws FlightBookingSystemException {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String phone = parts[2];
                String email = parts[3];
                Customer customer = new Customer(id, name, phone, email);
                customers.add(customer);
            }
        } catch (IOException | NumberFormatException e) {
            throw new FlightBookingSystemException("Error reading customers from file: " + e.getMessage());
        }
        return customers;
    }

}
