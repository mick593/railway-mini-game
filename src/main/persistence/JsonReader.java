package persistence;

import model.*;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads railwayGame from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game data from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RailwayGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRailwayGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game data from JSON object and returns it
    private RailwayGame parseRailwayGame(JSONObject jsonObject) {
        int score = jsonObject.getInt("score");
        int mapNum = jsonObject.getInt("map");
        RailwayGame game = new RailwayGame(mapNum);
        game.setScore(score);

        for (Train train : game.getTrains()) {
            train.delete();
        }

        modifySwitches(game, jsonObject);
        modifyTrain(game, jsonObject);
        modifyStation(game, jsonObject);
//        game.setSelectedTrainNext();
        return game;
    }

    // MODIFIES: game
    // EFFECTS: parses train from JSON object and adds them to trains
    private void modifyTrain(RailwayGame game, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("trains");
        game.getTrains().clear();
        int index = 0;

        for (Object json : jsonArray) {
            JSONObject nextTrain = (JSONObject) json;
            addTrain(game, nextTrain, index);
            index++;
        }
    }

    // MODIFIES: game
    // EFFECTS: parses train in to map object of game
    private void addTrain(RailwayGame game, JSONObject jsonObject, int index) {
        int size = jsonObject.getInt("size");
        int passenger = jsonObject.getInt("passenger");
        JSONArray pos = jsonObject.getJSONArray("position");
        int posX = (int) pos.get(0);
        int posY = (int) pos.get(1);
        Tile start = game.getMap().findTileByPosition(posX, posY);
        game.getTrains().add(new Train(size, start, index));
        boolean stop = jsonObject.getBoolean("stop");
        game.getTrains().get(index).setStop(stop);
        game.getTrains().get(index).setPassenger(passenger);
    }

    // MODIFIES: game
    // EFFECTS: parses switchTile from JSON object and adds them to switchTiles
    private void modifySwitches(RailwayGame game, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("switches");
        int index = 0;

        for (Object json : jsonArray) {
            JSONObject nextSwitch = (JSONObject) json;
            addSwitch(game, nextSwitch, index);
            index++;
        }
    }

    // MODIFIES: game
    // EFFECTS: parses switchTile in to map object of game
    private void addSwitch(RailwayGame game, JSONObject jsonObject, int index) {
        JSONArray pos = jsonObject.getJSONArray("position");
        int pointingIndex = jsonObject.getInt("PI");
        game.getSwitchTiles().get(index).setPointingIndex(pointingIndex);
    }

    // MODIFIES: game
    // EFFECTS: parses station from JSON object and adds them to stations
    private void modifyStation(RailwayGame game, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("stations");
        int index = 0;

        for (Object json : jsonArray) {
            JSONObject nextStation = (JSONObject) json;
            addStation(game, nextStation, index);
            index++;
        }
    }

    // MODIFIES: game
    // EFFECTS: parses station in to map object of game
    private void addStation(RailwayGame game, JSONObject jsonObject, int index) {
        JSONArray passengerList = jsonObject.getJSONArray("passenger");
        int indexOfList = 0;

        for (Object passenger : passengerList) {
            game.getStations().get(index).addPassenger(indexOfList, (int) passenger);
            indexOfList++;
        }
    }
}
