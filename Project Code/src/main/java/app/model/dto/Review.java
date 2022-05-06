package app.model.dto;

public class Review {

    private final int reviewID;
    private int showID;
    private String email;
    private String rating;
    private String review;
    private String date;

    //<editor-fold desc="Constructor">
    public Review(int reviewID, int showID, String email, String rating, String review, String date) {
        this.reviewID = reviewID;
        this.showID = showID;
        this.email = email;
        this.rating = rating;
        this.review = review;
        this.date = date;
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">
    public int getReviewID() {
        return reviewID;
    }

    public int getShowID() {
        return showID;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    //</editor-fold>
}
