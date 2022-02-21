package model;

import exception.CantSwitchWhileHasTrainException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;

//The special tile that switch train's route, this should built with at least size 2 of connectedTiles
public class SwitchTile extends Tile implements Writeable {
    private ArrayList<Tile> connectedTiles;
    private int pointingIndex;

    //MODIFIES: this
    //EFFECTS: create new SwitchTile
    public  SwitchTile(int px, int py) {
        super(px, py);
        connectedTiles = new ArrayList<>();
    }

    // This behave the same as super class but also will add info of connected tile into the list
    //REQUIRES: tile2 should not be null
    //MODIFIES: this, tile2
    //EFFECTS: connect tile with another next tile and add the next tile to list.
    @Override
    public void connectAfter(Tile tile2) {
        super.connectAfter(tile2);
        connectedTiles.add(0, tile2);
    }

//    //REQUIRES: Not having train in tile, connectedTiles size >= 2
//    //MODIFIES: this
//    //EFFECTS: switch the next tile of this tile into the next one from connectedTiles. Then, put the first tile
//    //          into last index of connectedTiles.
//    public void switchLine() {
//        super.connectAfter(connectedTiles.get(1));
//        connectedTiles.add(connectedTiles.get(0));
//        connectedTiles.remove(0);
//    }

    //MODIFIES: this
    //EFFECTS: switch the next tile of this tile into the next one from connectedTiles. Then, put the first tile
    //          into last index of connectedTiles.
    public void switchLine() throws CantSwitchWhileHasTrainException {
        if (this.hasTrain()) {
            throw new CantSwitchWhileHasTrainException();
        }
        pointingIndex += 1;
        pointingIndex %= connectedTiles.size();
        super.connectAfter(connectedTiles.get(pointingIndex));
    }

    //MODIFIES: this
    //EFFECTS: change the pointing index
    public boolean setPointingIndex(int pointingIndex) {
        while (this.pointingIndex != pointingIndex) {
            try {
                switchLine();
            } catch (CantSwitchWhileHasTrainException e) {
                return false;
            }
        }
        return true;
    }

    public int getPointingIndex() {
        return pointingIndex;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray coordinate = new JSONArray();
        coordinate.put(getPx());
        coordinate.put(getPy());

        JSONArray pos = new JSONArray();
        pos.put(getPx());
        pos.put(getPy());
        json.put("position", pos);
        json.put("PI", pointingIndex);

        return json;
    }
}



