package src.model;

public class Villa {
    private int id;
    private String name;
    private String address;
    private double price;
    private int capacity;

    // Constructor
    public Villa() {}

    public Villa(int id, String name, String address, double price, int capacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.capacity = capacity;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
