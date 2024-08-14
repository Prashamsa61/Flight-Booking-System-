package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The ShowFlight class represents a command to display details of a specific flight.
 */
public class ShowFlight implements Command {

    private final int flightId;

    /**
     * Constructs a new ShowFlight object with the specified flight ID.
     *
     * @param flightId The ID of the flight to display.
     */
    public ShowFlight(int flightId) {
        this.flightId = flightId;
    }

    /**
     * Executes the command to display details of the flight with the specified ID.
     *
     * @param fbs The FlightBookingSystem object.
     * @throws FlightBookingSystemException If the flight with the specified ID is not found.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Flight flight = fbs.getFlightByID(flightId);
        if (flight != null) {
            System.out.println(flight.getDetailsLong());
        } else {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }
    }
}
