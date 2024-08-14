package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The ListFlights class represents a command to list all flights in the flight booking system.
 */
public class ListFlights implements Command {

    /**
     * Executes the command to list all flights in the flight booking system.
     *
     * @param flightBookingSystem The FlightBookingSystem object.
     * @throws FlightBookingSystemException If there is an error in the flight booking system.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Flight> flights = readFlightsFromFile("resources/data/flights.txt");
        for (Flight flight : flights) {
            System.out.println(flight.getDetailsShort());
        }
        System.out.println(flights.size() + " flight(s)");
    }

    /**
     * Reads flights from a file and returns a list of Flight objects.
     *
     * @param filename The name of the file to read flights from.
     * @return A list of Flight objects.
     * @throws FlightBookingSystemException If there is an error reading flights from the file.
     */
    private List<Flight> readFlightsFromFile(String filename) throws FlightBookingSystemException {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String flightNumber = parts[1];
                String origin = parts[2];
                String destination = parts[3];
                LocalDate departureDate = LocalDate.parse(parts[4]);
                int numberOfSeats = Integer.parseInt(parts[5]);
                int price = Integer.parseInt(parts[6]);
                Flight flight = new Flight(id, flightNumber, origin, destination, departureDate, numberOfSeats, price);
                flights.add(flight);
            }
        } catch (IOException | NumberFormatException e) {
            throw new FlightBookingSystemException("Error reading flights from file: " + e.getMessage());
        }
        return flights;
    }
}
