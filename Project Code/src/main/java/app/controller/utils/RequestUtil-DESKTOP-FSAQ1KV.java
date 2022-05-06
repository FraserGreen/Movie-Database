package app.controller.utils;

import app.model.dto.User;
import io.javalin.http.Context;

import java.util.Objects;

public class RequestUtil {

    //<editor-fold desc="User-based queries">
    public static String getQueryEmail(Context ctx) {
        return ctx.formParam("email");
    }
    public static String getQueryPassword(Context ctx) {
        return ctx.formParam("password");
    }
    //</editor-fold>

    //<editor-fold desc="Show-based queries">
    public static String getQueryShowID(Context ctx) {
        return ctx.formParam("showID");
    }

    public static String getQueryShowTitle(Context ctx) {
        return ctx.formParam("title");
    }

    public static String getQueryShowGenre(Context ctx) {
        return ctx.formParam("genre");
    }

    public static Double getQueryShowLength(Context ctx) {
        return Double.parseDouble(Objects.requireNonNull(ctx.formParam("length")));
    }

    public static String getQueryShowType(Context ctx) {
        return ctx.formParam("showType");
    }

    public static int getQueryShowProcoID(Context ctx) {
        return Integer.parseInt(Objects.requireNonNull(ctx.formParam("procoId")));
    }

    public static String getQueryShowYear(Context ctx) {
        return ctx.formParam("year");
    }

    public static String getQueryShowImage(Context ctx) {
        return ctx.formParam("image");
    }
    //</editor-fold>

    //<editor-fold desc="Review-based queries">
    public static String getQueryReviewID(Context ctx) {
        return ctx.formParam("reviewID");
    }

    public static String getQueryRating(Context ctx) {
        return ctx.formParam("rating");
    }

    public static String getQueryReview(Context ctx) {
        return ctx.formParam("review");
    }

    public static String getQueryAccountRequestType(Context ctx) {
        return ctx.formParam("accountType");
    }

    public static String getQueryOrgName(Context ctx) {
        return ctx.formParam("orgName");
    }

    public static String getQueryOrgPhone(Context ctx) {
        return ctx.formParam("orgPhone");
    }
    //</editor-fold>

    public static User getSessionCurrentUser(Context ctx) {
        return ctx.sessionAttribute("currentUser");
    }

    public static int getParamShowID(Context ctx) {
        return Integer.parseInt(ctx.pathParam("showID"));
    }

    public static String getParamRequestID(Context ctx) {
        return ctx.pathParam("requestID");
    }
}
