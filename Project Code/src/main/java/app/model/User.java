package app.model;

import app.backend.DBDist;
import org.mindrot.jbcrypt.BCrypt;
public class User {

    public final String username;
    public final String password;
    public final String salt;
    public final String type;

    public User(boolean addToDatabase, String[] values) {
        salt = BCrypt.gensalt();
        username = values[0];
        password = BCrypt.hashpw(values[1],salt);
        this.type = "Regular";
        if (addToDatabase) {
            addToDatabase();
        }
    }

    public User (boolean addToDatabase, String username, String password, String salt, String type){
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.type = type;
    }

    private void addToDatabase() {
        DBDist.db.insertInto("account", new String[]{username,password,salt,type});
    }
}
