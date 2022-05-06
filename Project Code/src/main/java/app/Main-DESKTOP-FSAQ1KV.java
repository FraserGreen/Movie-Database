package app;

import app.controller.*;
import app.dao.*;
import app.model.DBDist;
import app.model.DBUtils;
import app.controller.utils.HerokuUtil;
import app.controller.paths.Web;
import app.controller.utils.ViewUtil;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Main {

    // Declare dependencies
    public static ShowDao showDao;
    public static UserDao userDao;
    public static ReviewDao reviewDao;
    public static AccountRequestDao accountRequestDao;
    public static ProductionCompanyDao productionCompanyDao;

    public static void main(String[] args) {
        setupBackend(args);
        setupFrontend();
    }

    public static void setupBackend(String[] args) {
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
        accountRequestDao = new AccountRequestDao();
        productionCompanyDao = new ProductionCompanyDao();

        //starts the website
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(HerokuUtil.getHerokuAssignedPort());

        //here are all the things that can be done on the website
        app.routes(() -> {
            get(Web.INDEX, IndexController.serveIndexPage);
            get(Web.LOGIN, UserController.serveLoginPage);
            get(Web.SIGNUP, UserController.serveSignUpPage);
            get(Web.ADD_SHOW, ShowController.serveAddShowPage);
            get(Web.ADD_REVIEW, ReviewController.serveAddReviewPage);
            get(Web.VIEW_ALL_SHOWS, ShowController.serveViewAllShowsPage);
            get(Web.VIEW_SHOW, ShowController.serveViewShowPage);
            get(Web.EDIT_SHOW, ShowController.serveEditShowPage);
            get(Web.EDIT_SHOW_STATUS, ShowController.serveEditShowStatusPage);
            get(Web.CREATE_ACCOUNT_REQUEST, AccountRequestController.serveCreateAccountRequestPage);
            get(Web.VIEW_ALL_ACCOUNT_REQUESTS, AccountRequestController.serveViewALlAccountRequestsPage);
            get(Web.VIEW_ACCOUNT_REQUEST, AccountRequestController.serveViewAccountRequestPage);
            post(Web.DELETE_SHOW, ShowController.handleDeleteShowPost);
            post(Web.EDIT_SHOW, ShowController.handleEditShowPost);
            post(Web.EDIT_SHOW_STATUS, ShowController.handleEditShowStatusPost);
            post(Web.LOGIN, UserController.handleLoginPost);
            post(Web.LOGOUT, UserController.handleLogoutPost);
            post(Web.SIGNUP, UserController.handleSignUpPost);
            post(Web.ADD_SHOW, ShowController.handleAddShowPost);
            post(Web.ADD_REVIEW, ReviewController.handleAddReviewPost);
            post(Web.CREATE_ACCOUNT_REQUEST, AccountRequestController.handleCreateAccountRequestPost);
            post(Web.ACCEPT_ACCOUNT_REQUEST, AccountRequestController.handleAcceptAccountRequestPost);
            post(Web.REJECT_ACCOUNT_REQUEST, AccountRequestController.handleRejectAccountRequestPost);
        });

        app.error(404, ViewUtil.notFound);
    }
}
