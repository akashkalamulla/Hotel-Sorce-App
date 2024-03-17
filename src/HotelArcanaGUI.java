import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class HotelArcanaGUI extends JFrame implements ActionListener {
    private JLabel nameLabel, roomTypeLabel, daysLabel, servicesLabel;
    private JTextField nameField, daysField;
    private JComboBox<String> roomTypeComboBox;
    private JCheckBox[] servicesCheckBoxes;
    private JButton bookButton;
    private JTextArea outputArea;

    public HotelArcanaGUI() {
        setTitle("Hotel Arcana Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new GridLayout(6, 2));

        nameLabel = new JLabel("Name:");
        add(nameLabel);
        nameField = new JTextField();
        add(nameField);

        roomTypeLabel = new JLabel("Room Type:");
        add(roomTypeLabel);
        roomTypeComboBox = new JComboBox<>(new String[]{"Luxury Room", "Standard Room"});
        add(roomTypeComboBox);

        daysLabel = new JLabel("Number of Days:");
        add(daysLabel);
        daysField = new JTextField();
        add(daysField);

        servicesLabel = new JLabel("Select Services:");
        add(servicesLabel);
        JPanel servicesPanel = new JPanel();
        servicesPanel.setLayout(new GridLayout(4, 1));
        servicesCheckBoxes = new JCheckBox[4];
        servicesCheckBoxes[0] = new JCheckBox("Restaurant");
        servicesCheckBoxes[1] = new JCheckBox("Bar");
        servicesCheckBoxes[2] = new JCheckBox("Pool");
        servicesCheckBoxes[3] = new JCheckBox("Gym");
        for (JCheckBox checkBox : servicesCheckBoxes) {
            servicesPanel.add(checkBox);
        }
        add(servicesPanel);

        bookButton = new JButton("Book");
        bookButton.addActionListener(this);
        add(bookButton);

        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            String name = nameField.getText();
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            int numOfDays = Integer.parseInt(daysField.getText());
            StringBuilder selectedServices = new StringBuilder();
            float totalAmount = 0;

            // Calculate total amount and display selected services
            for (JCheckBox checkBox : servicesCheckBoxes) {
                if (checkBox.isSelected()) {
                    String service = checkBox.getText();
                    selectedServices.append(service).append(", ");
                    // Calculate cost for each selected service (for demonstration, assume fixed prices)
                    totalAmount += getServiceCost(service) * numOfDays;
                }
            }
            // Remove the last comma and space
            if (selectedServices.length() > 0) {
                selectedServices.delete(selectedServices.length() - 2, selectedServices.length());
            }

            // Calculate room cost based on room type
            float roomCost = getRoomCost(roomType) * numOfDays;
            totalAmount += roomCost;

            // Display booking details and total amount
            outputArea.setText("Name: " + name + "\n" +
                               "Room Type: " + roomType + "\n" +
                               "Number of Days: " + numOfDays + "\n" +
                               "Selected Services: " + selectedServices.toString() + "\n" +
                               "Room Cost: $" + roomCost + "\n" +
                               "Total Amount: $" + totalAmount);

            // Write booking details to a text file
            writeBookingToFile(name, roomType, numOfDays, selectedServices.toString(), roomCost, totalAmount);
        }
    }

    private float getRoomCost(String roomType) {
        // Provide fixed room prices for demonstration
        if (roomType.equals("Luxury Room")) {
            return 10000.0f;
        } else if (roomType.equals("Standard Room")) {
            return 5000.0f;
        }
        return 0.0f;
    }

    private float getServiceCost(String serviceName) {
        // Provide fixed service prices for demonstration
        switch (serviceName) {
            case "Restaurant":
                return 4500.0f;
            case "Bar":
                return 5000.0f;
            case "Pool":
                return 2500.0f;
            case "Gym":
                return 1500.0f;
            default:
                return 0.0f;
        }
    }

    private void writeBookingToFile(String name, String roomType, int numOfDays, String selectedServices, float roomCost, float totalAmount) {
        try (FileWriter writer = new FileWriter("BookingDetails.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write("Name: " + name + "\n");
            bw.write("Room Type: " + roomType + "\n");
            bw.write("Number of Days: " + numOfDays + "\n");
            bw.write("Selected Services: " + selectedServices + "\n");
            bw.write("Room Cost: $" + roomCost + "\n");
            bw.write("Total Amount: $" + totalAmount + "\n\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelArcanaGUI::new);
    }
}

