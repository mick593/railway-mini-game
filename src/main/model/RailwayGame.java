package model;

import exception.CantSwitchWhileHasTrainException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.List;
import java.util.Random;

public class RailwayGame implements Writeable {
    public static final int SCORE_TO_WIN = 100;

//    private List<Tile> tiles;
//    private List<SwitchTile> switchTiles;
//    private List<TrainStation> stations;
//    private List<Train> trains;
    private boolean isGameOver;
    private int score;
    private int mapNum;
//    private int mapNumber;
    private Train selectedTrain;
    private SwitchTile selectedSwitchTile;
    private Maps map;

    public RailwayGame(int mapNum) {
        this.mapNum = mapNum;
        map = new Maps(mapNum);
        if (getTrains().size() > 0) {
            selectedTrain = getTrains().get(0);
        }

        if (getSwitchTiles().size() > 0) {
            selectedSwitchTile = getSwitchTiles().get(0);
        }
//        tiles = map.getTiles();
//        switchTiles = map.getSwitches();
//        stations = map.getStations();
//        trains = map.getTrains();
    }

    public void setSelectedTrainNext() {
        int indexOfNext = (getTrains().indexOf(selectedTrain) + 1) % getTrains().size();
        selectedTrain = getTrains().get(indexOfNext);
    }

    public void setSelectedSwitchTileNext() {
        int indexOfNext = (getSwitchTiles().indexOf(selectedSwitchTile) + 1) % getSwitchTiles().size();
        selectedSwitchTile = getSwitchTiles().get(indexOfNext);
    }

    //MODIFIES: this
    //EFFECTS: stop the selected train
    public void stopSelected() {
        selectedTrain.switchStopState();
    }

    //MODIFIES: this
    //EFFECTS: switchLine the selected switchTile if possible. Otherwise, do nothing and print error
    public void switchSelected() {
        try {
            selectedSwitchTile.switchLine();
        } catch (CantSwitchWhileHasTrainException e) {
            e.printStackTrace();
        }
    }

    public SwitchTile getSelectedSwitchTile() {
        return selectedSwitchTile;
    }

    //MODIFIES: this
    //EFFECTS: update the state of the game
    public void update() {
        isCrash();
        checkDropPassenger();
        checkPickPassenger();
        moveTrains();
        generatePassenger();

    }

    //MODIFIES: this
    //EFFECTS: add the passenger waiting in each station
    public void generatePassenger() {
        for (TrainStation station : getStations()) {
            if (!station.hasTrain()) {
                Random rand  = new Random();
                int passNum = rand.nextInt(1) + 1;
                int stationNum = rand.nextInt(getStations().size());
                if (station.getStationNum() != stationNum) {
                    station.addPassenger(stationNum, passNum);
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: move all train in trains if it is not stopped
    public void moveTrains() {
        for (Train train : getTrains()) {
            if (!train.isStop()) {
                train.move();
            }
        }
    }

    //REQUIRES: index in bound
    //MODIFIES: this
    //EFFECTS: call switchStopState on the train we want from trains
    public void switchStopperThatTrain(int index) {
        getTrains().get(index).switchStopState();
    }

    //REQUIRES: index in bound
    //MODIFIES: this
    //EFFECTS: call switch line on the switchTile we want from trains
    public void switchLineSwitchTile(int index) throws CantSwitchWhileHasTrainException {
        getSwitchTiles().get(index).switchLine();
    }

    //MODIFIES: this
    //EFFECTS: for each train if there is a station nearby then if that stationNum is match the trainNum and
    //         train has passenger on train, then stop the train, drop passenger, and add to score.
    private void checkDropPassenger() {
        for (Train train : getTrains()) {
            score += train.dropPassenger();
        }
    }

    //MODIFIES: this
    //EFFECTS: for each train if there is a station nearby and have passenger waiting then stop and
    //         pickup the passenger in the amount of not more than train capacity.
    public void checkPickPassenger() {
        for (Train train : getTrains()) {
            train.pickPassenger();
        }
    }

    public boolean isCrash() {
        for (Train train : getTrains()) {
            if (train.isCrash()) {
                return true;
            }
        }
        return false;
    }

    public boolean isWin() {
        return score >= SCORE_TO_WIN;
    }

    public int getScore() {
        return score;
    }

    public List<SwitchTile> getSwitchTiles() {
        return map.switches;
    }

    public List<Train> getTrains() {
        return map.trains;
    }

    public List<TrainStation> getStations() {
        return map.stations;
    }

    public List<Tile> getTiles() {
        return map.tiles;
    }

    //EFFECTS: put score, mapNum, trains, switches, and stations into JSon
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        //json.put("map", mapNumber);
        json.put("score", score);
        json.put("map", mapNum);
        json.put("trains", trainsToJson());
        json.put("switches", switchesToJson());
        json.put("stations", stationsToJson());


        return json;
    }

    // EFFECTS: returns number of train to json array
    private JSONArray trainsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Train train : getTrains()) {
            jsonArray.put(train.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns number of train to json array
    private JSONArray stationsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TrainStation station : getStations()) {
            jsonArray.put(station.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns number of train to json array
    private JSONArray switchesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SwitchTile switchTile : getSwitchTiles()) {
            jsonArray.put(switchTile.toJson());
        }

        return jsonArray;
    }

    public Maps getMap() {
        return map;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Train getSelectedTrain() {
        return selectedTrain;
    }
}
