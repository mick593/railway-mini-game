package model;

//Tile is a place that train run on
public class Tile {
    private Cargo cargoInThisTile;
    private boolean isTrain;
    private Tile nextTile;
    private Tile prevTile;
    private Tile crossing;
    private int px;
    private int py;

    //MODIFIES: this
    //EFFECTS: create new tile without next tile assign
    public Tile(int px, int py) {
        this.px = px;
        this.py = py;
        isTrain = false;
    }

    //CONNECT 2 tiles in order of [this - tile2]
    //REQUIRES : tile2 is not null
    //MODIFIES : this, tile2
    //EFFECTS : Connect after this tile with tile2
    public void connectAfter(Tile tile2) {
        this.setNext(tile2);
        tile2.setPrev(this);
    }

    //CONNECT 2 tiles in order of [tile2 - this]
    //REQUIRES : tile2 is not null
    //MODIFIES : this, tile2
    //EFFECTS : Connect before this tile with tile2
    public void connectBefore(Tile tile2) {
        this.setPrev(tile2);
        tile2.setNext(this);
    }

    //assign which cargo is in this tile
    //REQUIRES : cargo != null
    //MODIFIES: this
    //EFFECTS: add link of the cargo into this tile
    public void assignCargo(Cargo cargo) {
        cargoInThisTile = cargo;
        isTrain = true;
    }

    //MODIFIES: this
    //EFFECTS: delete the cargo reference in this tile
    public void deleteCargo() {
        cargoInThisTile = null;
        isTrain = false;
    }

    public Tile getNext() {
        return nextTile;
    }

    public void setNext(Tile next) {
        nextTile = next;
    }

    public Tile getsPrev() {
        return prevTile;
    }

    public void setPrev(Tile prev) {
        prevTile = prev;
    }

    public Cargo getCargo() {
        return cargoInThisTile;
    }

    //EFFECTS: if this is crossing return either this tile or bind tile has train
    //       :  otherwise return isTrain
    public boolean hasTrain() {
        if (crossing != null) {
            return (isTrain || getCrossing().isTrain);
        }
        return isTrain;
    }

    //MODIFIES: this
    //EFFECTS: bind 2 tiles together to be intersection
    public static void makeItCrossing(Tile tile1, Tile tile2) {
        tile1.crossing = tile2;
        tile2.crossing = tile1;
    }

    //EFFECTS: check the position of this tile that is match with input or not and return boolean
    public boolean isPosition(int findX, int findY) {
        return getPx() == findX && getPy() == findY;
    }

    public int getPx() {
        return px;
    }

    public int getPy() {
        return py;
    }

    public Tile getCrossing() {
        return crossing;
    }
}
