package app.model.dto;

import org.mindrot.jbcrypt.BCrypt;
public class ProductionCompany {

    private int procoID;
    private String name;

    //<editor-fold desc="Constructors">

    public ProductionCompany(int procoID, String name) {
        this.procoID = procoID;
        this.name = name;
    }

//</editor-fold>

    //<editor-fold desc="Getters and Setters">

    public int getProcoID() {
        return procoID;
    }

    public void setProcoID(int procoID) {
        this.procoID = procoID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //</editor-fold>
}
