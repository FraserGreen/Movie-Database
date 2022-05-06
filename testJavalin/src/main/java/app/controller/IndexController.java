package app.controller;

import app.controller.paths.Template;
import io.javalin.http.Handler;
import java.util.Map;
import app.controller.utils.ViewUtil;
import static app.Main.*;





public class IndexController {
    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("users", userDao.getAllUserNames());
        model.put("book", bookDao.getRandomBook());
        ctx.render(Template.INDEX, model);
    };
    public static Handler serveHello = ctx -> {
        System.out.println("Hello World");
    };
}
