package corefunctions;

import corecomponents.CoreMovie;
import corecomponents.CoreSeries;
import java.util.Map;
import corecomponents.CoreUsers;
import fileio.ActionInputData;

public class CoreCommands {

    /**
     *
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
     *
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
     *
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
     *
     */
   public int checkratingAndhistory(final CoreRecommendation recommendation,
                                    final ActionInputData action, final CoreUsers user) {

       if (verifyhistoryRating(action.getTitle(), user.getHistory()) == 0
               && recommendation.checkifinHistory(action.getTitle(), user.getHistory()) == 1) {

           return 1;
       }

       return 0;
   }

    /**
     *
     */
    public int checkRatingmovie(final CoreMovie movie, final CoreUsers user,
                                 final double rating) {

        for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
            if (entry.getKey().equals(movie.getTitle()) && movie.getRating() == 0) {
                return 1;
            }
            if (entry.getKey().equals(movie.getTitle()) && movie.getRating() != 0) {
                return 2;
            }
        }
        return 0;
    }

    /**
     *
     */
    public void addRatingmovie(final CoreUsers user, final ActionInputData action) {

        user.getRatedvids().put(action.getTitle(), 1);
    }

    /**
     *
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
     *
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
     *
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
