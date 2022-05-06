package app.model;

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

    public boolean execute(String query){
        try {
            conn.createStatement().execute(query);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
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

    public static boolean testConnection() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select email from account");
            return rs.next();
        } catch (Exception e) {
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            return false;
        }
    }

    private Connection connectToDatabase(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
//            return DriverManager.getConnection("jdbc:mysql://localhost/moviemad?user=" +
//                    username + "&password=" + password);
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/moviemad", username, password);
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
            return null;
        }
    }

}

