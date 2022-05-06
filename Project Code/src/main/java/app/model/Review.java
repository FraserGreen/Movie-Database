package app.model;

import app.backend.DBDist;
public class Review {

    public final String reviewID;
    public final String showID;
    public final String username;
    public final String rating;
    public final String review;
    public final String date;

    public Review(boolean addToDatabase, String[] values) {
        if (values.length != 6) {
            throw new IndexOutOfBoundsException();
        }
        reviewID = values[0];
        showID = values[1];
        username = values[2];
        rating = values[3];
        review = values[4];
        date = values[5];

        if (addToDatabase) {
            addToDatabase();
        }
    }

    private void addToDatabase() {
        DBDist.db.insertInto("user_review", new String[]{reviewID, showID, username, rating, review, date});
    }

    public String getReview() {
        return review;
    }

    public String getRating() {
        return rating;
    }

    public String getUsername() {
        return username;
    }

    public String getShowID() {
        return showID;
    }
}
