package model;

import java.util.ArrayList;
import java.util.List;

//This class contains pre-made map. This class should has no bug as user won't modify anything here.
public class Maps {
    public static final int NUMBER_OF_MAP = 3;
    public int bounderX;
    public int bounderY;
    List<Tile> tiles;
    List<Train> trains;
    List<TrainStation> stations;
    List<SwitchTile> switches;

    //Create Map constructor
    //MODIFIES: this
    //EFFECTS: create the map and assign value to lists
    public Maps(int mapNum) {
        tiles = new ArrayList<>();
        trains = new ArrayList<>();
        stations = new ArrayList<>();
        switches = new ArrayList<>();
        switch (mapNum) {
            case 1:
                map1();
                break;
            case 2:
                map2();
                break;
            case 3:
                map3();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: create the pre-made map 1.
    public void map1() {
//        buildRight(0,0,3);
//        buildDown(3,0,3);
//        buildLeft(3,3,3);
//        buildUp(0,3,2);
//        findTileByPosition(0,1).connectAfter(findTileByPosition(0,0));
//        makeTileToStation(0,0,1);
//        buildTrain(0,0,1,3);
//
//        makeItCrossing();

        buildRight(0, 0, 3);
        buildRight(3, 0, 3);
        buildDown(3, 0, 3);
        buildDown(6, 0, 3);


        buildLeft(6, 3, 2);
        connectNextOfIntersect(4, 3, 3, 3);
        buildLeft(3, 3, 3);
        buildUp(0, 3, 2);
        findTileByPosition(0, 1).connectAfter(findTileByPosition(0, 0));

        makeTileToStation(4, 0, 0);
        makeTileToStation(0, 3, 1);

        buildTrain(0, 0, 0, 3);
        buildTrain(3, 3, 1, 3);
        makeTileToSwitch(3, 0, 4, 0, 3, 1);
        makeItCrossing();

        bounderX = 7;
        bounderY = 4;

//        buildRight(0,0,2);
//        buildDown(2,0,4);
//        buildRight(2,4,2);
//        buildUp(4,4,2);
//        buildLeft(4,2,4);
//        buildUp(0,2,1);
//        findTileByPosition(0,1).connectAfter(findTileByPosition(0,0));
//        buildTrain(0,0,1,3);
//        makeItCrossing();
    }

    //MODIFIES: this
    //EFFECTS: create the pre-made map 2.
    public void map2() {
        buildRight(4, 4, 4);
        buildDown(8, 4, 4);
        buildLeft(8, 8, 4);
        buildUp(4, 8, 3);
        findTileByPosition(4, 5).connectAfter(findTileByPosition(4, 4));

        buildLeft(4, 6, 4);
        buildUp(0, 6, 3);
        buildRight(0, 3, 3);
        buildDown(3, 3, 2);
        findTileByPosition(3, 5).connectAfter(findTileByPosition(4, 5));
        buildTrainMap2();

        buildRight(8, 6, 4);
        buildUp(12, 6, 6);
        buildLeft(12, 0, 6);
        buildDown(6, 0, 3);
        findTileByPosition(6, 3).connectAfter(findTileByPosition(6, 4));


        makeTileToSwitch(4, 6, 4, 5, 3, 6);
        makeTileToSwitch(8, 6, 8, 7, 9, 6);
        makeTileToStation(12, 4, 0);
        makeTileToStation(3, 4, 1);
        makeItCrossing();


        bounderX = 14;
        bounderY = 14;
    }

    private void buildTrainMap2() {
        buildTrain(4, 4, 1, 1);
        buildTrain(8, 4, 0, 1);
    }

    //MODIFIES: this
    //EFFECTS: create the pre-made map 3.
    public void map3() {
        buildRight(0, 0, 2);
        buildDown(2, 0, 4);
        buildRight(2, 4, 2);
        buildUp(4, 4, 2);
        buildLeft(4, 2, 4);
        buildUp(0, 2, 1);
        findTileByPosition(0, 1).connectAfter(findTileByPosition(0, 0));
        buildTrain(0, 0, 1, 3);

        makeTileToStation(3, 4, 0);
        makeTileToStation(1, 0, 1);
        makeItCrossing();

        bounderX = 8;
        bounderY = 6;
    }

    //MODIFIES: this
    //EFFECTS: add new Tile in px,py coordinate and add to tiles
    public Tile newTile(int px, int py) {
        Tile newT = new Tile(px, py);
        tiles.add(newT);
        return newT;

    }

    //MODIFIES: this
    //EFFECTS:  if the tile in fromPx, fromPy is null create a new instance in fromPx, fromPy. Then,
    //          build sequences of tile in UP direction for amount of tiles.
    public void buildUp(int fromPx, int fromPy, int amount) {
        Tile fromTile = findTileByPosition(fromPx, fromPy);

        if (fromTile == null) {
            fromTile = newTile(fromPx, fromPy);
        }

        Tile lastBuilt = fromTile;
        int buildingPy = fromPy - 1;

        for (int i = 0; i < amount; i++, buildingPy--) {
            Tile newlyBuilt = newTile(fromPx, buildingPy);
            lastBuilt.connectAfter(newlyBuilt);
            lastBuilt = lastBuilt.getNext();
        }
    }

    //MODIFIES: this
    //EFFECTS:  if the tile in fromPx, fromPy is null create a new instance in fromPx, fromPy. Then,
    //          build sequences of tile in Down direction for amount of tiles.
    public void buildDown(int fromPx, int fromPy, int amount) {
        Tile fromTile = findTileByPosition(fromPx, fromPy);

        if (fromTile == null) {
            fromTile = newTile(fromPx, fromPy);
        }

        Tile lastBuilt = fromTile;
        int buildingPy = fromPy + 1;

        for (int i = 0; i < amount; i++, buildingPy++) {
            Tile newlyBuilt = newTile(fromPx, buildingPy);
            lastBuilt.connectAfter(newlyBuilt);
            lastBuilt = lastBuilt.getNext();
        }
    }

    //MODIFIES: this
    //EFFECTS:  if the tile in fromPx, fromPy is null create a new instance in fromPx, fromPy. Then,
    //          build sequences of tile in RIGHT direction for amount of tiles.
    public void buildRight(int fromPx, int fromPy, int amount) {
        Tile fromTile = findTileByPosition(fromPx, fromPy);

        if (fromTile == null) {
            fromTile = newTile(fromPx, fromPy);
        }

        Tile lastBuilt = fromTile;
        int buildingPx = fromPx + 1;

        for (int i = 0; i < amount; i++, buildingPx++) {
            Tile newlyBuilt = newTile(buildingPx, fromPy);
            lastBuilt.connectAfter(newlyBuilt);
            lastBuilt = lastBuilt.getNext();
        }
    }

    //MODIFIES: this
    //EFFECTS:  if the tile in fromPx, fromPy is null create a new instance in fromPx, fromPy. Then,
    //          build sequences of tile in Left direction for amount of tiles.
    public void buildLeft(int fromPx, int fromPy, int amount) {
        Tile fromTile = findTileByPosition(fromPx, fromPy);

        if (fromTile == null) {
            fromTile = newTile(fromPx, fromPy);
        }

        Tile lastBuilt = fromTile;
        int buildingPx = fromPx - 1;

        for (int i = 0; i < amount; i++, buildingPx--) {
            Tile newlyBuilt = newTile(buildingPx, fromPy);
            lastBuilt.connectAfter(newlyBuilt);
            lastBuilt = lastBuilt.getNext();
        }
    }

//    public void buildRightUp(int fromPx, int fromPy, int toPx, int toPy) {
//        Tile fromTile = findTileByPosition(fromPx,fromPy);
//        buildRight(fromPx,fromPy, Math.abs(fromPx - toPx));
//        buildUp(toPx,fromPy, Math.abs(fromPy - toPy));
//    }

    //MODIFIES: this
    //EFFECTS:  find the tile by position x,y and return it
    public Tile findTileByPosition(int px, int py) {
        for (Tile tile : tiles) {
            if (tile.isPosition(px, py)) {
                return tile;
            }
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: turn the tile in x,y into a station
    public void makeTileToStation(int px, int py, int stationNum) {
        Tile target = findTileByPosition(px, py);
        Tile prev = target.getsPrev();
        Tile next = target.getNext();

        TrainStation station = new TrainStation(px, py, stationNum);
        station.connectBefore(prev);
        station.connectAfter(next);

        //station.addPassenger(1,5);
        tiles.remove(target);
        tiles.add(station);
        stations.add(station);
    }

    //MODIFIES: this
    //EFFECTS:  build train n size in the tile x,y and assign train number
    public void buildTrain(int px, int py, int trainNum, int size) {
        Train train = new Train(size, findTileByPosition(px, py), trainNum);
        trains.add(train);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<TrainStation> getStations() {
        return stations;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public List<SwitchTile> getSwitches() {
        return switches;
    }

    //MODIFIES: this
    //EFFECTS:  if there any crossing(2 overlap tile in the same x,y) then makeItCrossing
    public void makeItCrossing() {

        for (Tile tile : tiles) {

            int px = tile.getPx();
            int py = tile.getPy();

            for (Tile tile2 : tiles) {

                if (tile2.isPosition(px, py) && tile != tile2) {
                    Tile.makeItCrossing(tile, tile2);
                }

            }
        }
    }

    //MODIFIES: this
    //EFFECTS:  turn tile into switch tile
    public void makeTileToSwitch(int px, int py, int secondPx, int secondPy, int thirdPx, int thirdPy) {
        Tile target = findTileByPosition(px, py);
        Tile secondTarget = findTileByPosition(secondPx, secondPy);
        Tile thirdTarget = findTileByPosition(thirdPx, thirdPy);
        Tile prev = target.getsPrev();

        SwitchTile switchTile = new SwitchTile(px, py);
        switchTile.connectBefore(prev);

        switchTile.connectAfter(secondTarget);
        switchTile.connectAfter(thirdTarget);

        tiles.remove(target);
        tiles.add(switchTile);
        switches.add(switchTile);
    }

    //MODIFIES: this
    //EFFECTS:  clear every variable in Map
    public void clear() {
        trains.clear();
        stations.clear();
        tiles.clear();
        switches.clear();
    }

    //MODIFIES: this
    //EFFECTS:  connect the tile to other tile that already has prevTile
    public void connectNextOfIntersect(int px, int py, int toPx, int toPy) {
        Tile target = findTileByPosition(px, py);
        Tile secondTarget = findTileByPosition(toPx, toPy);
        target.setNext(secondTarget);
    }

//    //MODIFIES: this
//    //EFFECTS: clear train for reload
//    public void clearTrainOut() {
//        f
//    }
}
