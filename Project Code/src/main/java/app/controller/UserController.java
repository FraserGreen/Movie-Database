package app.controller;

import app.backend.DBDist;
import app.Main;
import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.model.User;
import io.javalin.http.Handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static app.controller.utils.RequestUtil.getQueryPassword;
import static app.controller.utils.RequestUtil.getQueryUsername;

public class UserController {

    public static Handler serveLoginPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.LOGIN, model);
    };

    public static Handler handleLoginPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (getQueryUsername(ctx) != null &&
                getQueryPassword(ctx) != null &&
                Main.userDao.getUserByUsername(getQueryUsername(ctx)) != null &&
                DBDist.db.logIn(getQueryUsername(ctx),
                Main.userDao.getUserByUsername(getQueryUsername(ctx)).salt,
                getQueryPassword(ctx))) {
            //successful login
            ctx.sessionAttribute("currentUser", getQueryUsername(ctx));
            model.put("authenticationSucceeded", true);
            model.put("currentUser", getQueryUsername(ctx));

            try {
                ResultSet result = DBDist.db
                        .inquire("select type from `account` where username = '" + getQueryUsername(ctx) + "'");
                result.next();
                if (result.getString(1).equals("Admin")) {
//                    System.out.println("logged into admin account");
                    ctx.sessionAttribute("isAdmin", "true");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else { //failed login
            model.put("authenticationFailed", true);
        }
        //do something
        ctx.render(Template.LOGIN, model);
    };

    public static Handler handleLogoutPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.sessionAttribute("currentUser", null);
        ctx.sessionAttribute("loggedOut", "true");
        ctx.sessionAttribute("isAdmin", "false");
        ctx.render(Template.INDEX, model);
    };

    public static Handler serveSignUpPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.SIGNUP, model);
    };

    public static Handler handleSignUpPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (Main.userDao.getUserByUsername(getQueryUsername(ctx)) == null) {
            Main.userDao.add(new User(true, new String[]{getQueryUsername(ctx), getQueryPassword(ctx)}));
            model.put("authenticationSucceeded", true);
        }
        else {
            model.put("authenticationFailed", true);
        }
        ctx.render(Template.SIGNUP, model);
    };
}
