package model;

import java.sql.Connection;
public class Show {

    Connection conn = DBUtils.getConnection();
    boolean isMovie;
    boolean isSeries;
    String title;
    String length;
    String genre;
    ProCo productionCompany;
    int year;
}
