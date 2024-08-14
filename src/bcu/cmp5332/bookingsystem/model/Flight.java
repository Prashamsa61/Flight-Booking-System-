package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Flight class represents a flight in the flight booking system.
 * It contains information about the flight such as its ID, flight number, origin, destination,
 * departure date, number of seats, price, and the list of passengers.
 */
public class Flight {
    
    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private int numberOfSeats;
    private int price;
   


    private final Set<Customer> passengers;

    /**
     * Constructs a new Flight object with the specified parameters.
     *
     * @param id The unique identifier for the flight.
     * @param flightNumber The flight number.
     * @param origin The origin of the flight.
     * @param destination The destination of the flight.
     * @param departureDate The departure date of the flight.
     * @param numberOfSeats The number of available seats on the flight.
     * @param price The price of the flight.
     */
    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int numberOfSeats, int price) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        
        passengers = new HashSet<>();
    }

    /**
     * Returns the unique identifier for the flight.
     *
     * @return The flight ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the flight.
     *
     * @param id The flight ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the flight number.
     *
     * @return The flight number.
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the flight number.
     *
     * @param flightNumber The flight number.
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    /**
     * Returns the origin of the flight.
     *
     * @return The origin of the flight.
     */
    public String getOrigin() {
        return origin;
    }
    
    /**
     * Sets the origin of the flight.
     *
     * @param origin The origin of the flight.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Returns the destination of the flight.
     *
     * @return The destination of the flight.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination of the flight.
     *
     * @param destination The destination of the flight.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Returns the departure date of the flight.
     *
     * @return The departure date of the flight.
     */
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the departure date of the flight.
     *
     * @param departureDate The departure date of the flight.
     */
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Returns the number of available seats on the flight.
     *
     * @return The number of available seats on the flight.
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the number of available seats on the flight.
     *
     * @param numberOfSeats The number of available seats on the flight.
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Returns the price of the flight.
     *
     * @return The price of the flight.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the flight.
     *
     * @param price The price of the flight.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Returns a list of passengers on the flight.
     *
     * @return A list of passengers on the flight.
     */
    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }
	
    /**
     * Returns a short string representation of the flight details.
     *
     * @return A short string representation of the flight details.
     */
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " 
                + destination + " on " + departureDate.format(dtf) + " - Price: " + price + " - Seats: " + numberOfSeats;
    }

    /**
     * Returns a long string representation of the flight details.
     *
     * @return A long string representation of the flight details.
     */
    public String getDetailsLong() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        StringBuilder sb = new StringBuilder();
        sb.append("Flight #").append(id).append("\n");
        sb.append("Flight Number: ").append(flightNumber).append("\n");
        sb.append("Origin: ").append(origin).append("\n");
        sb.append("Destination: ").append(destination).append("\n");
        sb.append("Departure Date: ").append(departureDate.format(dtf)).append("\n");
        sb.append("Number of Seats: ").append(numberOfSeats).append("\n");
        sb.append("Price: ").append(price).append("\n");
        sb.append("Passengers: ").append("\n");
        for (Customer passenger : passengers) {
            sb.append(passenger.getName()).append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Adds a passenger to the flight.
     *
     * @param passenger The customer to add.
     */
    public void addPassenger(Customer customer) {
        passengers.add(customer);
    }
    /**
     * Adds a passenger to the flight.
     *
     * @param passenger The customer to remove
     */

	public void removePassenger(Customer customer) {
		passengers.remove(customer);
		
	}




}
