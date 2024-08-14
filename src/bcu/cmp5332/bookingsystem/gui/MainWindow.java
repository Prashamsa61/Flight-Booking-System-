package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;


/**
 * The MainWindow class represents the main window of the Flight Booking Management System GUI.
 * It provides a graphical user interface for managing flights, bookings, and customers.
 * The main window contains menus for navigating to different sections of the system,
 * such as viewing flights, adding flights, deleting flights, etc.
 * The class implements the ActionListener interface to handle user actions, such as clicking on menu items.
 */
public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu bookingsMenu;
    private JMenu customersMenu;

    private JMenuItem adminExit;

    private JMenuItem flightsView;
    private JMenuItem flightsAdd;
    private JMenuItem flightsDel;
    
    private JMenuItem bookingsAdd;
    private JMenuItem bookingsUpdate;
    private JMenuItem bookingsCancel;
    private JMenuItem bookingsView;

    private JMenuItem custView;
    private JMenuItem custAdd;
    private JMenuItem custDel;

    private FlightBookingSystem fbs;
    
    /**
     * Constructs a new MainWindow object with the specified FlightBookingSystem.
     * @param fbs The FlightBookingSystem object to be used by the main window.
     * @author Prashamsa Rijal
     * @author Sadikshya Ghimire
    
     */
    
    public MainWindow(FlightBookingSystem fbs) {

        initialize();
        this.fbs = fbs;
        
    
    }
    /**
     * Returns the FlightBookingSystem object associated with the main window.
     * @return The FlightBookingSystem object.
     */
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
    	// Code for initializing the main window
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Flight Booking Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding Flights menu and menu items
        flightsMenu = new JMenu("Flights");
        menuBar.add(flightsMenu);

        flightsView = new JMenuItem("View");
        flightsAdd = new JMenuItem("Add");
        flightsDel = new JMenuItem("Delete");
        flightsMenu.add(flightsView);
        flightsMenu.add(flightsAdd);
        flightsMenu.add(flightsDel);
        // adding action listener for Flights menu items
        for (int i = 0; i < flightsMenu.getItemCount(); i++) {
            flightsMenu.getItem(i).addActionListener(this);
        }
        
        bookingsMenu = new JMenu("Bookings");
        menuBar.add(bookingsMenu);

        
       
        bookingsAdd = new JMenuItem("Add");
        bookingsView =  new JMenuItem("View"); 
        bookingsUpdate = new JMenuItem("Update");
        bookingsCancel = new JMenuItem("Cancel");
        bookingsMenu.add(bookingsAdd);
        bookingsMenu.add(bookingsView); 
        bookingsMenu.add(bookingsUpdate);
        bookingsMenu.add(bookingsCancel);

        // adding action listener for Bookings menu items
        for (int i = 0; i < bookingsMenu.getItemCount(); i++) {
            bookingsMenu.getItem(i).addActionListener(this);
        }

        // adding Customers menu and menu items
        customersMenu = new JMenu("Customers");
        menuBar.add(customersMenu);

        custView = new JMenuItem("View");
        custAdd = new JMenuItem("Add");
        customersMenu.add(custAdd);
        custAdd.addActionListener(this);

        custDel = new JMenuItem("Delete");

        customersMenu.add(custView);
        customersMenu.add(custAdd);
        customersMenu.add(custDel);
        // adding action listener for Customers menu items
        custView.addActionListener(this);
        custAdd.addActionListener(this);
        custDel.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
    public static void main(String[] args) throws IOException, FlightBookingSystemException {
       FlightBookingSystem fbs = FlightBookingSystemData.load();
        new MainWindow(fbs);			
   }
    /**
     * Handles the user actions performed on the main window.
     * @param ae The ActionEvent object representing the user action.
     */


    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            try {
                FlightBookingSystemData.store(fbs);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } else if (ae.getSource() == flightsView) {
            displayFlights();
            
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this);
            
        } else if (ae.getSource() == flightsDel) {
        	int flightId = -1;
        	String input = JOptionPane.showInputDialog(this, "Enter Flight ID:");
        	if (input != null && !input.isEmpty()) {
        	    try {
        	        flightId = Integer.parseInt(input);
        	    } catch (NumberFormatException ex) {
        	        JOptionPane.showMessageDialog(this, "Please enter a valid flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
        	        return;
        	    }
        	} else {
        	    return; // Cancel button was clicked
        	}

        	deleteFlight(flightId);

            
        } else if (ae.getSource() == bookingsAdd) {
         new AddBookingWindow(this);
         
        } else if (ae.getSource() == bookingsCancel) {
        	int customerId = -1;
        	int flightId = -1;
        	String input = JOptionPane.showInputDialog(this, "Enter Customer ID:");
        	if (input != null && !input.isEmpty()) {
        	    try {
        	        customerId = Integer.parseInt(input);
        	    } catch (NumberFormatException ex) {
        	        JOptionPane.showMessageDialog(this, "Please enter a valid customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
        	        return;
        	    }
        	} else {
        	    return; // Cancel button was clicked
        	}

        	String input2 = JOptionPane.showInputDialog(this, "Enter Flight ID:");
        	if (input2 != null && !input2.isEmpty()) {
        	    try {
        	        flightId = Integer.parseInt(input2);
        	    } catch (NumberFormatException ex) {
        	        JOptionPane.showMessageDialog(this, "Please enter a valid flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
        	        return;
        	    }
        	} else {
        	    return; // Cancel button was clicked
        	}

        	cancelBooking(customerId, flightId);

            
        }else if (ae.getSource() == bookingsView) {
            String input = JOptionPane.showInputDialog(this, "Enter Customer ID:");
            if (input != null) {
                try {
                    int customerId = Integer.parseInt(input);
                    String input2 = JOptionPane.showInputDialog(this, "Enter Flight ID:");
                    if (input2 != null) {
                        try {
                            int flightId = Integer.parseInt(input2);
                            viewBookings(customerId, flightId);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Please enter a valid flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
    

            
        } else if (ae.getSource() == custView) {
        	displayCustomers();
            
        } else if (ae.getSource() == custAdd) {
        	new AddCustomerWindow(this);
            
        } else if (ae.getSource() == custDel) {
            String input = JOptionPane.showInputDialog(this, "Enter Customer ID:");
            if (input != null) {
                try {
                    int customerId = Integer.parseInt(input);
                    deleteCustomer(customerId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

            
        
        
    }

    


	public void deleteFlight(int flightId) {
	    try {
	        fbs.deleteFlight(flightId);
	        JOptionPane.showMessageDialog(this, "Flight deleted successfully.");
	    } catch (FlightBookingSystemException ex) {
	        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


    public void cancelBooking(int customerId, int flightId) {
        try {
           fbs.cancelBooking(customerId,flightId);
            JOptionPane.showMessageDialog(this, "Booki;ng cancelled successfully.");
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

	public void displayCustomers() {
	    List<Customer> customersList = fbs.getCustomers();
	    // headers for the table
	    String[] columns = new String[]{"ID", "Name", "Phone", "Email"};

	    Object[][] data = new Object[customersList.size()][4];
	    for (int i = 0; i < customersList.size(); i++) {
	        Customer customer = customersList.get(i);
	        data[i][0] = customer.getId();
	        data[i][1] = customer.getName();
	        data[i][2] = customer.getPhone();
	        data[i][3] = customer.getEmail();
	        
	    }

	    JTable table = new JTable(data, columns);
	    this.getContentPane().removeAll();
	    this.getContentPane().add(new JScrollPane(table));
	    this.revalidate();
	}
	
	public void displayFlights() {
        List<Flight> flightsList = fbs.getFlights();
        // headers for the table
        String[] columns = new String[]{"Flight No", "Origin", "Destination", "Departure Date", "Number of Seats", "Price"};

        Object[][] data = new Object[flightsList.size()][6];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            data[i][0] = flight.getFlightNumber();
            data[i][1] = flight.getOrigin();
            data[i][2] = flight.getDestination();
            data[i][3] = flight.getDepartureDate();
            data[i][4] = flight.getNumberOfSeats();
            data[i][5] = flight.getPrice();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

	/**
	 * Displays the bookings for a specific customer and flight.
	 * @param customerId The ID of the customer.
	 * @param flightId The ID of the flight.
	 */
	public void viewBookings(int customerId, int flightId) {
	    try {
	        fbs.viewBookings(customerId, flightId);
	    } catch (FlightBookingSystemException ex) {
	        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	/**
	 * Deletes a customer from the system.
	 * @param customerId The ID of the customer to be deleted.
	 */
	public void deleteCustomer(int customerId) {
	    try {
	        fbs.deleteCustomer(customerId);
	        JOptionPane.showMessageDialog(this, "Customer deleted successfully.");
	    } catch (FlightBookingSystemException ex) {
	        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	/**
	 * Updates the booking date for a specific booking.
	 * @param bookingId The ID of the booking to be updated.
	 * @param newBookingDate The new booking date.
	 */
	public void updateBooking(int bookingId, LocalDate newBookingDate) {
	    try {
	        fbs.updateBooking(bookingId, newBookingDate);
	        JOptionPane.showMessageDialog(this, "Booking updated successfully.");
	    } catch (FlightBookingSystemException ex) {
	        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}




	}	

