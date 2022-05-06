package app.controller;

import app.Main;
import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.model.dto.Review;
import app.model.dto.User;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

import static app.controller.utils.RequestUtil.*;
public class ReviewController {

    public static Handler serveAddReviewPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("currentUser", ctx.sessionAttribute("currentUser"));
        model.put("show", Main.showDao.getShowById(getParamShowID(ctx)));

        ctx.render(Template.ADD_REVIEW, model);
    };

    public static Handler handleAddReviewPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put((validateAddReviewPost(ctx) ? "authenticationSucceeded" : "authenticationFailed"), true);
        model.put("currentUser", ctx.sessionAttribute("currentUser"));
        model.put("show", Main.showDao.getShowById(getParamShowID(ctx)));
        ctx.render(Template.ADD_REVIEW, model);
    };

    public static boolean validateAddReviewPost(Context ctx) {
        try {

            //gets all reviews
            ArrayList<Review> reviews = Main.reviewDao.getAllReviews();
            //gets all review IDs, puts them in an array
            ArrayList<Integer> allIDs = new ArrayList<>();
            for (Review review : reviews) {
                allIDs.add(review.getReviewID());
            }
            //gets the max review ID. new max = max + 1. if none exist, new max = 1.
            int newMaxReviewID;
            try {
                newMaxReviewID = Collections.max(allIDs) + 1;
            } catch (NoSuchElementException ex) {
                newMaxReviewID = 1;
            }

            User user = ctx.sessionAttribute("currentUser");

            if (user != null) {
                return Main.reviewDao.addReview(new Review(newMaxReviewID, getParamShowID(ctx),
                        user.getEmail(),
                        getQueryRating(ctx), getQueryReview(ctx), LocalDate.now().toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
