package main;

import actor.ActorsAwards;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import corecomponents.*;
import corefunctions.CoreCommands;
import corefunctions.CoreLaunch;
import corefunctions.CoreQueries;
import corefunctions.CoreRecommendation;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        /**
         * Lists that contain data retreived from parsing the JSON files.
         */
        List<CoreActor> actorlist = new ArrayList<>();
        List<CoreUsers> userlist = new ArrayList<>();
        List<CoreAction> actionlist = new ArrayList<>();
        List<CoreMovie> movielist = new ArrayList<>();
        List<CoreSeries> serieslist = new ArrayList<>();

        /**
         * Objects used for calling utility functions from different classes.
         */
        CoreCommands commands = new CoreCommands();
        CoreQueries queries = new CoreQueries();
        CoreRecommendation recommendation = new CoreRecommendation();
        CoreLaunch launch = new CoreLaunch();

        /**
         * Initialization of data lists.
         */
        userlist = launch.launchUsers(input, userlist);
        actorlist = launch.launchActors(input, actorlist);
        actionlist = launch.launchAction(input, actionlist);
        movielist = launch.launchMovie(input, movielist);
        serieslist = launch.launchSeries(input, serieslist);

        /**
         * The beginning of iterating through actions.
         */
        for (int i = 0; i < input.getCommands().size(); i++) {

            /** Check if user issued a command. **/
            if (input.getCommands().get(i).getActionType().equals("command")) {

                /** Check if the desired command is favourite. **/
                if (input.getCommands().get(i).getType().equals("favorite")) {
                    for (int j = 0; j < userlist.size(); j++) {

                        /** Check if the video can be added or not in the favourite list. **/
                        if (userlist.get(j).getUsername().equals(input.
                                getCommands().get(i).getUsername())) {

                            if (commands.addFavorite(input.getCommands().
                                    get(i).getTitle(), userlist.get(j)) == 1) {

                                JSONObject object =
                                        fileWriter.writeFile(input.getCommands()
                                                        .get(i).getActionId(),
                                                "field", "success -> "
                                                        + input.getCommands().
                                                        get(i).getTitle()
                                                        + " was added as favourite");
                                arrayResult.add(object);
                            } else if (commands.addFavorite(input.getCommands().get(i).getTitle(),
                                    userlist.get(j)) == 0) {
                                    JSONObject object =
                                            fileWriter.writeFile(input.getCommands()
                                                            .get(i).getActionId(),
                                                    "field", "error -> "
                                                            + input.getCommands().get(i).getTitle()
                                                            + " is already in favourite list");
                                    arrayResult.add(object);
                                    /** Also check if the user had not seen that video. **/
                                } else {
                                    JSONObject object =
                                            fileWriter.writeFile(input.getCommands()
                                                            .get(i).getActionId(),
                                                    "field", "error -> "
                                                            + input.getCommands().get(i).getTitle()
                                                            + " is not seen");
                                    arrayResult.add(object);
                                }
                        }
                    }

                }
                /** Check if the desired command is view. **/
                if (actionlist.get(i).getType().equals("view")) {
                    for (int j = 0; j < userlist.size(); j++) {
                        if (userlist.get(j).getUsername().equals(input
                                .getCommands().get(i).getUsername())) {

                            /** The function addView both checks if the video was seen or not and
                             *  increments the view count accordingly.
                             *  **/
                            JSONObject object =
                                    fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "field", "success -> "
                                                    + input.getCommands().get(i).getTitle()
                                                    + " was viewed with total views of "
                                                    + commands.addView(input.getCommands()
                                                    .get(i).getTitle(), userlist.get(j)));

                            arrayResult.add(object);
                        }
                    }

                }
                /** Check if the desired command is rating. **/
                if (actionlist.get(i).getType().equals("rating")) {
                    for (int j = 0; j < userlist.size(); j++) {

                        if (userlist.get(j).getUsername().equals(input
                                .getCommands().get(i).getUsername())) {

                            for (int k = 0; k < movielist.size(); k++) {
                                if (movielist.get(k).getTitle().equals(input
                                        .getCommands().get(i).getTitle())) {

                                    /** Check if the movie is both in the user's history as well
                                     * as if it's not in the history of rated movies. **/
                                    if (recommendation.checkifinHistory(input.getCommands()
                                            .get(i).getTitle(),
                                            userlist.get(j).getHistory()) == 1) {
                                        if (commands.verifyhistoryRating(input.getCommands()
                                                .get(i).getTitle(),
                                                userlist.get(j).getRatedvids()) == 0) {

                                            commands.addRatingmovie(userlist
                                                    .get(j), input.getCommands().get(i));

                                            JSONObject object =
                                                    fileWriter.writeFile(input
                                                                    .getCommands()
                                                                    .get(i).getActionId(),
                                                            "field", "success -> "
                                                                    + input.getCommands().
                                                                    get(i).getTitle()
                                                                    + " was rated with "
                                                                    + input.getCommands().get(i)
                                                                    .getGrade()
                                                                    + " by "
                                                                    + userlist
                                                                    .get(j).getUsername());
                                            arrayResult.add(object);

                                            /** Special case if the movie was already rated. **/
                                        } else if (commands.verifyhistoryRating(input.getCommands()
                                                        .get(i).getTitle(),
                                                userlist.get(j).getRatedvids()) == 1) {

                                            JSONObject object =
                                                    fileWriter.writeFile(input
                                                                    .getCommands()
                                                                    .get(i).getActionId(),
                                                            "field", "error -> "
                                                                    + input.getCommands().
                                                                    get(i).getTitle()
                                                                    + " has been already rated");
                                            arrayResult.add(object);

                                        }
                                        /** Also check if the movie is not in the user's history
                                         * at all.
                                         */
                                    } else {
                                        JSONObject object =
                                                fileWriter.writeFile(input
                                                                .getCommands().get(i).getActionId(),
                                                        "field", "error -> "
                                                                + input.getCommands()
                                                                .get(i).getTitle()
                                                                + " is not seen");
                                        arrayResult.add(object);

                                    }
                                }
                            }
                            for (int k = 0; k < serieslist.size(); k++) {

                                if (serieslist.get(k).getTitle().equals(input
                                        .getCommands().get(i).getTitle())) {

                                    /** Check if the series is not in the user's history
                                     * at all. **/
                                    if (commands.checkRatingseries(serieslist
                                                    .get(k), userlist.get(j),
                                            input.getCommands().get(i).getGrade(),
                                            input.getCommands().get(i).
                                                    getSeasonNumber()) == 1) {

                                        JSONObject object =
                                                fileWriter.writeFile(input
                                                                .getCommands().get(i).getActionId(),
                                                        "field", "error -> "
                                                                + input.getCommands()
                                                                .get(i).getTitle()
                                                                + " is not seen");
                                        arrayResult.add(object);

                                        /** If the series is in history, add the rating. **/
                                    } else {

                                        JSONObject object =
                                                fileWriter.writeFile(input
                                                                .getCommands().get(i).getActionId(),
                                                        "field", "success -> "
                                                                + input.getCommands().get(i)
                                                                .getTitle()
                                                                + " was rated with "
                                                                + commands
                                                                .addRatingseries(serieslist
                                                                        .get(k), userlist.get(j),
                                                                input.getCommands()
                                                                        .get(i).getGrade(),
                                                                input.getCommands().get(i).
                                                                        getSeasonNumber())
                                                                + " "
                                                                + "by "
                                                                + userlist.get(j).getUsername());
                                        arrayResult.add(object);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            /** Check if the desired command is query. **/
            if (input.getCommands().get(i).getActionType().equals("query")) {
                /** Check if the desired command is actors. **/
                if (input.getCommands().get(i).getObjectType().equals("actors")) {
                    /** Check if the desired command is average. **/
                    if (input.getCommands().get(i).getCriteria().equals("average")) {

                    }
                    /** Check if the desired command is awards. **/
                    if (input.getCommands().get(i).getCriteria().equals("awards")) {

                        List<ActorsAwards> awards = new ArrayList<>();

                        if (awards.size() != 0) {
                            for (int j = 0; j < actorlist.size(); j++) {
                                for (Map.Entry<ActorsAwards, Integer> entry
                                        : actorlist.get(j).getAwards().entrySet()) {
                                    for (int k = 0; k < input.getCommands()
                                            .get(i).getFilters().size(); k++) {
                                        awards.add(entry.getKey());
                                    }
                                }
                            }
                        }

                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "field", "Query result: "
                                                + awards);
                        arrayResult.add(object);


                    }
                    /** Check if the desired command is filter_description. **/
                    if (input.getCommands().get(i).getCriteria().equals("filter_description")) {

                        List<String> filter = new ArrayList<>();

                        for (int j = 0; j < actorlist.size(); j++) {
                            for (int k = 0; k < input.getCommands()
                                    .get(i).getFilters().size(); k++) {
                                if (actorlist.get(j).getCareerDescription().
                                        equals(input.getCommands().get(i).getFilters().get(k))) {
                                    filter.add(actorlist.get(j).getCareerDescription());
                                }
                            }
                        }

                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "field", "Query result: "
                                                + filter);
                        arrayResult.add(object);
                    }
                }
                /** Check if the desired command is movies. **/
                if (input.getCommands().get(i).getObjectType().equals("movies")) {
                    /** Check if the desired command is ratings. **/
                    if (input.getCommands().get(i).getCriteria().equals("ratings")) {

                    }
                    /** Check if the desired command is favourite. **/
                    if (input.getCommands().get(i).getCriteria().equals("favorite")) {

                        List<String> queryfavourite =
                                queries.executeFavouritemovie(input.getCommands().get(i),
                                        movielist, userlist);
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "field", "Query result: " + queryfavourite);

                        arrayResult.add(object);
                    }
                    /** Check if the desired command is longest. **/
                    if (input.getCommands().get(i).getCriteria().equals("longest")) {

                            List<String> longestmovies =
                                    queries.executeLongestmovies(input.getCommands().get(i),
                                            movielist);

                            JSONObject object =
                                    fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            "field", "Query result: "
                                                    + longestmovies);
                            arrayResult.add(object);

                        /** Check if the desired command is shows. **/
                        if (input.getCommands().get(i).getObjectType().equals("shows")) {


                        }

                    }
                    /** Check if the desired command is most_viewed. **/
                    if (input.getCommands().get(i).getCriteria().equals("most_viewed")) {

                    }
                }
                /** Check if the desired command is shows. **/
                if (input.getCommands().get(i).getObjectType().equals("shows")) {
                    /** Check if the desired command is ratings. **/
                    if (input.getCommands().get(i).getCriteria().equals("ratings")) {
                        int test;
                    }
                    /** Check if the desired command is favourite. **/
                    if (input.getCommands().get(i).getCriteria().equals("favorite")) {

                        List<String> favouriteseries =
                                queries.executeFavouriteseries(input.getCommands().get(i),
                                        serieslist, userlist);

                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "field", "Query result: " + favouriteseries);

                        arrayResult.add(object);

                    }
                    /** Check if the desired command is longest. **/
                    if (input.getCommands().get(i).getCriteria().equals("longest")) {

                        List<String> longestseries =
                                queries.executeLongestseries(input.getCommands().get(i),
                                        serieslist);


                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        "field", "Query result: "
                                                + longestseries);
                        arrayResult.add(object);

                    }
                    /** Check if the desired command is most_viewed. **/
                    if (input.getCommands().get(i).getCriteria().equals("most_viewed")) {

                    }
                }
                /** Check if the desired command is users. **/
                if (input.getCommands().get(i).getObjectType().equals("users")) {
                    /** Check if the desired command is num_ratings. **/
                    if (input.getCommands().get(i).getCriteria().equals("num_ratings")) {

                    }
                }


            }
            /** Check if the desired command is recommendation. **/
            if (input.getCommands().get(i).getActionType().equals("recommendation")) {
                /** Check if the desired command is standard. **/
                if (input.getCommands().get(i).getType().equals("standard")) {

                    int ok = 0;
                    String recommendtitle = new String();

                    for (int j = 0; j < userlist.size(); j++) {
                        if (userlist.get(j).getUsername().equals(input.getCommands()
                                .get(i).getUsername())) {
                            for (int k = 0; k < movielist.size(); k++) {
                                if (recommendation.checkifinHistory(movielist.get(k).getTitle(),
                                        userlist.get(j).getHistory()) == 0 && ok == 0) {
                                    recommendtitle = movielist.get(k).getTitle();
                                    ok = 1;
                                }
                            }
                        }
                    }

                    JSONObject object =
                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    "field", "StandardRecommendation result: "
                                            + recommendtitle);
                    arrayResult.add(object);
                }

                /** Check if the desired command is search. **/
                if (input.getCommands().get(i).getType().equals("search")) {

                    List<String> titles =
                            recommendation.searchRecommendation(input.getCommands().get(i),
                                    userlist, movielist, serieslist);

                    if (titles.size() != 0) {
                        JSONObject object =
                                fileWriter
                                        .writeFile(input.getCommands().get(i).getActionId(),
                                                "field",
                                                "SearchRecommendation result: "
                                                        + titles);

                        arrayResult.add(object);
                    } else {

                        JSONObject object =
                                fileWriter.
                                        writeFile(input.getCommands().get(i).getActionId(),
                                                "field",
                                                "SearchRecommendation cannot be applied!");

                        arrayResult.add(object);

                    }
                }
            }
        }



        fileWriter.closeJSON(arrayResult);
    }
}
