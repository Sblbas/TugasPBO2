package model;

public class Room {
    private int id;
    private int villa;
    private String name;
    private int quantity;
    private int capacity;
    private int price;
    private BedSize bedSize;
    private boolean hasDesk;
    private boolean hasAc;
    private boolean hasTv;
    private boolean hasWifi;
    private boolean hasShower;
    private boolean hasHotwater;
    private boolean hasFridge;

    public enum BedSize {
        DOUBLE, QUEEN, KING
    }

    // Constructor
    public Room() {
    }

    public Room(int id, int villaId, String name, int quantity, int capacity, int price,
                BedSize bedSize, boolean hasDesk, boolean hasAc, boolean hasTv,
                boolean hasWifi, boolean hasShower, boolean hasHotwater, boolean hasFridge) {
        this.id = id;
        this.villa = villa;
        this.name = name;
        this.quantity = quantity;
        this.capacity = capacity;
        this.price = price;
        this.bedSize = bedSize;
        this.hasDesk = hasDesk;
        this.hasAc = hasAc;
        this.hasTv = hasTv;
        this.hasWifi = hasWifi;
        this.hasShower = hasShower;
        this.hasHotwater = hasHotwater;
        this.hasFridge = hasFridge;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFacilities() {
        StringBuilder sb = new StringBuilder();
        if (hasDesk) sb.append("Desk,");
        if (hasAc) sb.append("AC,");
        if (hasTv) sb.append("TV,");
        if (hasWifi) sb.append("WiFi,");
        if (hasShower) sb.append("Shower,");
        if (hasHotwater) sb.append("Hotwater,");
        if (hasFridge) sb.append("Fridge,");

        // Hapus koma terakhir jika ada
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }


    public int getVilla() {
        return villa;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPrice() {
        return price;
    }

    public BedSize getBedSize() {
        return bedSize;
    }

    public boolean hasDesk() {
        return hasDesk;
    }

    public boolean hasAc() {
        return hasAc;
    }

    public boolean hasTv() {
        return hasTv;
    }

    public boolean hasWifi() {
        return hasWifi;
    }

    public boolean hasShower() {
        return hasShower;
    }

    public boolean hasHotwater() {
        return hasHotwater;
    }

    public boolean hasFridge() {
        return hasFridge;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setVilla(int villa) {
        this.villa = villa;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setBedSize(BedSize bedSize) {
        this.bedSize = bedSize;
    }

    public void setHasDesk(boolean hasDesk) {
        this.hasDesk = hasDesk;
    }

    public void setHasAc(boolean hasAc) {
        this.hasAc = hasAc;
    }

    public void setHasTv(boolean hasTv) {
        this.hasTv = hasTv;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public void setHasShower(boolean hasShower) {
        this.hasShower = hasShower;
    }

    public void setHasHotwater(boolean hasHotwater) {
        this.hasHotwater = hasHotwater;
    }

    public void setHasFridge(boolean hasFridge) {
        this.hasFridge = hasFridge;
    }
}