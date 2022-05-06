package app.dao;

import app.Main;
import app.model.DBDist;
import app.model.dto.Show;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ShowDao {

    private final ArrayList<Show> shows = new ArrayList<>();

    public ShowDao() {
        getAllFromDatabase();
    }

    private void getAllFromDatabase() {
        try {
            ResultSet s = DBDist.db.inquire("SELECT * FROM `SHOW`;");
            int numColumns = DBDist.db.getNumColumns("show");
            while (s.next()) {

                String[] values = new String[numColumns];

                for (int col = 0; col < numColumns; col++) {

                    values[col] = s.getString(1 + col);
                }

                shows.add(
                        new Show(Integer.parseInt(values[0]), values[1], values[2], Double.parseDouble(values[3]),
                                values[4].equals("1"), values[5].equals("1"),
                                Integer.parseInt(values[6]), values[7], values[8], values[9]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Shows could not be loaded from database");
        }
    }

    public void deleteShow(int showID) {
        //todo try using if() below as deleteall.. returns boolean
        if (Main.reviewDao.getReviewById(showID) != null) {
            Main.reviewDao.deleteAllReviewsByShowID(showID);
        }
        DBDist.db.execute("DELETE FROM `SHOW` WHERE SHOWID = '" + showID + "';");
        shows.remove(getShowById(showID));
    }

    public Show getShowById(int id) {
        return shows.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public ArrayList<Show> getAllShows() {
        return shows;
    }

    public void add(@NotNull Show show) {
        if (addToDatabase(show.getId(), show.getTitle(), show.getGenre(),
                show.getLength(), show.getIsMovie(),
                show.getIsSeries(), show.getProcoID(),
                show.getYear(), show.getImageFilename(), show.getStatus())) {
            shows.add(show);
        }
    }

    public void editShow(int id, String title, String genre, double length, boolean isMovie, boolean isSeries,
                         int procoID, String year, String imageFilename, String status) {
        if (DBDist.db.execute(
                "update `show` set show_title = '" + title + "', genre = '" + genre + "', length = '" + length + "', " +
                        "movie = " + isMovie + ", series = " + isSeries + ", proco_id = '" + procoID + "', year " +
                        "= '" + year + "', path_to_image = '" + imageFilename + "', status = '" + status +
                        "' where showid = '" + id + "';")) {
            Show show = getShowById(id);
            show.setTitle(title);
            show.setGenre(genre);
            show.setLength(length);
            show.setIsMovie(isMovie);
            show.setIsSeries(isSeries);
            show.setProcoID(procoID);
            show.setYear(year);
            show.setImageFilename(imageFilename);
            show.setStatus(status);
        }
    }

    private boolean addToDatabase(int id, String title, String genre, double length, boolean isMovie, boolean isSeries,
                                  int procoID, String year, String imageFilename, String status) {
        String query = "insert into `show` values(" + id + ",'" + title + "','" + genre + "','" + length + "'," + isMovie + "," + isSeries +
                "," + procoID + ",'" + year + "','" + imageFilename + "','" + status + "');";
        return DBDist.db.execute(query
                                );
    }
}
