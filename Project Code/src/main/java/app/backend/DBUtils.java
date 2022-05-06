package app.backend;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class DBUtils {

    private static Connection conn;

    public DBUtils(String[] args) {
        conn = connectToDatabase(args[0], args[1]);
    }

    public static Connection getConnection() {
        return conn;
    }

    public boolean insertInto(String table, String[] variables) {
        try {

            String str = "insert into `" + table + "` values ('";
            for (int i = 0; i < variables.length; i++) {
                str += variables[i];

                if (i + 1 != getNumColumns(table)) {
                    str += ("', '");
                }
            }
            str += "');";
            conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).execute(str);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ResultSet inquire(String query) {
        try {
            return conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE).executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void execute(String query) {
        try {
            conn.createStatement().execute(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

    public int getNumColumns(String table) {
        try {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery(
                    "select count(*) " +
                            "from information_schema.columns " +
                            "where table_name = '" + table + "';");
            result.next();
            return result.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public void printAllFromTable(String table) {
        try {

            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery("select * from `" + table + "`;");

            printResultSet(result);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void printResultSet(ResultSet result) {
        printResultHead(result);
        try {
            while (result.next()) {
                int numColumns = result.getMetaData().getColumnCount();
                for (int i = 1; i <= numColumns; i++) {
                    System.out.printf("%-" + maxColumnLength(result, i) + "s  ", result.getString(i));
//                }
                }
                System.out.println();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void printResultHead(ResultSet result) {
        try {
            ResultSetMetaData metaData = result.getMetaData();
            int numColumns = metaData.getColumnCount();
            int maxChars = 0;
            for (int i = 1; i <= numColumns; i++) {
                System.out.printf("%-" + maxColumnLength(result, i) + "s  ", metaData.getColumnLabel(i));
                maxChars += maxColumnLength(result, i);
            }

            System.out.println();
            for (int i = 2; i < maxChars + 2 * numColumns; i++)
                System.out.print('-');
            System.out.println();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

    private int maxColumnLength(ResultSet result, int column) {
        try {
            //initialises max as the column label's length
            int max = result.getMetaData().getColumnLabel(column).length();
            int originalRow = result.getRow(); //gets state of cursor before iterating over resultSet
            result.beforeFirst();
            while (result.next()) {
                int candidate = result.getString(column).length();
                if (candidate > max) {
                    max = candidate;
                }
            }
            result.absolute(originalRow); //returns cursor to original state
            return max;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public void testConnection() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("Select username from account");
            while (rs.next()) {
                String username = rs.getString("username");
                System.out.println(username);
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
        }
    }

    private Connection connectToDatabase(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/moviemad?user=" +
                    username + "&password=" + password);
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
            return null;
        }
    }

    public void signUp(String[] info) {
        //check parameter is valid
        int numArgs = 7;
        if (info.length != numArgs) {
            System.err.println("Incorrect number of arguments");
            return;
        }
        try {
            //check if username is not taken
            ResultSet isUsernameTaken = inquire("select username from account where username = '" + info[0] + "'");
            if (isUsernameTaken.next()) {
                System.err.println("Username already taken");
                return;
            }

            String[] newInfo = new String[numArgs + 1];
            System.arraycopy(info, 0, newInfo, 0, numArgs);
            newInfo[numArgs] = "Regular";
            insertInto("account", newInfo);
            System.out.println("Successfully signed up.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean logIn(String username, String salt, String plainTextPassword) {
        ResultSet checkUsername = inquire("select username from `account` where username = '" + username + "'");
        try {

            //check input is not null
            if (username == null || plainTextPassword == null || salt == null) {
                return false;
            }
            String password = BCrypt.hashpw(plainTextPassword, salt);

            //check if username exists
            if (!checkUsername.next()) {
//                System.err.println("Username does not exist");
                return false;
            }
            ResultSet checkPassword = inquire("select password from `account` where username = '" + username + "'");
            checkPassword.next();

            //check if password matches
            return checkPassword.getString(1).equals(password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

