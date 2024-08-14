package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * The Booking class represents a booking made by a customer for a flight in the flight booking system.
 * It contains information about the booking such as its ID, customer, flight, booking date, and price.
 */
public class Booking {
   
	 private static int maxId = 0;
	 
    private  int id;
    private Customer customer;
    private Flight flight;
    private LocalDate bookingDate;
    private Integer price; 

    /**
     * Constructs a new Booking object with the specified parameters.
     *
     * @param id The unique identifier for the booking.
     * @param customer The customer who made the booking.
     * @param flight The flight that was booked.
     * @param bookingDate The date when the booking was made.
     */
    public Booking(int id,Customer customer, Flight flight, LocalDate bookingDate) {
    	this.id = generateId();
    	this.customer = customer;
        this.flight = flight;
        this.bookingDate = bookingDate;
    }

  

	/**
     * Generates a new unique ID for the booking.
     *
     * @return The new unique ID for the booking.
     */
    private static int generateId() {
        return ++maxId;
    }

    /**
     * Returns the unique identifier for the booking.
     *
     * @return The booking ID.
     */
	public int getId() {
        return id;
    }

    /**
     * Returns the customer who made the booking.
     *
     * @return The customer who made the booking.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made the booking.
     *
     * @param customer The customer who made the booking.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the flight that was booked.
     *
     * @return The flight that was booked.
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the flight that was booked.
     *
     * @param flight The flight that was booked.
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Returns the date when the booking was made.
     *
     * @return The date when the booking was made.
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the date when the booking was made.
     *
     * @param bookingDate The date when the booking was made.
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Returns the price of the booking.
     *
     * @return The price of the booking.
     */
    public int getPrice() {
        if (price == null) {
            // Calculate the number of days left for the flight to depart
            int daysLeft = (int) ChronoUnit.DAYS.between(LocalDate.now(), flight.getDepartureDate());

            // Calculate the price based on the number of days left and the capacity of the flight
            price = calculatePrice(daysLeft, flight.getNumberOfSeats());
        }
        return price;
    }

    /**
     * Sets the price of the booking.
     *
     * @param price The price of the booking.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Calculates the price of the booking based on the number of days left for the flight to depart
     * and the capacity of the flight.
     *
     * @param daysLeft The number of days left for the flight to depart.
     * @param numberOfSeats The number of seats available on the flight.
     * @return The price of the booking.
     */
    private int calculatePrice(int daysLeft, int numberOfSeats) {
        // Calculate the base price based on the number of days left
        int basePrice = calculateBasePrice(daysLeft);

        // Calculate the price based on the capacity of the flight
        int price = calculateCapacityPrice(basePrice, numberOfSeats);

        return price;
    }

    /**
     * Calculates the base price of the booking based on the number of days left for the flight to depart.
     *
     * @param daysLeft The number of days left for the flight to depart.
     * @return The base price of the booking.
     */
    private int calculateBasePrice(int daysLeft) {
        // Calculate the base price based on the number of days left
        int basePrice = 0;
        if (daysLeft >= 30) {
            basePrice = 100; // Base price for flights more than 30 days from departure
        } else if (daysLeft >= 15) {
            basePrice = 150; // Base price for flights between 30 and 15 days from departure
        } else if (daysLeft >= 7) {
            basePrice = 200; // Base price for flights between 15 and 7 days from departure
        } else if (daysLeft >= 3) {
            basePrice = 250; // Base price for flights between 7 and 3 days from departure
        } else {
            basePrice = 300; // Base price for flights less than 3 days from departure
        }
        return basePrice;
    }

    /**
     * Calculates the price of the booking based on the capacity of the flight.
     *
     * @param basePrice The base price of the booking.
     * @param numberOfSeats The number of seats available on the flight.
     * @return The price of the booking.
     */
    private int calculateCapacityPrice(int basePrice, int numberOfSeats) {
        // Calculate the price based on the capacity of the flight
        int price = basePrice;
        if (numberOfSeats <= 50) {
            price += 50; // Additional price for flights with 50 or fewer seats
        } else if (numberOfSeats <= 100) {
            price += 100; // Additional price for flights with 51 to 100 seats
        } else {
            price += 150; // Additional price for flights with more than 100 seats
        }
        return price;
    }
}
