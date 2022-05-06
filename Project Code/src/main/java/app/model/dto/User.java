package app.model.dto;

import org.mindrot.jbcrypt.BCrypt;
public class User {

    private String firstName;
    private String lastName;
    private char sex;
    private String birthYear;
    private String country;
    private String postCode;
    private String email;
    private String password;
    private String salt;
    private String type;
    private String orgName;
    private String orgPhone;

    //<editor-fold desc="Constructors">
    public User(String firstName, String lastName, char sex, String birthYear, String country, String postCode,
                String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthYear = birthYear;
        this.country = country;
        this.postCode = postCode;
        salt = BCrypt.gensalt();
        this.email = email;
        this.password = BCrypt.hashpw(password, salt);
        this.type = "Regular";
        this.orgName = null;
        this.orgPhone = null;
    }

    public User(String firstName, String lastName, char sex, String birthYear, String country, String postCode,
                String email, String password, String salt, String type, String orgName, String orgPhone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthYear = birthYear;
        this.country = country;
        this.postCode = postCode;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.type = type;
        this.orgName = orgName;
        this.orgPhone = orgPhone;
    }
//</editor-fold>

    //<editor-fold desc="Getters and Setters">

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrgName() {
        return orgName;
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

    //</editor-fold>
}
