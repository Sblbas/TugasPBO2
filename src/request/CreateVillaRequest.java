package src.request;

public class CreateVillaRequest {
    private String name;
    private String address;
    private double price;
    private int capacity;

    public CreateVillaRequest() {}

    public CreateVillaRequest(String name, String address, double price, int capacity) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.capacity = capacity;
    }

    // Getter dan Setter
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