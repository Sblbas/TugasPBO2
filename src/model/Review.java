package model;

public class Review {
    private int id;
    private int villaId;
    private String reviewerName;
    private String comment;
    private int rating; // 1-5

    public Review() {}

    public Review(int id, int villaId, String reviewerName, String comment, int rating) {
        this.id = id;
        this.villaId = villaId;
        this.reviewerName = reviewerName;
        this.comment = comment;
        this.rating = rating;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVillaId() {
        return villaId;
    }

    public void setVillaId(int villaId) {
        this.villaId = villaId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}