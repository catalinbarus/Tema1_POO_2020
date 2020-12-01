package corecomponents;

import java.util.ArrayList;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class CoreMovie {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    private final String title;
    /**
     * The year the show was released
     */
    private final int year;

    /**
     * The rating the show received
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
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;

    public CoreMovie(final String title, final ArrayList<String> cast,
                     final ArrayList<String> genres, final int year,
                     final int duration) {

        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.duration = duration;
        this.rating = 0;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + getTitle() + "year= "
                + getYear() + "duration= "
                + duration + "cast {"
                + getCast() + " }\n"
                + "genres {" + getGenres() + " }\n ";
    }
}

