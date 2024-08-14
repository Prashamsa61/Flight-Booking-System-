package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

/**
 * The DataManager interface defines methods for loading and storing data in a FlightBookingSystem.
 */
public interface DataManager {
    
    /**
     * The default separator used in data files.
     */
    public static final String SEPARATOR = ",";
    
    /**
     * Loads data into the FlightBookingSystem from a data file.
     * 
     * @param fbs The FlightBookingSystem to load data into.
     * @throws IOException If an I/O error occurs.
     * @throws FlightBookingSystemException If there is an error in the FlightBookingSystem.
     */
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException;
    
    /**
     * Stores data from the FlightBookingSystem into a data file.
     * 
     * @param fbs The FlightBookingSystem to store data from.
     * @throws IOException If an I/O error occurs.
     * @throws FlightBookingSystemException If there is an error in the FlightBookingSystem.
     */
    public void storeData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException;
    
}
