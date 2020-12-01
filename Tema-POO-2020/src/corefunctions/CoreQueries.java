package corefunctions;

import corecomponents.CoreMovie;
import corecomponents.CoreSeries;
import corecomponents.CoreUsers;
import fileio.ActionInputData;

import java.util.*;

/**
 * Utility functions for issuing various commands. Their purpose is to ease the core
 * implementations as well as to shorten the code.
 */
public class CoreQueries {

    /**
     * Sorts the movies based on the number of appearances in users' lists of favourite movies.
     */
    public List<String> executeFavouritemovie(final ActionInputData action,
                                              final List<CoreMovie> movielist,
                                              final List<CoreUsers> userlist) {

        List<String> showlist = new ArrayList<>();
        List<String> display = new ArrayList<>();
        Map<String, Integer> favourite = new HashMap<>();
        Map<String, Integer> unsorted = new HashMap<>();

        /** Get the required filters. **/
        String release = action.getFilters().get(0).get(0);
        String showgenre = action.getFilters().get(1).get(0);

        int movieidx = 0;

        /** Count the number of movies in users' lists of favourite movies. **/
        for (int j = 0; j < movielist.size(); j++) {
            for (int k = 0; k < userlist.size(); k++) {
                for (int l = 0; l < userlist.get(k).getFavoriteMovies().size(); l++) {
                    if (userlist.get(k).getFavoriteMovies().get(l)
                            .equals(movielist.get(j).getTitle())) {
                        movieidx++;
                    }
                }
            }
            /** Add each title in a hashmap that remembers the number of appearances. **/
            favourite.put(movielist.get(j).getTitle(), movieidx);
            movieidx = 0;
        }

        /** Check if the filters can be applied or not. **/
        if (release != null) {

            for (int l = 0; l < movielist.size(); l++) {
                String movieyear = String.valueOf(movielist.get(l).getYear());
                if (movieyear.equals(release)) {
                    showlist.add(movielist.get(l).getTitle());
                }
            }
        } else if (showgenre != null) {
            for (int l = 0; l < movielist.size(); l++) {
                for (int m = 0; m < movielist.get(l).getGenres().size(); m++) {
                    if (showgenre.equals(movielist.get(l).getGenres().get(m))) {
                        showlist.add(movielist.get(l).getTitle());
                    }
                }
            }
            /** If the filters are not applied simply put all the movies in a hashmap. **/
        } else {
            unsorted.putAll(favourite);
        }

        /** If some filters were found put those movies in the hashmap based on the index from
         * the favourite hashmap.
         *  **/
        for (int j = 0; j < showlist.size(); j++) {
            for (Map.Entry<String, Integer> entry : favourite.entrySet()) {
                if (showlist.get(j).equals(entry.getKey())) {
                    unsorted.put(entry.getKey(), favourite.get(entry.getKey()));
                }
            }
        }

        /** Add only the titles, which are now sorted in the hashmap, in a list to be displayed. **/
        for (Map.Entry<String, Integer> entry : unsorted.entrySet()) {
            display.add(entry.getKey());
        }

        return display;
    }

    /**
     * Sorts the series based on the number of appearances in users' lists of favourite movies.
     */
    public List<String> executeFavouriteseries(final ActionInputData action,
                                                final List<CoreSeries> serieslist,
                                                final List<CoreUsers> userlist) {

        List<String> showlist = new ArrayList<>();
        List<String> display = new ArrayList<>();
        Map<String, Integer> favourite = new HashMap<>();
        Map<String, Integer> unsorted = new HashMap<>();
        int ok = 0;

        String release = action.getFilters().get(0).get(0);
        String showgenre = action.getFilters().get(1).get(0);

        int movieidx = 0;

        for (int j = 0; j < serieslist.size(); j++) {
            for (int k = 0; k < userlist.size(); k++) {
                for (int l = 0; l < userlist.get(k)
                        .getFavoriteMovies().size(); l++) {
                    if (userlist.get(k).getFavoriteMovies().get(l)
                            .equals(serieslist.get(j).getTitle())) {
                        movieidx++;
                    }
                }
            }
            favourite.put(serieslist.get(j).getTitle(), movieidx);
            movieidx = 0;
        }

        if (release != null) {

            for (int l = 0; l < serieslist.size(); l++) {
                String movieyear = String.valueOf(serieslist.get(l).getYear());
                if (movieyear.equals(release)) {
                    showlist.add(serieslist.get(l).getTitle());
                }
            }
        } else if (showgenre != null) {
            for (int l = 0; l < serieslist.size(); l++) {
                for (int m = 0; m < serieslist.get(l).getGenres().size(); m++) {
                    if (showgenre.equals(serieslist.get(l).getGenres().get(m))) {
                        showlist.add(serieslist.get(l).getTitle());
                    }
                }
            }
        } else {
            unsorted.putAll(favourite);
        }

        for (int j = 0; j < showlist.size(); j++) {
            for (Map.Entry<String, Integer> entry : favourite.entrySet()) {
                if (showlist.get(j).equals(entry.getKey())) {
                    unsorted.put(entry.getKey(), favourite.get(entry.getKey()));
                }
            }
        }

        for (Map.Entry<String, Integer> entry : unsorted.entrySet()) {
            display.add(entry.getKey());
            break;
        }

        return display;
    }

    /**
     * Sorts the series based on their duration.
     */
    public List<String> executeLongestseries(final ActionInputData action,
                                               final List<CoreSeries> serieslist) {

        List<String> display = new ArrayList<>();
        TreeMap<String, Integer> unsorted = new TreeMap<>();
        TreeMap<String, Integer> length = new TreeMap<>();
        String key;
        Integer value = 0;

        /** Get the required filters. **/
        String year = action.getFilters().get(0).get(0);
        String genre = action.getFilters().get(1).get(0);

        for (int j = 0; j < serieslist.size(); j++) {
            key = serieslist.get(j).getTitle();

            /** Add up the duration for each season. **/
            for (int l = 0; l < serieslist.get(j).getSeasons().size(); l++) {
                value =
                        value + serieslist.get(j).getSeasons().get(l).getDuration();
            }
            length.put(key, value);

            String movieyear = String.valueOf(serieslist.get(j).getYear());

            /** Check if the filters can be applied. **/
            if (movieyear.equals(year)) {
                for (int k = 0; k < serieslist.get(j).getGenres().size(); k++) {
                    if (serieslist.get(j).getGenres().get(k).equals(genre)) {
                        unsorted.put(serieslist.get(j).getTitle(),
                                value);
                    }
                }
            }
            value = 0;
        }

        /** Add only the titles, which are now sorted in the hashmap, in a list to be displayed. **/
        for (Map.Entry<String, Integer> entry : unsorted.entrySet()) {
            display.add(entry.getKey());
        }

        return display;
    }

    /**
     * Sorts the movies based on their duration.
     */
    public List<String> executeLongestmovies(final ActionInputData action,
                                               final List<CoreMovie> movielist) {

        List<String> display = new ArrayList<>();
        TreeMap<String, Integer> unsorted = new TreeMap<>();
        TreeMap<String, Integer> length = new TreeMap<>();
        String key;
        Integer value;

        /** Get the required filters. **/
        String year = action.getFilters().get(0).get(0);
        String genre = action.getFilters().get(1).get(0);

        for (int j = 0; j < movielist.size(); j++) {
            key = movielist.get(j).getTitle();
            value = movielist.get(j).getDuration();
            length.put(key, value);

            String movieyear = String.valueOf(movielist.get(j).getYear());

            /** Check if the filters can be applied. **/
            if (movieyear.equals(year)) {
                for (int k = 0; k < movielist.get(j).getGenres().size(); k++) {
                    if (movielist.get(j).getGenres().get(k).equals(genre)) {
                        unsorted.put(movielist.get(j).getTitle(),
                                movielist.get(j).getDuration());
                    }
                }
            }
        }

        /** Add only the titles, which are now sorted in the hashmap, in a list to be displayed. **/
        for (Map.Entry<String, Integer> entry : unsorted.entrySet()) {
            display.add(entry.getKey());
        }

        return display;
    }
}
