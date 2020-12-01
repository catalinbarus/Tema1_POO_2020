package corefunctions;

import corecomponents.CoreMovie;
import corecomponents.CoreSeries;
import corecomponents.CoreUsers;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CoreRecommendation {

    /**
     *
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
     *
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
     *
     */
    public int checkseriesRating(final String genre, final CoreSeries series) {

        for (int i = 0; i < series.getGenres().size(); i++) {

            if (genre.equals(series.getGenres().get(i))) {
                return 1;
            }
        }

        return 0;
    }

    public List<String> searchRecommendation(final ActionInputData action,
                                             final List<CoreUsers> userlist,
                                             final List<CoreMovie> movielist,
                                             final List<CoreSeries> serieslist) {

        List<String> titles = new ArrayList<>();
        int ok = 0;

        for (int j = 0; j < userlist.size(); j++) {
            if (userlist.get(j).getUsername().equals(action.getUsername())) {

                if (userlist.get(j).getSubscriptionType().equals("PREMIUM")) {

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

                    Collections.sort(titles);
                    ok = 1;


                }
            }
        }

        return titles;
    }
}
