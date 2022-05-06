package app;

import app.model.dao.ReviewDao;
import app.backend.DBDist;
import app.backend.DBUtils;
import app.controller.ShowController;
import app.controller.IndexController;
import app.controller.UserController;
import app.model.dao.ShowDao;
import app.controller.utils.HerokuUtil;
import app.controller.paths.Web;
import app.controller.utils.ViewUtil;
import app.model.dao.UserDao;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

/*
Credit to Rmit's Dr. Melina Vidoni (melina.vidoni@rmit.edu.au)
for providing the base structure for this code, as well as the bulk of the code for many components, such as the css.
 */

public class Main {

    // Declare dependencies
    public static ShowDao showDao;
    public static UserDao userDao;
    public static ReviewDao reviewDao;

    public static void main(String[] args) {
        setupBackend(args);
        setupFrontend();
    }

    private static void setupBackend(String[] args) {
        //connect to MySQL database
        if (args.length == 2)
            DBDist.db = new DBUtils(args);
        else {
            System.err.println("Unable to connect to MySQL database.");
        }
    }

    private static void setupFrontend() {
        // Instantiate your dependencies
        showDao = new ShowDao();
        userDao = new UserDao();
        reviewDao = new ReviewDao();

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(HerokuUtil.getHerokuAssignedPort());

        app.routes(() -> {
            get(Web.INDEX, IndexController.serveIndexPage);
            get(Web.LOGIN, UserController.serveLoginPage);
            get(Web.SIGNUP, UserController.serveSignUpPage);
            get(Web.ADD_SHOW, ShowController.serveAddShowPage);
            get(Web.ADD_REVIEW, ShowController.serveAddReviewPage);
            get(Web.VIEW_ALL_SHOWS, ShowController.serveViewAllShowsPage);
            post(Web.LOGIN, UserController.handleLoginPost);
            post(Web.LOGOUT, UserController.handleLogoutPost);
            post(Web.SIGNUP, UserController.handleSignUpPost);
            post(Web.ADD_SHOW, ShowController.handleAddShowPost);
            post(Web.ADD_REVIEW, ShowController.handleAddReviewPost);

            //implementing this in m2
//            post(Web.VIEW_ALL_SHOWS, ShowController.handleAddRatingPost);
        });

        app.error(404, ViewUtil.notFound);
    }
}
