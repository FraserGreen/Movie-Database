package frontend;

import backend.DBDist;
import backend.DBUtils;
import frontend.controller.BookController;
import frontend.dao.BookDao;
import frontend.controller.IndexController;
import frontend.controller.LoginController;
import frontend.dao.UserDao;
import frontend.controller.localisation.Filters;
import frontend.controller.utils.HerokuUtil;
import frontend.controller.paths.Web;
import frontend.controller.utils.ViewUtil;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Main {

    // Declare dependencies
    public static BookDao bookDao;
    public static UserDao userDao;

    public static void main(String[] args) {
        setupBackend(args);
        setupFrontend();
    }

    private static void setupBackend(String[] args) {
        //connect to MySQL database
        if (args.length == 2) {
            new DBDist(new DBUtils(args));
        }
        else {
            System.err.println("Unable to connect to MySQL database.");
        }
    }

    private static void setupFrontend() {
        // Instantiate your dependencies
        bookDao = new BookDao();
        userDao = new UserDao();

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(HerokuUtil.getHerokuAssignedPort());

        app.routes(() -> {
            before(Filters.handleLocaleChange);
            before(LoginController.ensureLoginBeforeViewingBooks);
            get(Web.INDEX, IndexController.serveIndexPage);
            get(Web.BOOKS, BookController.fetchAllBooks);
            get(Web.ONE_BOOK, BookController.fetchOneBook);
            get(Web.LOGIN, LoginController.serveLoginPage);
            post(Web.LOGIN, LoginController.handleLoginPost);
            post(Web.LOGOUT, LoginController.handleLogoutPost);
        });

        app.error(404, ViewUtil.notFound);
    }
}
