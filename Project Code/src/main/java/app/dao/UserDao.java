package app.dao;

import app.model.DBDist;
import app.model.dto.User;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class UserDao {

    private final ArrayList<User> users = new ArrayList<>();

    public UserDao() {
        getAllFromDatabase();
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    private void getAllFromDatabase() {
        try {
            ResultSet s = DBDist.db.inquire("select * from `account`;");
            int numColumns = DBDist.db.getNumColumns("account");
            //goes through database, and retrieves existing user records
            //parses output, adds user records to array
            while (s.next()) {

                String[] str = new String[numColumns];

                for (int col = 0; col < numColumns; col++) {

                    str[col] = s.getString(1 + col);
                }
                users.add(new User(str[0], str[1], str[2].charAt(0), str[3], str[4], str[5], str[6], str[7], str[8], str[9],
                        str[10], str[11]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Users could not be loaded from database");
        }
    }

    public boolean addToDatabase(String[] fields) {
        return DBDist.db.insertInto("account", fields);
    }

    public User getUserByEmail(String email) {
        return users.stream().filter(b -> b.getEmail().equals(email)).findFirst().orElse(null);
    }

    public void add(@NotNull User user) {
        if (addToDatabase(new String[]{user.getFirstName(), user.getLastName(), String.valueOf(user.getSex()), user.getBirthYear(),
                                       user.getCountry(), user.getPostCode(), user.getEmail(), user.getPassword(),
                                       user.getSalt(), user.getType(), user.getOrgName(), user.getOrgPhone()})) {
            users.add(user);
        }
    }

    public boolean authenticateLogin(String email, String salt, String plainTextPassword){

        String password = BCrypt.hashpw(plainTextPassword, salt);

        User account = getUserByEmail(email);
        return account !=null && account.getSalt().equals(salt) && account.getPassword().equals(password);
    }
}
