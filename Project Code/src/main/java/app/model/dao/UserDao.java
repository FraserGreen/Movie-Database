package app.model.dao;

import app.backend.DBDist;
import app.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;
public class UserDao {

    private ArrayList<User> users = new ArrayList<>();

    public UserDao() {
        getAllFromDatabase();
    }

    private void getAllFromDatabase() {
        try {
            ResultSet s = DBDist.db.inquire("select * from `account`;");
            int numColumns = DBDist.db.getNumColumns("account");
            while (s.next()) {

                String[] str = new String[numColumns];

                for (int col = 0; col < numColumns; col++) {

                    str[col] = s.getString(1 + col);
                }

                users.add(new User(false, str[0], str[1], str[2], str[3]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Shows could not be loaded from database");
        }
    }

    public User getUserByUsername(String username) {
        return users.stream().filter(b -> b.username.equals(username)).findFirst().orElse(null);
    }

    public Iterable<String> getAllUsernames() {
        return users.stream().map(user -> user.username).collect(Collectors.toList());
    }

    public void add(User user){
        users.add(user);
    }
}
