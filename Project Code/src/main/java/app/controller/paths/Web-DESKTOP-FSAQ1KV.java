package app.controller.paths;

public class Web {
    public static final String INDEX = "/";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String SIGNUP = "/signUp";
    public static final String ADD_SHOW = "/addShow";
    public static final String ADD_REVIEW = "/addReview/:showID";
    public static final String VIEW_ALL_SHOWS = "/viewAllShows";
    public static final String VIEW_SHOW = "/viewShow/:showID";
    public static final String DELETE_SHOW = "/deleteShow/:showID";
    public static final String EDIT_SHOW = "/editShow/:showID";
    public static final String EDIT_SHOW_STATUS = "/editShowStatus/:showID";
    public static final String CREATE_ACCOUNT_REQUEST = "/createAccountRequest";
    public static final String VIEW_ALL_ACCOUNT_REQUESTS = "/viewAllAccountRequests";
    public static final String VIEW_ACCOUNT_REQUEST = "/viewAccountRequest/:requestID";
    public static final String ACCEPT_ACCOUNT_REQUEST = "/acceptAccountRequest/:requestID";
    public static final String REJECT_ACCOUNT_REQUEST = "/rejectAccountRequest/:requestID";

}