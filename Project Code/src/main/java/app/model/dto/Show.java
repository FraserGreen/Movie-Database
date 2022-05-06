package app.model.dto;

public class Show {

    private final int id;
    private String title;
    private String genre;
    private double length;
    private boolean isMovie;
    private boolean isSeries;
    private int procoID;
    private String year;
    private String imageFilename;
    private String status;

    //<editor-fold desc="Constructor">
    public Show(int id, String title, String genre, double length, boolean isMovie, boolean isSeries,
                int procoID, String year, String imageFilename, String status) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.length = length;
        this.isMovie = isMovie;
        this.isSeries = isSeries;
        this.procoID = procoID;
        this.year = year;
        this.imageFilename = imageFilename;
        this.status = status;
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public boolean getIsMovie() {
        return isMovie;
    }

    public void setIsMovie(boolean isMovie) {
        this.isMovie = isMovie;
    }

    public boolean getIsSeries() {
        return isSeries;
    }

    public void setIsSeries(boolean isSeries) {
        this.isSeries = isSeries;
    }

    public int getProcoID() {
        return procoID;
    }

    public void setProcoID(int procoID) {
        this.procoID = procoID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //</editor-fold>



}
