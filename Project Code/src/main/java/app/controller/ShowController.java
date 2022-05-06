package app.controller;

import app.Main;
import app.backend.DBDist;
import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.model.Review;
import app.model.Show;
import io.javalin.http.Handler;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Map;

import static app.controller.utils.RequestUtil.*;

public class ShowController {

    public static Handler serveAddShowPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        //the add show page is currently admin-only.
        //if the current user is not an admin, they wont be able to access the page.
        String isAdmin = (ctx.sessionAttribute("isAdmin"));
        if (isAdmin != null && isAdmin.equals("true")) {
            ctx.render(Template.ADD_SHOW, model);
        }
        else {
            ctx.render(Template.NOT_FOUND, model);
        }
    };

    public static Handler handleAddShowPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        try {

            //the following chunk is just a bunch of data validation to reduce likelihood of throwing a SQLException

            ResultSet showid = DBDist.db
                    .inquire("select * from `show` where showid = '" + getQueryShowID(ctx) + "'");
            ResultSet procoid = DBDist.db
                    .inquire("select * from `production_company` where proco_id = '" + getQueryShowProcoID(ctx) + "'");
            if (!showid.next() && (Double.parseDouble(getQueryShowLength(ctx)) >= 0) && (getQueryShowIsMovie(ctx)
                    .equals("0") || getQueryShowIsMovie(ctx).equals("1")) && (getQueryShowIsSeries(ctx)
                    .equals("0") || getQueryShowIsSeries(ctx).equals("1")) && (procoid.next())) {
                Main.showDao.addShow(new Show(true, new String[]{getQueryShowID(ctx), getQueryShowTitle(ctx),
                                                                 getQueryShowGenre(ctx),
                                                                 getQueryShowLength(ctx),
                                                                 getQueryShowIsMovie(ctx), getQueryShowIsSeries(
                        ctx), getQueryShowProcoID(ctx),
                                                                 getQueryShowYear(ctx),
                                                                 getQueryShowImage(ctx)}));
                model.put("authenticationSucceeded", true);
            }
            else {
                model.put("authenticationFailed", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.put("authenticationFailed", true);
        }
        ctx.render(Template.ADD_SHOW, model);
    };

    public static Handler serveAddReviewPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.ADD_REVIEW, model);
    };
    public static Handler handleAddReviewPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        try {
            //the following chunk is just a bunch of data validation to reduce likelihood of throwing a SQLException

            ResultSet reviewid = DBDist.db
                    .inquire("select * from `user_review` where reviewId = '" + getQueryReviewID(ctx) + "'");
            ResultSet showid = DBDist.db
                    .inquire("select * from `show` where showid = '" + getQueryShowID(ctx) + "'");
            ResultSet username = DBDist.db
                    .inquire("select * from `account` where username = '" + getQueryUsername(ctx) + "'");
            int rating = Integer.parseInt(getQueryRating(ctx));

            if (!reviewid.next() && showid.next() && username.next() && rating > 0 && rating <= 5) {
                Main.reviewDao.addReview(new Review(true,
                        new String[]{getQueryReviewID(ctx), getQueryShowID(ctx), getQueryUsername(ctx),
                                     getQueryRating(ctx), getQueryReview(ctx), LocalDate.now().toString()}));
                model.put("authenticationSucceeded", true);
            }
            else {
                model.put("authenticationFailed", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.put("authenticationFailed", true);
        }

        ctx.render(Template.ADD_REVIEW, model);
    };

    public static Handler serveViewAllShowsPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("shows", Main.showDao.getAllShows());
        model.put("reviews", Main.reviewDao.getAllReviews());
        ctx.render(Template.VIEW_ALL_SHOWS, model);
    };

    //to be implemented in m2
//    public static Handler handleAddRatingPost = ctx -> {
//        Map<String, Object> model = ViewUtil.baseModel(ctx);
//        System.out.println(getQueryRating(ctx));
//
//        //        if (ctx.sessionAttribute("currentUser"))
//        //if user has already rated the show, update
//
//        //if not, insert
//        ctx.render(Template.VIEW_ALL_SHOWS, model);
//    };
};

