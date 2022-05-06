package app.dao;

import app.model.dto.AccountRequest;
import app.model.DBDist;
import app.model.dto.User;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class AccountRequestDao {

    private final ArrayList<AccountRequest> requests = new ArrayList<>();

    public AccountRequestDao() {
        getAllFromDatabase();
    }

    public ArrayList<AccountRequest> getAllRequests() {
        return requests;
    }

    private void getAllFromDatabase() {
        try {
            ResultSet s = DBDist.db.inquire("select * from `account_request`;");
            int numColumns = DBDist.db.getNumColumns("account_request");
            //goes through database, and retrieves existing user records
            //parses output, adds user records to array
            while (s.next()) {

                String[] str = new String[numColumns];

                for (int col = 0; col < numColumns; col++) {

                    str[col] = s.getString(1 + col);
                }
                requests.add(new AccountRequest(Integer.parseInt(str[0]), str[1], str[2], str[3], str[4], str[5]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Users could not be loaded from database");
        }
    }

    public boolean addToDatabase(String[] fields) {
        return DBDist.db.insertInto("account_request", fields);
    }

    public AccountRequest getRequestByID(int requestID) {
        return requests.stream().filter(b -> b.getRequestID()==requestID).findFirst().orElse(null);
    }

    public boolean add(@NotNull AccountRequest ar) {
        if (addToDatabase(new String[]{String.valueOf(ar.getRequestID()), ar.getEmail(), ar.getType(), ar.getOrgName(),
                                       ar.getOrgPhone(), ar.getDate()})) {
            requests.add(ar);
            return true;
        }
        else {
            return false;
        }
    }

    public void remove (AccountRequest ar){
        DBDist.db.execute("delete from `account_request` where request_id = '" + ar.getRequestID() + "';");
        requests.remove(ar);
    }

    public void acceptRequest(User account) {
        DBDist.db.execute(
                "update `account` set type = '" + account.getType() +
                        "', organisation_name = '" + account.getOrgName() +
                        "', organisation_phone = '" + account.getOrgPhone() +
                        "' where email = '" + account.getEmail() + "';");
    }
}
