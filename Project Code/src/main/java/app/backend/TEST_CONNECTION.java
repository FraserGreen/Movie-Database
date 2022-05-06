package app.backend;

public class TEST_CONNECTION {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Please input MySQL username and password in the command line. E.g.:");
            System.err.println("TEST_CONNECTION username password");
            System.exit(0);
        }
        DBUtils db = new DBUtils(args);
        db.testConnection();
    }
}
