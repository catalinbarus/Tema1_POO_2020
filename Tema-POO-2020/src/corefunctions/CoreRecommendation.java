package corefunctions;

import corecomponents.CoreMovie;
import corecomponents.CoreSeries;
import corecomponents.CoreUsers;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Utility functions for issuing various commands. Their purpose is to ease the core
 * implementations as well as to shorten the code.
 */
public class CoreRecommendation {

    /**
     * Utility function that checks is a single video is in a user's history or not.
     */
    public int checkifinHistory(final String title, final Map<String, Integer> history) {

        for (Map.Entry<String, Integer> entry : history.entrySet()) {
            if (title.equals(entry.getKey())) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * Utility function that checks if a given genre is found in a movie's genres.
     */
    public int checkifhasRating(final String genre, final CoreMovie movie) {

        for (int i = 0; i < movie.getGenres().size(); i++) {

            if (genre.equals(movie.getGenres().get(i))) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * Utility function that checks if a given genre is found in a movie's genres.
     */
    public int checkseriesRating(final String genre, final CoreSeries series) {

        for (int i = 0; i < series.getGenres().size(); i++) {

            if (genre.equals(series.getGenres().get(i))) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * Function that applies the search recommendation for a given user.
     */
    public List<String> searchRecommendation(final ActionInputData action,
                                             final List<CoreUsers> userlist,
                                             final List<CoreMovie> movielist,
                                             final List<CoreSeries> serieslist) {

        List<String> titles = new ArrayList<>();

        for (int j = 0; j < userlist.size(); j++) {
            if (userlist.get(j).getUsername().equals(action.getUsername())) {

                /** Check if a user has a premium subscription or not. **/
                if (userlist.get(j).getSubscriptionType().equals("PREMIUM")) {

                    /** For both the movie list and series list, find the first video that is not
                     * in a user's history.
                     */
                    for (int k = 0; k < movielist.size(); k++) {
                        if (checkifinHistory(movielist.get(k).getTitle(),
                                userlist.get(j).getHistory()) == 0) {
                            if (checkifhasRating(action.getGenre(), movielist.get(k)) == 1) {
                                titles.add(movielist.get(k).getTitle());
                            }
                        }
                    }

                    for (int k = 0; k < serieslist.size(); k++) {
                        if (checkifinHistory(serieslist.get(k).getTitle(),
                                userlist.get(j).getHistory()) == 0) {
                            if (checkseriesRating(action.getGenre(),
                                    serieslist.get(k)) == 1) {
                                titles.add(serieslist.get(k).getTitle());
                            }
                        }
                    }

                    /** Sort the list with titles alphabetically. **/
                    Collections.sort(titles);
                }
            }
        }

        return titles;
    }
}
