package corecomponents;

import fileio.ShowInput;

import java.util.ArrayList;

/**
 * General information about show (video), retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public abstract class CoreShow {
    /**
     * Show's title
     */
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
     * Show genres
     */
    private final ArrayList<String> genres;

    public CoreShow(final ShowInput showData) {
        this.title = showData.getTitle();
        this.year = showData.getYear();
        this.cast = showData.getCast();
        this.genres = showData.getGenres();
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }
}

