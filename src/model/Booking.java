package model;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int customerId;
    private int roomTypeId;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
    private int price;
    private Integer voucherId; // Bisa null jika tidak pakai voucher
    private int finalPrice;
    private String paymentStatus; // enum: waiting, failed, success
    private boolean hasCheckedIn;
    private boolean hasCheckedOut;

    public Booking() {}

    public Booking(int id, int customerId, int roomTypeId, LocalDateTime checkinDate, LocalDateTime checkoutDate,
            int price, Integer voucherId, int finalPrice, String paymentStatus,
            boolean hasCheckedIn, boolean hasCheckedOut) {
        this.id = id;
        this.customerId = customerId;
        this.roomTypeId = roomTypeId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.price = price;
        this.voucherId = voucherId;
        this.finalPrice = finalPrice;
        this.paymentStatus = paymentStatus;
        this.hasCheckedIn = hasCheckedIn;
        this.hasCheckedOut = hasCheckedOut;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public LocalDateTime getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDateTime checkinDate) {
        this.checkinDate = checkinDate;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDateTime checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean isHasCheckedIn() {
        return hasCheckedIn;
    }

    public void setHasCheckedIn(boolean hasCheckedIn) {
        this.hasCheckedIn = hasCheckedIn;
    }

    public boolean isHasCheckedOut() {
        return hasCheckedOut;
    }

    public void setHasCheckedOut(boolean hasCheckedOut) {
        this.hasCheckedOut = hasCheckedOut;
    }
}
