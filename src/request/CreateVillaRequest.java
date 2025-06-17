package src.request;

public class CreateVillaRequest {
    private String name;
    private String address;

    public CreateVillaRequest() {}

    public CreateVillaRequest(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getter dan Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}