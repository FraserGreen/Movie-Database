package backend;

public class Main {

    public static void main(String[] args) {
        if (areArgumentsValid(args)) {

            DBUtils db = new DBUtils(args);
            new DBDist(db);

            db.getConnection();
        }
    }

    private static boolean areArgumentsValid(String[] args) {
        if (args.length != 2) {
            System.err.println("Please input MySQL username and password in the command line. E.g.:");
            System.err.println("Main username password");
            return false;
        }
        return true;
    }
}
