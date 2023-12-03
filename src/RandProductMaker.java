import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField idField;
    private JTextField costField;
    private JTextField recordCountField;

    private RandomAccessFile randomAccessFile;
    private int recordCount;

    public RandProductMaker() {
        // Initialize GUI components and layout

        nameField = new JTextField(20);
        descriptionField = new JTextField(40);
        idField = new JTextField(10);
        costField = new JTextField(10);
        recordCountField = new JTextField(5);
        recordCountField.setEditable(false);

        JButton addButton = new JButton("Add Record");
        addButton.addActionListener(e -> addRecord());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Cost:"));
        formPanel.add(costField);
        formPanel.add(new JLabel("Record Count:"));
        formPanel.add(recordCountField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Open RandomAccessFile in read-write mode
        try {
            randomAccessFile = new RandomAccessFile("productRandomAccess.dat", "rw");
        } catch (IOException ioException) {
            ioException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error opening Random Access File", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Initialize record count
        recordCount = 0;

        // Set up the GUI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addRecord() {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String id = idField.getText();
            double cost = Double.parseDouble(costField.getText());

            // Validate input
            if (name.isEmpty() || description.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Format and write the record to the RandomAccessFile
            String formattedRecord = new Product(name, description, id, cost).toRandomAccessDataRecord();
            randomAccessFile.writeChars(formattedRecord);

            // Update record count
            recordCount++;
            recordCountField.setText(Integer.toString(recordCount));

            // Clear input fields
            nameField.setText("");
            descriptionField.setText("");
            idField.setText("");
            costField.setText("");

        } catch (IOException | NumberFormatException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding record.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductMaker::new);
    }
}
