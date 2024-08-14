package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * Represents a window for adding a new booking to the flight booking system.
 */
public class AddBookingWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private FlightBookingSystem fbs;
    private JTextField customerIdText = new JTextField();
    private JTextField flightIdText = new JTextField();
    private JTextField bookingDateText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an AddBookingWindow object with the specified MainWindow.
     * @param mw The MainWindow object.
     */
    public AddBookingWindow(MainWindow mw) {
        this.mw = mw;
        this.fbs = mw.getFlightBookingSystem();
        initialize();
    }

    /**
     * Initializes the AddBookingWindow by setting up the UI components.
     */
    private void initialize() {
        setTitle("Add Booking");
        setSize(400, 200);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(new JLabel("Customer ID : "), gbc);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(customerIdText, gbc);
        gbc.gridy = 2;
        topPanel.add(new JLabel("Flight ID : "), gbc);
        gbc.gridy = 3;
        topPanel.add(flightIdText, gbc);
        
      
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        getContentPane().add(topPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addBooking();
        } else if (ae.getSource() == cancelBtn) {
            setVisible(false);
        }
    }

    /**
     * Adds a new booking to the flight booking system based on the user input.
     */
    private void addBooking() {
        try {
            String customerIdStr = customerIdText.getText();
            String flightIdStr = flightIdText.getText();

            if (customerIdStr.isEmpty() || flightIdStr.isEmpty()) {
                throw new FlightBookingSystemException("Customer ID and Flight ID must be provided.");
            }

            int customerId = Integer.parseInt(customerIdStr);
            int flightId = Integer.parseInt(flightIdStr);

            if (customerId <= 0 || flightId <= 0) {
                throw new FlightBookingSystemException("Customer ID and Flight ID must be positive integers.");
            }

            Customer customer = fbs.getCustomerByID(customerId);
            Flight flight = fbs.getFlightByID(flightId);

            if (customer == null || flight == null) {
                throw new FlightBookingSystemException("Customer or Flight not found.");
            }
           

            Command addBooking = new AddBooking(customerId, flightId, LocalDate.now());
            addBooking.execute(fbs);

            JOptionPane.showMessageDialog(this, "Booking added successfully.");
            clearFields();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void clearFields() {
        customerIdText.setText("");
        flightIdText.setText("");
        bookingDateText.setText("");
    }
}
