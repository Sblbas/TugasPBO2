package request;

public class CreateRoomRequest {
    private String name;
    private String type;

    public CreateRoomRequest() {}

    public CreateRoomRequest(String name, String type) {
        this.name = name;
        this.type = type;
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
}