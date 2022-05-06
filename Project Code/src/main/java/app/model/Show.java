package app.model;

import app.backend.DBDist;
public class Show {

    public final String id;
    public final String title;
    public final String genre;
    public final String length;
    public final String isMovie;
    public final String isSeries;
    public final String procoID;
    public final String year;
    public String imageFilename;

    public Show(boolean addToDatabase, String[] values) {
        if (values.length != 9) {
            throw new IndexOutOfBoundsException();
        }
        id = values[0];
        title = values[1];
        genre = values[2];
        length = values[3];
        isMovie = values[4];
        isSeries = values[5];
        procoID = values[6];
        year = values[7];
        imageFilename = values[8];
        if (addToDatabase) {
            addToDatabase();
        }
    }

    private void addToDatabase() {
        DBDist.db.insertInto("show",
                new String[]{id, title, genre, length, isMovie, isSeries, procoID, year, imageFilename});
    }

    public String getTitle() {
        return title;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public String getId() {
        return id;
    }
}
