package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
/**
 * Represents a window for adding a new flight to the flight booking system.
 */
public class AddFlightWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField flightNoText = new JTextField();
    private JTextField originText = new JTextField();
    private JTextField destinationText = new JTextField();
    private JTextField depDateText = new JTextField();
    private JTextField seatsText = new JTextField();
    private JTextField priceText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");
    /**
     * Constructs an AddFlightWindow object with the specified MainWindow.
     * @param mw The MainWindow object.
     */
    public AddFlightWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }
    
    /**
    * Initializes the AddFlightWindow by setting up the UI components.
    */

    private void initialize() {
    	// Code for setting up the UI components
        setTitle("Add a New Flight");
        setSize(350, 300);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(new JLabel("Flight No : "), gbc);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(flightNoText, gbc);
        gbc.gridy = 2;
        topPanel.add(new JLabel("Origin : "), gbc);
        gbc.gridy = 3;
        topPanel.add(originText, gbc);
        gbc.gridy = 4;
        topPanel.add(new JLabel("Destination : "), gbc);
        gbc.gridy = 5;
        topPanel.add(destinationText, gbc);
        gbc.gridy = 6;
        topPanel.add(new JLabel("Departure Date (YYYY-MM-DD) : "), gbc);
        gbc.gridy = 7;
        topPanel.add(depDateText, gbc);
        gbc.gridy = 8;
        topPanel.add(new JLabel("Number of Seats : "), gbc);
        gbc.gridy = 9;
        topPanel.add(seatsText, gbc);
        gbc.gridy = 10;
        topPanel.add(new JLabel("Price : "), gbc);
        gbc.gridy = 11;
        topPanel.add(priceText, gbc);

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
    /**
     * Handles the action events for the Add and Cancel buttons.
     * @param ae The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addFlight();
        } else if (ae.getSource() == cancelBtn) {
            setVisible(false);
        }
    }
    /**
     * Adds a new flight to the flight booking system based on the user input.
     */
    private void addFlight() {
    	// Code for adding a new flight
        try {
            String flightNumber = flightNoText.getText();
            String origin = originText.getText();
            String destination = destinationText.getText();
            LocalDate departureDate = LocalDate.parse(depDateText.getText());
            int numberOfSeats = Integer.parseInt(seatsText.getText());
            int price = Integer.parseInt(priceText.getText());

            if (flightNumber.isEmpty() || origin.isEmpty() || destination.isEmpty()) {
                throw new FlightBookingSystemException("Flight No, Origin, and Destination cannot be empty.");
            }

            if (numberOfSeats <= 0 || price <= 0) {
                throw new FlightBookingSystemException("Number of Seats and Price must be positive integers.");
            }

            if (departureDate.isBefore(LocalDate.now())) {
                throw new FlightBookingSystemException("Departure Date must be in the future.");
            }

            Command addFlight = new AddFlight(flightNumber, origin, destination, departureDate, numberOfSeats, price);
            addFlight.execute(mw.getFlightBookingSystem());

            JOptionPane.showMessageDialog(this, "Flight added successfully.");
            clearFields();
        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Clears the text fields in the AddFlightWindow.
     */
    private void clearFields() {
    	// Code for clearing text fields
        flightNoText.setText("");
        originText.setText("");
        destinationText.setText("");
        depDateText.setText("");
        seatsText.setText("");
        priceText.setText("");
    }
}
