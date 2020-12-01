package corefunctions;

import corecomponents.*;
import fileio.Input;

import java.util.List;

/**
 * Utility functions for initializing the lists with data from the parsing of JSON files. Their
 * purpose is to ease the core implementations as well as to shorten the code. They also make
 * sure the provided functions from skel do not break.
 */
public class CoreLaunch {


    /**
     * Initialize the actor list.
     */
    public List<CoreActor> launchActors(final Input input, final List<CoreActor> actorlist) {

        for (int i = 0; i < input.getActors().size(); i++) {
            CoreActor actor =
                    new CoreActor(input.getActors().get(i).getName(),
                            input.getActors().get(i).getCareerDescription(),
                            input.getActors().get(i).getFilmography(),
                            input.getActors().get(i).getAwards());
            actorlist.add(actor);
        }

        return actorlist;
    }
    /**
     * Initialize the user list.
     */
    public List<CoreUsers> launchUsers(final Input input, final List<CoreUsers> userlist) {

        for (int i = 0; i < input.getUsers().size(); i++) {
            CoreUsers user =
                    new CoreUsers(input.getUsers().get(i).getUsername(),
                            input.getUsers().get(i).getSubscriptionType(),
                            input.getUsers().get(i).getHistory(),
                            input.getUsers().get(i).getFavoriteMovies());
            userlist.add(user);
        }

        return userlist;
    }

    /**
     * Initialize the command list.
     */
    public List<CoreAction> launchAction(final Input input, final List<CoreAction> actionlist) {

        for (int i = 0; i < input.getCommands().size(); i++) {
            CoreAction action =
                    new CoreAction(input.getCommands().get(i).getActionId(),
                            input.getCommands().get(i).getActionType(),
                            input.getCommands().get(i).getType(),
                            input.getCommands().get(i).getUsername(),
                            input.getCommands().get(i).getGenre());
            actionlist.add(action);
        }

        return actionlist;
    }

    /**
     * Initialize the movie list.
     */
    public List<CoreMovie> launchMovie(final Input input, final List<CoreMovie> movielist) {
        for (int i = 0; i < input.getMovies().size(); i++) {
            CoreMovie movie =
                    new CoreMovie(input.getMovies().get(i).getTitle(),
                            input.getMovies().get(i).getCast(),
                            input.getMovies().get(i).getGenres(),
                            input.getMovies().get(i).getYear(),
                            input.getMovies().get(i).getDuration());
            movielist.add(movie);
        }

        return movielist;
    }

    /**
     * Initialize the series list.
     */
    public List<CoreSeries> launchSeries(final Input input, final List<CoreSeries> serieslist) {

        for (int i = 0; i < input.getSerials().size(); i++) {
            CoreSeries series =
                    new CoreSeries(input.getSerials().get(i).getTitle(),
                            input.getSerials().get(i).getCast(),
                            input.getSerials().get(i).getGenres(),
                            input.getSerials().get(i).getNumberSeason(),
                            input.getSerials().get(i).getSeasons(),
                            input.getSerials().get(i).getYear());
            serieslist.add(series);
        }

        return serieslist;
    }
}
