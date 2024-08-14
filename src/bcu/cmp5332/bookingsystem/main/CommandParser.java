package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.*;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user input and returns the corresponding command.
 */
public class CommandParser {

    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param line The user input.
     * @param fbs  The FlightBookingSystem object.
     * @return The corresponding command.
     * @throws IOException                  If an I/O error occurs.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    public static Command parse(String line, FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try {
            String[] parts = line.split(" ", 3);
            String cmd = parts[0];

            if (cmd.equals("addflight")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Flight Number: ");
                String flightNumber = reader.readLine();
                System.out.print("Origin: ");
                String origin = reader.readLine();
                System.out.print("Destination: ");
                String destination = reader.readLine();
                System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
                LocalDate departureDate = LocalDate.parse(reader.readLine());
                System.out.print("Number of Seats: ");
                int numberOfSeats = Integer.parseInt(reader.readLine());
                System.out.print("Price: ");
                int price = Integer.parseInt(reader.readLine());

                return new AddFlight(flightNumber, origin, destination, departureDate, numberOfSeats, price);

            } else if (cmd.equals("addcustomer")) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Customer Name: ");
                String name = reader.readLine();
                System.out.print("Customer Phone: ");
                String phone = reader.readLine();
                System.out.print("Customer Email: ");
                String email = reader.readLine();

                return new AddCustomer(name, phone, email);

            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
            } else if (parts.length == 1) {
                if (line.equals("listflights")) {
                    return new ListFlights();
                } else if (line.equals("listcustomers")) {
                    return new ListCustomers();
                } else if (line.equals("help")) {
                    return new Help();
                }
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showflight")) {
                    return new ShowFlight(id);
                } else if (cmd.equals("showcustomer")) {
                    return new ShowCustomer(id);
                }
            } else if (parts.length == 3) {
                if (cmd.equals("addbooking")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Customer ID: ");
                    int customerId = Integer.parseInt(reader.readLine());
                    System.out.print("Flight ID: ");
                    int flightId = Integer.parseInt(reader.readLine());
                    System.out.print("Booking Date (\"YYYY-MM-DD\" format): ");
                    LocalDate bookingDate = LocalDate.parse(reader.readLine());

                    return new AddBooking(customerId, flightId, bookingDate);

                } else if (cmd.equals("editbooking")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Customer ID: ");
                    int customerId = Integer.parseInt(reader.readLine());
                    System.out.print("Flight ID: ");
                    int flightId = Integer.parseInt(reader.readLine());
                    System.out.print("New Booking Date (\"YYYY-MM-DD\" format): ");
                    LocalDate newBookingDate = LocalDate.parse(reader.readLine());

                    return new EditBooking(customerId, flightId, newBookingDate);

                } else if (cmd.equals("cancelbooking")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Customer ID: ");
                    int customerId = Integer.parseInt(reader.readLine());
                    System.out.print("Flight ID: ");
                    int flightId = Integer.parseInt(reader.readLine());

                    return new CancelBooking(customerId, flightId);

                } else if (cmd.equals("viewBooking")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Customer ID: ");
                    int customerId = Integer.parseInt(reader.readLine());
                    System.out.print("Flight ID: ");
                    int flightId = Integer.parseInt(reader.readLine());

                    return new ViewBooking(customerId, flightId);
                }
            }
        } catch (NumberFormatException ex) {
            // Handle NumberFormatException
        }

        throw new FlightBookingSystemException("Invalid command.");
    }

    /**
     * Parses the date with attempts.
     *
     * @param br       The BufferedReader object.
     * @param attempts The number of attempts.
     * @return The parsed LocalDate object.
     * @throws IOException                  If an I/O error occurs.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws IOException, FlightBookingSystemException {
        if (attempts < 1) {
            throw new IllegalArgumentException("Number of attempts should be higher that 0");
        }
        while (attempts > 0) {
            attempts--;
            System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
            try {
                LocalDate departureDate = LocalDate.parse(br.readLine());
                return departureDate;
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
            }
        }

        throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
    }

    /**
     * Parses the date with attempts.
     *
     * @param br The BufferedReader object.
     * @return The parsed LocalDate object.
     * @throws IOException                  If an I/O error occurs.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    private static LocalDate parseDateWithAttempts(BufferedReader br) throws IOException, FlightBookingSystemException {
        return parseDateWithAttempts(br, 3);
    }
}

