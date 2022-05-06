package app.controller;

import app.controller.paths.Web;
import app.Main;
import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.model.dto.User;
import io.javalin.http.Handler;

import java.util.Map;
import java.util.Objects;

import static app.controller.utils.RequestUtil.*;

public class UserController {

    public static Handler serveLoginPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.LOGIN, model);
    };

    public static Handler handleLoginPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        //checks login details are correct
        User user = Main.userDao.getUserByEmail(getQueryEmail(ctx));
        if (Main.userDao.authenticateLogin(getQueryEmail(ctx),
                Main.userDao.getUserByEmail(getQueryEmail(ctx)).getSalt(), getQueryPassword(ctx))) {
            //successful login
            ctx.sessionAttribute("currentUser", Main.userDao.getUserByEmail(getQueryEmail(ctx)));
            ctx.sessionAttribute("username", Main.userDao.getUserByEmail(getQueryEmail(ctx)).getFirstName());
            model.put("currentUser", ctx.sessionAttribute("currentUser"));
            model.put("username", ctx.sessionAttribute("username"));
            model.put("authenticationSucceeded", true);
        }
        else { //failed login
            model.put("authenticationFailed", true);
        }
//        ctx.render(Template.LOGIN, model);
        ctx.redirect(Web.INDEX);
    };

    public static Handler handleLogoutPost = ctx -> {
        ctx.sessionAttribute("currentUser", null);
        ctx.redirect(Web.INDEX);
    };

    public static Handler serveSignUpPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.SIGNUP, model);
//        ctx.redirect(Web.LOGIN);
    };

    public static Handler handleSignUpPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (Main.userDao.getUserByEmail(getQueryEmail(ctx)) == null) {
            Main.userDao.add(new User(ctx.formParam("firstName"), ctx.formParam("lastName"),
                    Objects.requireNonNull(ctx.formParam("sex")).charAt(0), ctx.formParam("birthYear"), ctx.formParam("country"),
                    ctx.formParam("postCode"), ctx.formParam("email"), ctx.formParam("password")));
            model.put("authenticationSucceeded", true);
        }
        else {
            model.put("authenticationFailed", true);
        }
        ctx.redirect(Web.LOGIN);
    };
}
