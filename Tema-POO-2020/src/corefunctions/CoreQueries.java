package corefunctions;

import corecomponents.CoreMovie;
import corecomponents.CoreSeries;
import corecomponents.CoreUsers;
import fileio.ActionInputData;

import java.util.*;

public class CoreQueries {

    /**
     *
     */
    public List<String> executeFavouritemovie(final ActionInputData action,
                                              final List<CoreMovie> movielist,
                                              final List<CoreUsers> userlist) {

        List<String> showlist = new ArrayList<>();
        List<String> display = new ArrayList<>();
        Map<String, Integer> favourite = new HashMap<>();
        Map<String, Integer> unsorted = new HashMap<>();

        String release = action.getFilters().get(0).get(0);
        String showgenre = action.getFilters().get(1).get(0);

        int movieidx = 0;

        for (int j = 0; j < movielist.size(); j++) {
            for (int k = 0; k < userlist.size(); k++) {
                for (int l = 0; l < userlist.get(k).getFavoriteMovies().size(); l++) {
                    if (userlist.get(k).getFavoriteMovies().get(l)
                            .equals(movielist.get(j).getTitle())) {
                        movieidx++;
                    }
                }
            }
            favourite.put(movielist.get(j).getTitle(), movieidx);
            movieidx = 0;
        }

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
        }

        return display;
    }

    /**
     *
     */
    public List<String> executeLongestseries(final ActionInputData action,
                                               final List<CoreSeries> serieslist) {

        List<String> display = new ArrayList<>();
        TreeMap<String, Integer> unsorted = new TreeMap<>();
        TreeMap<String, Integer> length = new TreeMap<>();
        String key;
        Integer value = 0;

        String year = action.getFilters().get(0).get(0);
        String genre = action.getFilters().get(1).get(0);

        for (int j = 0; j < serieslist.size(); j++) {
            key = serieslist.get(j).getTitle();

            for (int l = 0; l < serieslist.get(j).getSeasons().size(); l++) {
                value =
                        value + serieslist.get(j).getSeasons().get(l).getDuration();
            }
            length.put(key, value);

            String movieyear = String.valueOf(serieslist.get(j).getYear());

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

        for (Map.Entry<String, Integer> entry : unsorted.entrySet()) {
            display.add(entry.getKey());
        }

        return display;
    }

    public List<String> executeLongestmovies(final ActionInputData action,
                                               final List<CoreMovie> movielist) {

        List<String> display = new ArrayList<>();
        TreeMap<String, Integer> unsorted = new TreeMap<>();
        TreeMap<String, Integer> length = new TreeMap<>();
        String key;
        Integer value;

        String year = action.getFilters().get(0).get(0);
        String genre = action.getFilters().get(1).get(0);

        for (int j = 0; j < movielist.size(); j++) {
            key = movielist.get(j).getTitle();
            value = movielist.get(j).getDuration();
            length.put(key, value);

            String movieyear = String.valueOf(movielist.get(j).getYear());

            if (movieyear.equals(year)) {
                for (int k = 0; k < movielist.get(j).getGenres().size(); k++) {
                    if (movielist.get(j).getGenres().get(k).equals(genre)) {
                        unsorted.put(movielist.get(j).getTitle(),
                                movielist.get(j).getDuration());
                    }
                }
            }
        }

        for (Map.Entry<String, Integer> entry : unsorted.entrySet()) {
            display.add(entry.getKey());
        }

        return display;
    }
}
