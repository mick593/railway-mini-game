package model;

//The member of the train. Cargo must have its train.
public class Cargo {
    private  Train masterTrain;
    private Tile tile;

    //REQUIRES: location != NULL, masterTrain != null, location != hasTrain()
    //EFFECTS: create an instance of train with default location
    public Cargo(Tile location, Train masterTrain) {
        tile = location;
        this.masterTrain = masterTrain;
        tile.assignCargo(this);
    }

    //REQUIRES: cargo is not stopped, and the next tile is not null
    //MODIFIES: this, Train
    //EFFECT: change this cargo position to the next Tile
    public void move() {
        tile.deleteCargo();

        //if (tile.getNext().hasTrain()) -> gameOver or just stop that train ?
        tile = tile.getNext();

        tile.assignCargo(this);
    }

    //EFFECTS: return Train of this cargo
    public Train getTrain() {
        return masterTrain;
    }

    //EFFECTS: return the tile that this cargo is on
    public Tile getTile() {
        return tile;
    }



}
