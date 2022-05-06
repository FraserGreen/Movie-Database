package app.model.dto;

public class AccountRequest {

    private int requestID;
    private String email;
    private String type;
    private String orgName;
    private String orgPhone;
    private String date;

    //<editor-fold desc="Constructor">
    public AccountRequest(int requestID, String email, String type, String orgName, String orgPhone,
                          String date) {
        this.requestID = requestID;
        this.email = email;
        this.type = type;
        this.orgName = orgName;
        this.orgPhone = orgPhone;
        this.date = date;
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //</editor-fold>
}
