package app.controller;

import app.controller.paths.Template;
import io.javalin.http.Handler;

import java.util.Map;

import app.controller.utils.ViewUtil;

public class IndexController {

    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("isAdmin", ctx.sessionAttribute("isAdmin"));
        ctx.render(Template.INDEX, model);
    };
}
