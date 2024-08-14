package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The AddFlight class represents a command to add a flight to the flight booking system.
 */
public class AddFlight implements Command {

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;
    private final int numberOfSeats;
    private final int price;

    /**
     * Initializes a new instance of the AddFlight class with the specified flight details.
     *
     * @param flightNumber   The flight number.
     * @param origin         The origin of the flight.
     * @param destination    The destination of the flight.
     * @param departureDate  The departure date of the flight.
     * @param numberOfSeats  The number of seats available on the flight.
     * @param price          The price of the flight.
     */
    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int numberOfSeats, int price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
    }

    /**
     * Executes the command to add a flight to the flight booking system.
     *
     * @param flightBookingSystem The FlightBookingSystem object.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = 0;
        if (flightBookingSystem.getFlights().size() > 0) {
            int lastIndex = flightBookingSystem.getFlights().size() - 1;
            maxId = flightBookingSystem.getFlights().get(lastIndex).getId();
        }

        Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate, numberOfSeats, price);
        flightBookingSystem.addFlight(flight);
        System.out.println("Flight #" + flight.getId() + " added.");

        // Write the new flight data to the flights.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/flights.txt", true))) {
            writer.write(flight.getId() + "," + flight.getFlightNumber() + "," + flight.getOrigin() + "," + flight.getDestination() + "," + flight.getDepartureDate() + "," + flight.getNumberOfSeats() + "," + flight.getPrice());
            writer.newLine();
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error writing to flights.txt: " + ex.getMessage());
        }
    }
}
