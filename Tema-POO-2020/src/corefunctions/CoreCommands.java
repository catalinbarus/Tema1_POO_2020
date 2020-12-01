package corefunctions;

import corecomponents.CoreSeries;
import java.util.Map;
import corecomponents.CoreUsers;
import fileio.ActionInputData;

/**
 * Utility functions for issuing various commands. Their purpose is to ease the core
 * implementations as well as to shorten the code.
 */
public class CoreCommands {

    /**
     * Checks if a video can be added to user's favourite list or not.
     */
    public int addFavorite(final String title,
                                   final CoreUsers user) {
        for (int j = 0; j < user.getFavoriteMovies().size(); j++) {
            if (title.equals(user.getFavoriteMovies().get(j))) {
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
     * Checks if a video can be added to user's history list or not.
     */
   public Integer addView(final String title, final CoreUsers user) {
       Integer views = 0;

       for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
           if (entry.getKey().equals(title)) {
               user.getHistory().put(title,
                       (user.getHistory().get(entry.getKey())) + 1);
               views = user.getHistory().get(entry.getKey());
               return  views;
           } else {
               user.getHistory().put(entry.getKey(), 1);
               views = 1;
           }
       }


       return views;
   }

    /**
     * Checks if a video is in a user's history of rated movies or not.
     */
   public int verifyhistoryRating(final String title, final Map<String, Integer> history) {

       for (Map.Entry<String, Integer> entry : history.entrySet()) {
           if (title.equals(entry.getKey())) {
               return 1;
           }
       }

       return 0;
   }

    /**
     * Adds a video to a user's rated movies.
     */
    public void addRatingmovie(final CoreUsers user, final ActionInputData action) {

        user.getRatedvids().put(action.getTitle(), 1);
    }

    /**
     * Check if a certain season can be rated by a user or not.
     */
    public int checkRatingseries(final CoreSeries series, final CoreUsers user,
                                 final double rating, final int seasonnumber) {


        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if (entry.getKey().equals(series.getTitle())) {
                for (int i = 1; i <= series.getNumberSeason(); i++) {
                    if (i == seasonnumber) {
                        return 0;
                    }
                }
            }
        }

        return 1;
    }

    /**
     * Determine the final rating of a series based on the ratings given per season.
     */
    public double determineratingSeries(final CoreSeries series) {

        double initialrating = 0;
        double secondrating;

        for (int i = 0; i < series.getNumberSeason(); i++) {
            secondrating = 0;
            for (int j = 0; j < series.getSeasons().get(i).getRatings().size(); j++) {
                secondrating =
                        secondrating + series.getSeasons().get(i).getRatings().get(j);
            }
            secondrating =
                    secondrating / series.getSeasons().get(i).getRatings().size();

            initialrating = initialrating + secondrating;

        }

        initialrating = initialrating / series.getNumberSeason();
        series.setRating(initialrating);

        return initialrating;
    }

    /**
     * Adds the final rating for a series in the rating list.
     */
    public double addRatingseries(final CoreSeries series, final CoreUsers user,
                                final double rating, final int seasonnumber) {

        double finalrating = 0;

        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if (entry.getKey().equals(series.getTitle())) {
                for (int i = 1; i <= series.getNumberSeason(); i++) {
                    if (i == seasonnumber) {
                        series.getSeasons().get(i - 1).getRatings().add(rating);
                        finalrating = rating;
                    }
                }
            }
        }

        return finalrating;
    }
}
