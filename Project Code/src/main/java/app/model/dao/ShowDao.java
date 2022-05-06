package app.model.dao;

import app.backend.DBDist;
import app.model.Show;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;
public class ShowDao {

    private ArrayList<Show> shows = new ArrayList<>();

    public ShowDao() {
        getAllFromDatabase();
    }

    private void getAllFromDatabase() {
        try {
            ResultSet s = DBDist.db.inquire("select * from `show`;");
            int numColumns = DBDist.db.getNumColumns("show");
            while (s.next()) {

                String[] values = new String[numColumns];

                for (int col = 0; col < numColumns; col++) {

                    values[col] = s.getString(1 + col);
                }

                shows.add(new Show(false, values));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Shows could not be loaded from database");
        }
    }

    public Show getShowById(String id) {
        return shows.stream().filter(b -> b.id.equals(id)).findFirst().orElse(null);
    }

    public Iterable<String> getAllShowTitles() {
        return shows.stream().map(show -> show.title).collect(Collectors.toList());

    }
    public Iterable<Show> getAllShows() {
        return shows;

    }

    public Iterable<String> getAllShowIDs() {
        return shows.stream().map(show -> show.id).collect(Collectors.toList());
    }
    public Iterable<String> getAllShowImages() {
        return shows.stream().map(show -> show.imageFilename).collect(Collectors.toList());
    }

    public void addShow(Show show){
        shows.add(show);
    }
}
