package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainTest {
    private static final int STATION_NUM_TEST = 1;
    private static final int X_POSITION = 5;
    private static final int Y_POSITION = 5;

    Tile startTile;
    Train trainN;

    @BeforeEach
    public void runBefore(){
        startTile = new Tile(X_POSITION, Y_POSITION);
        trainN = new Train(1,startTile,STATION_NUM_TEST);
    }

    @Test
    public void oneCargoTrainTest(){
        assertEquals(1,trainN.getSize());
        assertEquals(1,trainN.getTrainNum());
    }

    @Test
    public void addCargoTest(){
        Tile befTile = new Tile(X_POSITION, Y_POSITION);
        startTile.connectBefore(befTile);
        assertFalse(befTile.hasTrain());
        assertTrue(trainN.addCargoBehind());
        assertTrue(befTile.hasTrain());
        assertEquals(trainN, befTile.getCargo().getTrain());
        assertEquals(2, trainN.getSize());
    }

    @Test
    public void addCargoFailTest(){
        //null tile add
        assertFalse(trainN.addCargoBehind());
        Tile befTile = new Tile(X_POSITION, Y_POSITION);
        startTile.connectBefore(befTile);
        Train train2 = new Train(1,befTile,STATION_NUM_TEST);
        assertFalse(trainN.addCargoBehind());

    }

    @Test
    public void moveTrainTest(){
        Tile tile2 = new Tile(X_POSITION, Y_POSITION);
        startTile.connectAfter(tile2);
        assertEquals(startTile, trainN.getFrontTile());
        trainN.move();
        assertEquals(tile2, trainN.getFrontTile());
    }

    @Test
    public void switchStateStopTest() {
        trainN.setStop(true);
        assertTrue(trainN.isStop());
        trainN.switchStopState();
        assertFalse(trainN.isStop());
    }

    @Test
    public void crashTest() {
        Tile tile2 = new Tile(X_POSITION, Y_POSITION);
        startTile.connectAfter(tile2);
        Train train2 = new Train(1,tile2,1);
        trainN.move();
        assertTrue(trainN.isCrash());
    }

    @Test
    public void delTest() {
        assertTrue(startTile.hasTrain());
        trainN.delete();
        assertFalse(startTile.hasTrain());
    }

    @Test
    public void scanStationNullTest() {
        trainN = new Train(1,startTile,STATION_NUM_TEST);
        assertNull(trainN.scanStation());

    }

    @Test
    public void switchStopTest() {
        boolean initState = trainN.isStop();
        trainN.switchStopState();
        assertEquals(!initState, trainN.isStop());

        trainN.switchStopState();
        assertEquals(initState, trainN.isStop());
    }

    @Test
    public void pickUpFullTest() {
        TrainStation station = new TrainStation(X_POSITION, Y_POSITION, STATION_NUM_TEST);
        trainN = new Train(1,station,STATION_NUM_TEST);
        station.addPassenger(STATION_NUM_TEST, Train.MAX_PASSENGER/2);
        assertTrue(trainN.pickPassenger());
        assertEquals(Train.MAX_PASSENGER/2, trainN.getPassenger());
        assertEquals(Train.MAX_PASSENGER/2, trainN.getCapacity());
    }

    @Test
    public void pickUpOverflowTest() {
        TrainStation station = new TrainStation(X_POSITION, Y_POSITION, STATION_NUM_TEST);
        trainN = new Train(1,station,STATION_NUM_TEST);
        station.addPassenger(STATION_NUM_TEST, TrainStation.MAX_PASSENGER);
        assertTrue(trainN.pickPassenger());
        assertEquals(Train.MAX_PASSENGER, trainN.getPassenger());
    }

    @Test
    public void pickUpWrongStationTest() {
        TrainStation station = new TrainStation(X_POSITION, Y_POSITION, 3);
        trainN = new Train(1,station,STATION_NUM_TEST);
        station.addPassenger(STATION_NUM_TEST + 1, Train.MAX_PASSENGER*5);
        assertFalse(trainN.pickPassenger());
        assertEquals(0, trainN.getPassenger());
    }

    @Test
    public void pickUpNullStationTest() {
        trainN = new Train(1,startTile,STATION_NUM_TEST);
        assertFalse(trainN.pickPassenger());
        assertEquals(0, trainN.getPassenger());
    }

    @Test
    public void dropFullTest() {
        TrainStation station = new TrainStation(X_POSITION, Y_POSITION, STATION_NUM_TEST);
        trainN = new Train(1,station,STATION_NUM_TEST);
        station.addPassenger(STATION_NUM_TEST, Train.MAX_PASSENGER/2);
        assertTrue(trainN.pickPassenger());
        assertEquals(Train.MAX_PASSENGER/2, trainN.getPassenger());
        assertEquals(Train.MAX_PASSENGER/2, trainN.getCapacity());

        assertTrue(trainN.dropPassenger() > 0);
        assertEquals(0, trainN.getPassenger());
    }

    @Test
    public void dropWrongStationTest() {
        TrainStation station = new TrainStation(X_POSITION, Y_POSITION, 3);
        trainN = new Train(1,station,STATION_NUM_TEST );
        station.addPassenger(STATION_NUM_TEST, Train.MAX_PASSENGER);
        assertTrue(trainN.pickPassenger());
        assertEquals(Train.MAX_PASSENGER, trainN.getPassenger());

        assertFalse(trainN.dropPassenger() > 0);
        assertEquals(Train.MAX_PASSENGER, trainN.getPassenger());
    }

    @Test
    public void dropNullStationTest() {
        trainN = new Train(1,startTile,STATION_NUM_TEST);
        assertEquals(1,trainN.getCargos().size());
        assertFalse(trainN.dropPassenger() > 0);

    }

}
