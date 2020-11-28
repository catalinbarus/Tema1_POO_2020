package corefunctions;

import corecomponents.CoreMovie;
import corecomponents.CoreSeries;
import fileio.*;

import java.util.Map;
import corecomponents.CoreShow;
import corecomponents.CoreUsers;

public class CoreCommands {

    /**
     *
     */
    public int addFavorite(final String title,
                                   final CoreUsers user) {
        for (int j = 0; j < user.getFavoriteMovies().size(); j++) {
            if(title.equals(user.getFavoriteMovies().get(j))) {
                return 0;
            }
        }

        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if (entry.getKey().equals(title)) {
                user.getFavoriteMovies().add(title);
                return 1;
            }
        }
        return 2;
    }

    /**
     *
     */
   public static void addView(final String title, final CoreUsers user) {
       int views;

       for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
           if (entry.getKey() == title) {
               user.getHistory().put(entry.getKey(), user.getHistory().get(entry.getKey()) + 1);
           } else {
               user.getHistory().put(entry.getKey(), 1);
           }
       }

   }

    /**
     *
     */
    public void addRatingmovie(final CoreMovie movie, final CoreUsers user,
                               final double rating) {
        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if (entry.getKey() == movie.getTitle() && movie.getRating() == 0) {
                movie.setRating(rating);
            }
        }
    }

    /**
     *
     */
    public void addRatingseries(final CoreSeries series, final CoreUsers user,
                                final double rating, final int seasonidx) {
        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if (entry.getKey() == series.getTitle()) {
                if (series.getSeasons().get(seasonidx).getRatings().get(seasonidx) == 0) {
                        series.setRating(rating);
                }
            }
        }

        double newrating = 0;

        for (int i = 0; i < series.getSeasons().size(); i++) {
            newrating =
                    newrating + series.getSeasons().get(i).getRatings().get(i);
        }

        newrating = newrating / series.getNumberSeason();
    }
}
