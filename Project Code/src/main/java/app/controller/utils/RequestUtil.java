package app.controller.utils;

import io.javalin.http.Context;

public class RequestUtil {

    public static String getQueryUsername(Context ctx) {
        return ctx.formParam("username");
    }

    public static String getQueryShowID(Context ctx) {
        return ctx.formParam("showID");
    }

    public static String getQueryShowTitle(Context ctx) {
        return ctx.formParam("title");
    }

    public static String getQueryShowGenre(Context ctx) {
        return ctx.formParam("genre");
    }

    public static String getQueryShowLength(Context ctx) {
        return ctx.formParam("length");
    }

    public static String getQueryShowIsMovie(Context ctx) {
        return ctx.formParam("isMovie");
    }

    public static String getQueryShowIsSeries(Context ctx) {
        return ctx.formParam("isSeries");
    }

    public static String getQueryShowProcoID(Context ctx) {
        return ctx.formParam("procoId");
    }

    public static String getQueryShowYear(Context ctx) {
        return ctx.formParam("year");
    }

    public static String getQueryShowImage(Context ctx) {
        return ctx.formParam("image");
    }

    public static String getQueryPassword(Context ctx) {
        return ctx.formParam("password");
    }

    public static String getQueryReviewID(Context ctx) {
        return ctx.formParam("reviewID");
    }

    public static String getQueryRating(Context ctx) {
        return ctx.formParam("rating");
    }

    public static String getQueryReview(Context ctx) {
        return ctx.formParam("review");
    }

//    public static String getQueryDate(Context ctx) {
//        return ctx.formParam("date");
//    }

    public static String getSessionCurrentUser(Context ctx) {
        return (String) ctx.sessionAttribute("currentUser");
    }

    public static boolean removeSessionAttrLoggedOut(Context ctx) {
        String loggedOut = ctx.sessionAttribute("loggedOut");
        ctx.sessionAttribute("loggedOut", null);
        return loggedOut != null;
    }

    public static String removeSessionAttrLoginRedirect(Context ctx) {
        String loginRedirect = ctx.sessionAttribute("loginRedirect");
        ctx.sessionAttribute("loginRedirect", null);
        return loginRedirect;
    }
}
