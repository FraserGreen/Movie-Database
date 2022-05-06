package app.dao;

import app.model.Review;
import app.model.backend.DBDist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Dao {

    private ArrayList<> array = new ArrayList<>();

    public Dao() {
        getAllFromDatabase();
    }

    private void getAllFromDatabase() {
        try {
            ResultSet s = DBDist.db.inquire("select * from `user_review`;");
            int numColumns = DBDist.db.getNumColumns("user_review");
            while (s.next()) {

                String[] values = new String[numColumns];

                for (int col = 0; col < numColumns; col++) {

                    values[col] = s.getString(1 + col);
                }

                reviews.add(new Review(false, values));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Reviews could not be loaded from database");
        }
    }

    public Review getReviewById(String id) {
        return reviews.stream().filter(b -> b.reviewID.equals(id)).findFirst().orElse(null);
    }

    public Iterable<Review> getAllReviews() {
        return reviews;
    }

    public void addReview(Review review){
        reviews.add(review);
    }
}
