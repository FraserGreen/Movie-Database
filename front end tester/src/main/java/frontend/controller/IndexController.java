package frontend.controller;

import frontend.controller.paths.Template;
import io.javalin.http.Handler;
import java.util.Map;
import frontend.controller.utils.ViewUtil;
import static frontend.Main.*;





public class IndexController {
    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("users", userDao.getAllUserNames());
        model.put("book", bookDao.getRandomBook());
        ctx.render(Template.INDEX, model);
    };
}
