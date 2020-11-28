package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import corecomponents.*;
import corefunctions.CoreCommands;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

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

        List<CoreActor> actorlist = new ArrayList<>();

        List<CoreUsers> userlist = new ArrayList<>();

        List<CoreAction> actionlist = new ArrayList<>();

        List<CoreMovie> movielist = new ArrayList<>();

        List<CoreSeries> serieslist = new ArrayList<>();

        CoreCommands commands = new CoreCommands();

        /*JSONObject object = fileWriter.writeFile(1, "field", "error -> " +
                "Sherlock: The Final Problem is already in favourite list");
        arrayResult.add(object);*/

        for (int i = 0; i < input.getActors().size(); i++) {
            CoreActor actor =
                    new CoreActor(input.getActors().get(i).getName(),
                            input.getActors().get(i).getCareerDescription(),
                            input.getActors().get(i).getFilmography(),
                            input.getActors().get(i).getAwards());
            actorlist.add(actor);
        }

        for (int i = 0; i < input.getUsers().size(); i++) {
            CoreUsers user =
                    new CoreUsers(input.getUsers().get(i).getUsername(),
                            input.getUsers().get(i).getSubscriptionType(),
                            input.getUsers().get(i).getHistory(),
                            input.getUsers().get(i).getFavoriteMovies());
            userlist.add(user);
        }

        for (int i = 0; i < input.getCommands().size(); i++) {
            CoreAction action =
                    new CoreAction(input.getCommands().get(i).getActionId(),
                            input.getCommands().get(i).getActionType(),
                            input.getCommands().get(i).getType(),
                            input.getCommands().get(i).getUsername(),
                            input.getCommands().get(i).getGenre());
            actionlist.add(action);
        }

        for (int i = 0; i < input.getMovies().size(); i++) {
            CoreMovie movie =
                    new CoreMovie(input.getMovies().get(i).getTitle(),
                            input.getMovies().get(i).getCast(),
                            input.getMovies().get(i).getGenres(),
                            input.getMovies().get(i).getYear(),
                            input.getMovies().get(i).getDuration());
                    movielist.add(movie);
        }

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

        for (int i = 0; i < input.getCommands().size(); i++) {
            if (input.getCommands().get(i).getActionType().equals("command")) {
                if (input.getCommands().get(i).getType().equals("favorite")) {
                    for (int j = 0; j < userlist.size(); j++) {
                        if(userlist.get(j).getUsername().equals(input.getCommands().get(i).getUsername())) {

                            if (commands.addFavorite(input.getCommands().get(i).getTitle(), userlist.get(j)) == 1) {
                                JSONObject object =
                                        fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                                "field", "success -> " +
                                                        input.getCommands().get(i).getTitle() + " was added as favourite");
                                arrayResult.add(object);
                            }

                            else if (commands.addFavorite(input.getCommands().get(i).getTitle()
                                        , userlist.get(j)) == 0) {
                                    JSONObject object =
                                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                                    "field", "error -> " +
                                                            input.getCommands().get(i).getTitle() + " is " +
                                                            "already in " +
                                                            "favourite" +
                                                            " list");
                                    arrayResult.add(object);
                                } else {
                                    JSONObject object =
                                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                                    "field", "error -> " +
                                                            input.getCommands().get(i).getTitle() + " is " +
                                                            "not " +
                                                            "seen");
                                    arrayResult.add(object);
                                }

                        }
                    }

                }
                if (actionlist.get(i).getType().equals("view")) {

                }
                if (actionlist.get(i).getType() == "rating") {

                }
            }
            if (actionlist.get(i).getActionType() == "query") {

            }
            if (actionlist.get(i).getActionType() == "recommendation") {

            }
        }




        fileWriter.closeJSON(arrayResult);
    }
}
