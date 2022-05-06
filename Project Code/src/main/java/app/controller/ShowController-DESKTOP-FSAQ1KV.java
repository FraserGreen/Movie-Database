package app.controller;

import app.Main;
import app.controller.paths.Web;
import app.model.dto.ProductionCompany;
import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.model.dto.Show;
import app.model.dto.User;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

import static app.controller.utils.RequestUtil.*;

public class ShowController {

    public static Handler serveAddShowPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.ADD_SHOW, model);
    };

    public static Handler handleAddShowPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put((validateAddShowPost(ctx) ? "authenticationSucceeded" : "authenticationFailed"), true);
        ctx.render(Template.ADD_SHOW, model);
    };

    public static boolean validateAddShowPost(Context ctx) {
        try {

            //gets all shows
            ArrayList<Show> shows = Main.showDao.getAllShows();
            //gets all show IDs, puts them in an array
            ArrayList<Integer> allIDs = new ArrayList<>();
            for (Show show : shows) {
                allIDs.add(show.getId());
            }
            //gets the max show ID. new max = max + 1. if none exist, new max = 1.
            int newMaxShowID;
            try {
                newMaxShowID = Collections.max(allIDs) + 1;
            } catch (NoSuchElementException ex) {
                newMaxShowID = 1;
            }
            ProductionCompany proco = Main.productionCompanyDao.getProcoByID(getQueryShowProcoID(ctx));

            User user = ctx.sessionAttribute("currentUser");
            if (user == null || proco == null) {
                return false;
            }
            //determines status of show based on account type
            String status;
            if (user.getType().equals("Admin")) {
                status = "Visible";
            }
            else if (user.getType().equals("Regular")) {
                status = "Submitted";
            }
            else {
                status = "Waiting";
            }

            boolean isMovie = isMovie(getQueryShowType(ctx));
            boolean isSeries = isSeries(getQueryShowType(ctx));

            //ensures length, and ensures the procoID listed exists.
            if (getQueryShowLength(ctx) >= 0) {
                //adds new show to model and database
                Main.showDao.add(new Show(newMaxShowID, getQueryShowTitle(ctx),
                        getQueryShowGenre(ctx),
                        getQueryShowLength(ctx),
                        isMovie, isSeries, getQueryShowProcoID(ctx),
                        getQueryShowYear(ctx),
                        getQueryShowImage(ctx), status));
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Handler serveViewAllShowsPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("shows", Main.showDao.getAllShows());
        ctx.render(Template.VIEW_ALL_SHOWS, model);
    };

    public static Handler serveViewShowPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Show show = Main.showDao.getShowById(getParamShowID(ctx));
        User user = ctx.sessionAttribute("currentUser");

        //shows visible shows to everyone. Only displays shows with other statuses to admins
        if (show.getStatus().equals("Visible") || (user != null && user.getType().equals("Admin"))) {
            model.put("show", Main.showDao.getShowById(getParamShowID(ctx)));
            model.put("reviews", Main.reviewDao.getAllReviews());
            ctx.render(Template.VIEW_SHOW, model);
        }
        else {
            ctx.render(Template.NOT_FOUND, model);
        }
    };

    public static Handler handleDeleteShowPost = ctx -> {
        Main.showDao.deleteShow(getParamShowID(ctx));
        ctx.redirect(Web.VIEW_ALL_SHOWS);
    };

    public static Handler serveEditShowPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        //the edit show page is currently admin-only.
        //if the current user is not an admin, they wont be able to access the page.
        User user = ctx.sessionAttribute("currentUser");
        if (user != null && user.getType().equals("Admin")) {
            model.put("show", Main.showDao.getShowById(getParamShowID(ctx)));
            ctx.render(Template.EDIT_SHOW, model);
        }
        else {
            ctx.render(Template.NOT_FOUND, model);
        }
    };

    public static Handler handleEditShowPost = ctx -> {
        validateEditShowPost(ctx);
        ctx.redirect("/viewShow/" + getParamShowID(ctx));
    };

    public static boolean validateEditShowPost(Context ctx) {
        try {

            //the following chunk is just a bunch of data validation to reduce likelihood of throwing a SQLException

            Show show = Main.showDao.getShowById(getParamShowID(ctx));
            ProductionCompany proco = Main.productionCompanyDao.getProcoByID(getQueryShowProcoID(ctx));

            //checks if showID and procoID exist, and if length is a valid number
            if (show != null && proco != null && getQueryShowLength(ctx) >= 0) {

                boolean isMovie = isMovie(getQueryShowType(ctx));
                boolean isSeries = isSeries(getQueryShowType(ctx));

                //updates model and backend with the new show information
                Main.showDao.editShow(getParamShowID(ctx), getQueryShowTitle(ctx),
                        getQueryShowGenre(ctx), getQueryShowLength(ctx), isMovie,
                        isSeries, getQueryShowProcoID(ctx), getQueryShowYear(ctx),
                        getQueryShowImage(ctx), Main.showDao.getShowById(getParamShowID(ctx)).getStatus());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isMovie(String queryShowType) {
        return queryShowType.equals("Movie");
    }

    private static boolean isSeries(String queryShowType) {
        return queryShowType.equals("Series");
    }

    public static Handler serveEditShowStatusPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        //if current user is admin, serves the correct page
        User user = ctx.sessionAttribute("currentUser");
        if (user != null && user.getType().equals("Admin")) {
            model.put("show", Main.showDao.getShowById(getParamShowID(ctx)));
            ctx.render(Template.EDIT_SHOW_STATUS, model);
        }
        else {
            ctx.render(Template.NOT_FOUND, model);
        }
    };

    public static Handler handleEditShowStatusPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Show show = Main.showDao.getShowById(getParamShowID(ctx));

        //changes the show's status in the model and the database
        Main.showDao.editShow(show.getId(), show.getTitle(), show.getGenre(), show.getLength(), show.getIsMovie(),
                show.getIsSeries(), show.getProcoID(), show.getYear(), show.getImageFilename(), ctx.formParam("status"));

        model.put("shows", Main.showDao.getAllShows());
        ctx.render(Template.VIEW_ALL_SHOWS, model);
    };
}

