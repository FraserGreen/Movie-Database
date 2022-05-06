package app.controller;

import app.Main;
import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.model.dto.AccountRequest;
import app.model.dto.User;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

import static app.controller.utils.RequestUtil.*;

public class AccountRequestController {

    public static Handler serveCreateAccountRequestPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.CREATE_ACCOUNT_REQUEST, model);
    };

    public static Handler handleCreateAccountRequestPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put((validateCreateAccountRequestPost(ctx) ? "authenticationSucceeded" : "authenticationFailed"), true);
        ctx.render(Template.CREATE_ACCOUNT_REQUEST, model);
    };

    public static Handler handleAcceptAccountRequestPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        AccountRequest acctReq = Main.accountRequestDao.getRequestByID(Integer.parseInt(getParamRequestID(ctx)));
        User account = Main.userDao.getUserByEmail(acctReq.getEmail());

        //only works on non-admins
        if (!account.getType().equals("Admin")) {

            //modifies user's details to make them into a critic/pco, in model and database
            account.setType(acctReq.getType());
            account.setOrgName(acctReq.getOrgName());
            account.setOrgPhone(acctReq.getOrgPhone());

            Main.accountRequestDao.acceptRequest(account);
        }
        Main.accountRequestDao.remove(Main.accountRequestDao.getRequestByID(Integer.parseInt(getParamRequestID(ctx))));
        model.put("requests", Main.accountRequestDao.getAllRequests());
        ctx.render(Template.VIEW_ALL_ACCOUNT_REQUESTS, model);
    };

    public static Handler handleRejectAccountRequestPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Main.accountRequestDao.remove(Main.accountRequestDao.getRequestByID(Integer.parseInt(getParamRequestID(ctx))));
        ctx.render(Template.VIEW_ALL_ACCOUNT_REQUESTS, model);
    };


    private static boolean validateCreateAccountRequestPost(Context ctx) {
        try {
            //gets all account requests
            ArrayList<AccountRequest> requests = Main.accountRequestDao.getAllRequests();
            //gets all request IDS, puts them in an array
            ArrayList<Integer> allIDs = new ArrayList<>();
            for (AccountRequest request : requests) {
                allIDs.add(request.getRequestID());
            }
            //gets the max request ID. new max = max + 1. if none exist, new max = 1.
            int max;
            try {
                max = Collections.max(allIDs) + 1;
            } catch (NoSuchElementException ex) {
                max = 1;
            }

            User user = ctx.sessionAttribute("currentUser");

            //attempts to add account request
            return user != null && Main.accountRequestDao.add(new AccountRequest(max,
                    user.getEmail(), getQueryAccountRequestType(ctx), getQueryOrgName(ctx),
                    getQueryOrgPhone(ctx), LocalDate.now().toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Handler serveViewALlAccountRequestsPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        User user = ctx.sessionAttribute("currentUser");

        //this page is currently admin-only.
        //if the current user is not an admin, they wont be able to access the page.
        if (user != null && user.getType().equals("Admin")) {
            model.put("requests", Main.accountRequestDao.getAllRequests());
            ctx.render(Template.VIEW_ALL_ACCOUNT_REQUESTS, model);
        }
        else {
            ctx.render(Template.NOT_FOUND, model);
        }
    };

    public static Handler serveViewAccountRequestPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("request", Main.accountRequestDao.getRequestByID(Integer.parseInt(getParamRequestID(ctx))));
        User user = ctx.sessionAttribute("currentUser");

        //this page is currently admin-only.
        //if the current user is not an admin, they wont be able to access the page.
        if (user != null && user.getType().equals("Admin")) {
            model.put("requests", Main.accountRequestDao.getAllRequests());
            ctx.render(Template.VIEW_ACCOUNT_REQUEST, model);
        }
        else {
            ctx.render(Template.NOT_FOUND, model);
        }
    };
}
