package model;

import java.sql.ResultSet;
import java.sql.SQLException;
public class Account {

    String firstName;
    String lastName;
    String address;
    String username;
    String password;
    String country;
    String gender;
    String email;
    static String type = "Unregistered";

    public void logIn(DBUtils db, String username, String password) {
        db.logIn(this, username, password);
    }

    public String getType() {
        return type;
    }

    public void updateDetails(){

    }
    public void setType(String type){
        Account.type = type;
    }
}
