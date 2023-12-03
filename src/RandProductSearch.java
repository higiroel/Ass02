import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class RandProductSearch extends JFrame {
    private JTextField partialNameField;
    private JTextArea resultArea;

    private RandomAccessFile randomAccessFile;

    public RandProductSearch() {
        // Initialize GUI components and layout

        partialNameField = new JTextField(20);
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);

        JButton searchButton = new JButton("Search Products");
        searchButton.addActionListener(e -> searchProducts());

        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(new JLabel("Partial Name:"));
        formPanel.add(partialNameField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Open RandomAccessFile in read-only mode
        try {
            randomAccessFile = new RandomAccessFile("productRandomAccess.dat", "r");
            // Determine the line separator
            randomAccessFile.readLine();
            randomAccessFile.seek(0); // Move the file pointer to the beginning
        } catch (IOException ioException) {
            ioException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error opening Random Access File", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Set up the GUI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void searchProducts() {
        try {
            String partialName = partialNameField.getText().toLowerCase();

            // Validate input
            if (partialName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a partial name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            resultArea.setText(""); // Clear previous results

            randomAccessFile.seek(0); // Move the file pointer to the beginning

            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                String record = randomAccessFile.readLine();
                System.out.println("Record from file: " + record); // Debugging line
                if (record.toLowerCase().contains(partialName)) {
                    resultArea.append(record + "\n");
                }
            }

        } catch (IOException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching products.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductSearch::new);
    }
}
