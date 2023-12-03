import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int NAME_LENGTH = 35;
    private static final int DESCRIPTION_LENGTH = 75;
    private static final int ID_LENGTH = 6;

    private String name;
    private String description;
    private String ID;
    private double cost;

    public Product(String name, String description, String id, double cost) {
    }

    // Constructors, Getters, Setters, and toCSVDataRecord method (unchanged)

    // Additional Methods (You can add more if needed)

    // Utility method to format the Product record for Random Access file
    public String toRandomAccessDataRecord() {
        return String.format("%-" + NAME_LENGTH + "s%-" + DESCRIPTION_LENGTH + "s%-" + ID_LENGTH + "s%f",
                name, description, ID, cost);
    }
}
