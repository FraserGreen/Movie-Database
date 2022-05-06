package frontend.controller;

import backend.Account;
import backend.DBDist;
import frontend.model.User;
import org.mindrot.jbcrypt.BCrypt;

import static frontend.Main.*;





public class UserController {

    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public static boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, user.salt);
        return hashedPassword.equals(user.hashedPassword);
    }


    // This method doesn't do anything, it's just included as an example
    public static void setPassword(String username, String oldPassword, String newPassword) {
        if (authenticate(username, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password
        }
    }
    public static void attemptLogin(Account account, String username, String password){
        DBDist.db.logIn(account, username, password);
    }
    //public static void attemptRegistration(String[] args){
    //    DBDist.db.signup(args);
    //}
}
