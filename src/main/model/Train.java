package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWriter;
import persistence.Writeable;

import java.util.ArrayList;

//the collection of cargo form a train and manipulate passenger
public class Train implements Writeable {
    protected static final int MAX_PASSENGER = RailwayGame.SCORE_TO_WIN / 5;
    //private static int trainCount;

    private int size;
    private ArrayList<Cargo> cargos;
    private boolean stop;
    private int passenger;
    private int trainNum;
    private boolean isCrash;

    //REQUIRES: size > 0 and startTile is not null and not contain other train
    //EFFECTS : create new train by size and front cargo location
    public Train(int size, Tile startTile, int trainNum) {
        cargos = new ArrayList<>();
        this.size = size;
        this.trainNum = trainNum;
        passenger = 0;
        stop = true;
        for (int i = 0; i < size; i++) {
            cargos.add(0, new Cargo(startTile, this));
            startTile = startTile.getNext();
        }
    }

    //REQUIRES: train is not stopped, the next tile is not null
    //MODIFIES: this
    //EFFECTS : move each cargo to the next tile if this train is not stop
    public void move() {

        if (getFrontTile().getNext().hasTrain()) {
            setCrash(true);
        }
        for (Cargo cargo : cargos) {
            cargo.move();
        }
    }

    //add cargo behind the train
    //MODIFIES: this
    //EFFECTS: add the cargo behind cargo list. If there a train or null at addTile, return false and not add
    //        Otherwise, add the cargo and return true
    public boolean addCargoBehind() {
        Tile lastTile = cargos.get(cargos.size() - 1).getTile();
        Tile addTile = lastTile.getsPrev();
        if (addTile != null && !addTile.hasTrain()) {
            size++;
            cargos.add(new Cargo(addTile, this));
            return true;
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS : If running, stop this train from moving. If stopping, change stop to false.
    public void switchStopState() {
        stop = !stop;
    }

    //REQUIRES: pickup <= getCapacity(), isStop() == true
    //MODIFIES: this
    //EFFECT : add the passenger to this train
    public boolean pickPassenger() {
        TrainStation station = scanStation();
        if (station == null) {
            return false;
        }
        if (station.getPassenger(trainNum) > 0) {
            int picked = station.deployPassenger(trainNum);
            passenger += picked;
            return true;
        }
        return false;
    }

//    //MODIFIES: this
//    //EFFECTS: add passenger to train
//    public int pickPassenger(int demandVol) {
//        int add;
//        if (demandVol >= getCapacity()) {
//            add = getCapacity();
//            passenger += getCapacity();
//        } else {
//            add = demandVol;
//            passenger += demandVol;
//        }
//        return add;
//    }

    //MODIFIES : this, isStop() == true
    //EFFECT : drop all the passenger when arrived destination
    public int dropPassenger() {
        TrainStation station = scanStation();
        if (station == null) {
            return 0;
        } else if (station.getStationNum() == trainNum) {
            int dropped = passenger;
            passenger = 0;
            return dropped;
        }
        return 0;
    }

//    //MODIFIES : this, isStop() == true
//    //EFFECT : drop all the passenger when arrived destination
//    public int dropPassenger() {
//        int dropped = passenger;
//        passenger = 0;
//        return dropped;
//    }

    //EFFECTS : return the station nearby or null if not found
    public TrainStation scanStation() {
        for (Cargo cargo : cargos) {
            Tile tile = cargo.getTile();
            if (tile instanceof TrainStation) {
                return (TrainStation) tile;
            }
        }
        return null;
    }

    //EFFECTS : return the boolean if train is stop
    public boolean isStop() {
        return stop;
    }

    //EFFECTS : return how many seat left
    public int getCapacity() {
        return MAX_PASSENGER - passenger;
    }

    //EFFECTS : return how many passenger in train
    public int getPassenger() {
        return passenger;
    }

    //EFFECTS : return how many cargo
    public int getSize() {
        return size;
    }

    //EFFECTS : return the front tile
    public Tile getFrontTile() {
        return cargos.get(0).getTile();
    }

    //EFFECTS : return this train Number
    public int getTrainNum() {
        return trainNum;
    }

    public void setStop(boolean isStop) {
        this.stop = isStop;
    }

    @Override
    public JSONObject toJson() {
        Tile lastTileOfTrain = cargos.get(cargos.size() - 1).getTile();
        JSONObject json = new JSONObject();
        JSONArray pos = new JSONArray();
        pos.put(lastTileOfTrain.getPx());
        pos.put(lastTileOfTrain.getPy());
        json.put("position", pos);
        json.put("size", getSize());
        json.put("stop", isStop());
        json.put("passenger", getPassenger());

        return json;
    }

    public void setCrash(boolean crash) {
        isCrash = crash;
    }

    public boolean isCrash() {
        return isCrash;
    }

    //MODIFIES: this
    //EFFECTS: delete cart from every tile
    public void delete() {
        for (Cargo cart : cargos) {
            cart.getTile().deleteCargo();
        }
    }

    public ArrayList<Cargo> getCargos() {
        return cargos;
    }

    //MODIFIES: this
    //EFFECTS: set passenger of this train
    public void setPassenger(int passenger) {
        this.passenger = passenger;
    }
}
