package app.dao;

import app.model.dto.ProductionCompany;
import app.model.DBDist;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ProductionCompanyDao {

    private final ArrayList<ProductionCompany> procos = new ArrayList<>();

    public ProductionCompanyDao() {
        getAllFromDatabase();
    }

    public ArrayList<ProductionCompany> getAllProcos() {
        return procos;
    }

    private void getAllFromDatabase() {
        try {
            ResultSet s = DBDist.db.inquire("select * from `production_company`;");
            int numColumns = DBDist.db.getNumColumns("production_company");
            //goes through database, and retrieves existing production company records
            //parses output, adds production company records to array
            while (s.next()) {

                String[] str = new String[numColumns];

                for (int col = 0; col < numColumns; col++) {

                    str[col] = s.getString(1 + col);
                }
                procos.add(new ProductionCompany(Integer.parseInt(str[0]), str[1]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Procos could not be loaded from database");
        }
    }

    public boolean addToDatabase(String[] fields) {
        return DBDist.db.insertInto("production_company", fields);
    }

    public ProductionCompany getProcoByID(int procoID) {
        return procos.stream().filter(b -> b.getProcoID() == procoID).findFirst().orElse(null);
    }

    public void add(@NotNull ProductionCompany proco) {
        if (addToDatabase(new String[]{String.valueOf(proco.getProcoID()),proco.getName()})) {
            procos.add(proco);
        }
    }
}
