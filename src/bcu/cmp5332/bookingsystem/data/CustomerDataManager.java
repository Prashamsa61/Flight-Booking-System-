package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The CustomerDataManager class is responsible for loading and storing customer data.
 * It implements the DataManager interface.
 */
public class CustomerDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/customers.txt";
    private final String SEPARATOR = ",";

    /**
     * Loads customer data from the file and adds it to the FlightBookingSystem.
     * @param fbs The FlightBookingSystem object.
     * @throws IOException If an I/O error occurs.
     * @throws FlightBookingSystemException If there is an error in the FlightBookingSystem.
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                int id = Integer.parseInt(properties[0]);
                String name = properties[1];
                String phone = properties[2];
                String email = properties[3];
                Customer customer = new Customer(id, name, phone, email);
                fbs.addCustomer(customer);
            }
        }
    }

    /**
     * Stores customer data from the FlightBookingSystem to the file.
     * @param fbs The FlightBookingSystem object.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getName() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR);
                out.println();
            }
        }
    }
}
