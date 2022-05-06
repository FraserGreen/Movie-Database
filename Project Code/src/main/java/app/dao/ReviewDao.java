package app.dao;

import app.model.dto.Review;
import app.model.DBDist;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ReviewDao {

    private final ArrayList<Review> reviews = new ArrayList<>();

    public ReviewDao() {
        getAllFromDatabase();
    }

    public ArrayList<Review> getAllReviews() {
        return reviews;
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

                reviews.add(new Review(Integer.parseInt(values[0]), Integer.parseInt(values[1]), values[2], values[3],
                        values[4], values[5]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Reviews could not be loaded from database");
        }
    }

    public Review getReviewById(int id) {
        return reviews.stream().filter(b -> b.getReviewID() == id).findFirst().orElse(null);
    }

    public boolean deleteAllReviewsByShowID(int showID) {
        if (DBDist.db.execute("DELETE FROM `USER_REVIEW` WHERE SHOW_ID = '" + showID + "';")) {
            reviews.removeIf(review -> review.getReviewID() == showID);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean addReview(@NotNull Review r) {
        if (addToDatabase(new String[]{String.valueOf(r.getReviewID()), String.valueOf(r.getShowID()), r.getEmail(),
                                       r.getRating(), r.getReview(), r.getDate()})) {
            reviews.add(r);
            return true;
        }
        return false;
    }

    private boolean addToDatabase(String[] fields) {
        return DBDist.db.insertInto("user_review", fields);
    }
}
