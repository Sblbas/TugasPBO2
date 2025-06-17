package src.request;

public class CreateRoomRequest {
    private String name;
    private String type;
    private int capacity;
    private double price;

    public CreateRoomRequest() {}

    public CreateRoomRequest(String name, String type, int capacity, double price) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
    }

    // Getter dan Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}