package corecomponents;

import entertainment.Season;
import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class CoreSeries {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;

    /**
     * Rating for a TV Series
     */
    private double rating;

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }


    /**
     * Show genres
     */
    private final ArrayList<String> genres;

    public CoreSeries(final String title, final ArrayList<String> cast,
                      final ArrayList<String> genres,
                      final int numberOfSeasons, final ArrayList<Season> seasons,
                      final int year) {

        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.rating = 0;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + getTitle() + " " + " year= "
                + getYear() + " cast {"
                + getCast() + " }\n" + " genres {"
                + getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}

