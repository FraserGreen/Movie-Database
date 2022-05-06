package frontend.controller;

import frontend.controller.paths.Template;
import io.javalin.http.Handler;
import java.util.Map;
import frontend.controller.utils.ViewUtil;
import static frontend.Main.*;
import static frontend.controller.utils.RequestUtil.*;





public class BookController {

    public static Handler fetchAllBooks = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("books", bookDao.getAllBooks());
        ctx.render(Template.BOOKS_ALL, model);
    };

    public static Handler fetchOneBook = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("book", bookDao.getBookByIsbn(getParamIsbn(ctx)));
        ctx.render(Template.BOOKS_ONE, model);
    };
}
