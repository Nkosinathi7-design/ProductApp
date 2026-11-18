
package productapp;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ProductApp extends JFrame {

    private final JTextField txtCustomerName;
    private final JTextField txtPrice;
    private final JComboBox<String> cmbProduct;

    public ProductApp() {

        setTitle("Product App");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu toolsMenu = new JMenu("Tools");

        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");

        fileMenu.add(exitItem);
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);

        setJMenuBar(menuBar);

        // Create form panel
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        JLabel lblCustomer = new JLabel("Customer Name:");
        txtCustomerName = new JTextField();

        JLabel lblProduct = new JLabel("Select Product:");

        String[] products = {
            "Cozy Nights Monarch",
            "Restonic Barbados",
            "Sealy Phuket"
        };

        cmbProduct = new JComboBox<>(products);

        JLabel lblPrice = new JLabel("Enter Price:");
        txtPrice = new JTextField();

        JButton btnProcess = new JButton("PROCESS");

        panel.add(lblCustomer);
        panel.add(txtCustomerName);

        panel.add(lblProduct);
        panel.add(cmbProduct);

        panel.add(lblPrice);
        panel.add(txtPrice);

        panel.add(new JLabel(""));
        panel.add(btnProcess);

        add(panel);

        // Process button
        btnProcess.addActionListener(e -> processSale());

        // Process menu item
        processItem.addActionListener(e -> processSale());

        // Clear menu item
        clearItem.addActionListener(e -> clearFields());

        // Exit menu item
        exitItem.addActionListener(e -> System.exit(0));
    }

    // Process customer sale
    private void processSale() {

        String customerName = txtCustomerName.getText().trim();

        String productName =
            (String) cmbProduct.getSelectedItem();

        String priceText = txtPrice.getText().trim();

        if (customerName.isEmpty() || priceText.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please enter the customer name and product price."
            );

            return;
        }

        try {

            double price = Double.parseDouble(priceText);

            if (price <= 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please enter a price greater than zero."
                );
                return;
            }

            // Create calculations object
            ProductCalculations calculations =
                new ProductCalculations(price);

            double vat = calculations.GetVat();

            double commission =
                calculations.GetCommission();

            double total = calculations.GetTotal();

            // Prepare report
            String result =
                "Customer Name: " + customerName
                + "\nProduct: " + productName
                + "\nVAT: R " + vat
                + "\nCOMMISSION: R " + commission
                + "\nTOTAL: R " + total;

            // Save information to data.txt
            saveToFile(
                customerName,
                productName,
                vat,
                commission,
                total
            );

            // Display results
            JOptionPane.showMessageDialog(
                this,
                result,
                "Sale Information",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid numeric price."
            );

        }
    }

    // Save sales information
    private void saveToFile(
        String customer,
        String product,
        double vat,
        double commission,
        double total
    ) {

        try (BufferedWriter writer =
            new BufferedWriter(new FileWriter("data.txt", true))) {

            writer.write("DATA LOG");
            writer.newLine();

            writer.write("********************************");
            writer.newLine();

            writer.write("Customer Name: " + customer);
            writer.newLine();

            writer.write("Product: " + product);
            writer.newLine();

            writer.write("VAT: R " + vat);
            writer.newLine();

            writer.write("COMMISSION: R " + commission);
            writer.newLine();

            writer.write("TOTAL: R " + total);
            writer.newLine();

            writer.write("********************************");
            writer.newLine();
            writer.newLine();

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Error saving data: " + ex.getMessage()
            );
        }
    }

    // Clear all fields
    private void clearFields() {

        txtCustomerName.setText("");
        txtPrice.setText("");
        cmbProduct.setSelectedIndex(0);
    }

    // Start application
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ProductApp().setVisible(true);
        });
    }
}
