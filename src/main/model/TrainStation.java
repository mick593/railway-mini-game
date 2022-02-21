package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

//The tile that store passenger for train to drop of pick
public class TrainStation extends Tile implements Writeable {
    //public static int droppedPassenger;
    //private static final int PASSENGER_PER_ADD = 1;
    public static final int MAX_PASSENGER = RailwayGame.SCORE_TO_WIN / 3;
    private static final int MAX_STATION = 10;

    private int stationNum;
    private int[] destinationNumList;

    //REQUIRES : stationNum > 0, TrainStation will located more than Train size from each other
    //EFFECTS : create new trainStation
    public TrainStation(int px, int py, int stationNum) {
        super(px, py);
        destinationNumList = new int[MAX_STATION];
        this.stationNum = stationNum;
    }

    //REQUIRES : stationNUm > 0
    //MODIFIES: this
    //EFFECTS: add passenger number that wait for the train that go to stationNum station
    //public void addPassenger(int stationNum) {
//        destinationNumList[stationNum] += PASSENGER_PER_ADD;
//    }

    //REQUIRES : stationNUm > 0
    //MODIFIES: this
    //EFFECTS: add passenger number that wait for the train that go to stationNum station if it not exceed the maximum
    public void addPassenger(int stationNum, int vol) {
        if (destinationNumList[stationNum] + vol <= MAX_PASSENGER) {
            destinationNumList[stationNum] += vol;
        }
    }

    //REQUIRES : stationNum > 0, there is a cargo on this tile
    //MODIFIES : this
    //EFFECTS : let the passenger left station by the number of space permitted
    public int deployPassenger(int stationNum) {
        int trainCap = getCargo().getTrain().getCapacity();
        int person;
        if (trainCap >= destinationNumList[stationNum]) {
            person = destinationNumList[stationNum];
            destinationNumList[stationNum] = 0;
        } else {
            person = trainCap;
            destinationNumList[stationNum] -= trainCap;
        }
        return person;
    }

//    public void deployPassenger(int stationNum, int vol) {
//        destinationNumList[stationNum] -= vol;
//    }

    public int getStationNum() {
        return stationNum;
    }

    public int getPassenger(int statNum) {
        return destinationNumList[statNum];
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("station", stationNum);
        json.put("passenger", destinationNumListToJson());

        JSONArray pos = new JSONArray();
        pos.put(getPx());
        pos.put(getPy());
        json.put("position", pos);

        return json;
    }

    // EFFECTS: returns number of passenger to json
    private JSONArray destinationNumListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int peopleInList : destinationNumList) {
            jsonArray.put(peopleInList);
        }

        return jsonArray;
    }

}
