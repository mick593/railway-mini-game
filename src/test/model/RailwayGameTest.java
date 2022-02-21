package model;

import exception.CantSwitchWhileHasTrainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RailwayGameTest {
    private static final int SCORE_TO_WIN = RailwayGame.SCORE_TO_WIN;

    RailwayGame railwayGame;

    @BeforeEach
    public void runBefore() {
        railwayGame = new RailwayGame(1);
    }

    @Test
    public void generateTest() {
        TrainStation station = railwayGame.getStations().get(0);
        railwayGame.generatePassenger();
        //the passenger will not be assign to the same number of station
        assertEquals(0, railwayGame.getStations().get(0).getPassenger(0));
    }

    @Test
    public void generateHasTrainTest() {
        TrainStation stationFirst = railwayGame.getStations().get(0);
        new Cargo(stationFirst, null);
        int passengerBefore = stationFirst.getPassenger(0);
        railwayGame.generatePassenger();
        int passengerAfter = stationFirst.getPassenger(0);

        //no passenger will generate at all
        assertEquals(0, passengerAfter - passengerBefore);
    }

    @Test
    public void winningTest() {
        assertFalse(railwayGame.isWin());
        assertEquals(0, railwayGame.getScore());
        railwayGame.setScore(SCORE_TO_WIN);
        assertTrue(railwayGame.isWin());
        assertEquals(SCORE_TO_WIN, railwayGame.getScore());
    }

    @Test
    public void moveFail() {
        Train train1 = railwayGame.getTrains().get(0);
        int posX = train1.getFrontTile().getPx();
        int posY = train1.getFrontTile().getPy();

        railwayGame.moveTrains();

        assertEquals(posX, train1.getFrontTile().getPx());
        assertEquals(posY, train1.getFrontTile().getPy());
    }

    @Test
    public void moveSuccess() {
        Train train1 = railwayGame.getTrains().get(0);
        int posX = train1.getFrontTile().getPx();
        int posY = train1.getFrontTile().getPy();

        railwayGame.switchStopperThatTrain(0);
        railwayGame.update();

        assertTrue(posX != train1.getFrontTile().getPx() ||
                posY != train1.getFrontTile().getPy());
    }

    @Test
    public void switchTest() {
        SwitchTile switchTile = railwayGame.getSwitchTiles().get(0);
        assertEquals(0, switchTile.getPointingIndex());
        try {
            railwayGame.switchLineSwitchTile(0);
        } catch (CantSwitchWhileHasTrainException e) {
            fail("should not be here");
        }
        assertEquals(1, switchTile.getPointingIndex());
    }

    @Test
    public void crashTest() {
        Tile tile1 = new Tile(-99,-99);
        Tile tile2 = new Tile(-99,8888);
        tile1.connectAfter(tile2);
        Train train1 = new Train(1, tile1, 1);
        Train train2 = new Train(1, tile2,1);
        railwayGame.getTrains().add(train1);
        railwayGame.getTrains().add(train2);

        train1.setStop(false);

        railwayGame.update();
        assertTrue(railwayGame.isCrash());
    }

    @Test
    public void selectedTrainTest() {
        Train selected = railwayGame.getSelectedTrain();
        assertEquals(0, railwayGame.getTrains().indexOf(selected));

        railwayGame.setSelectedTrainNext();
        selected = railwayGame.getSelectedTrain();

        assertEquals(1 , railwayGame.getTrains().indexOf(selected));
        railwayGame.stopSelected();
        assertFalse(selected.isStop());
    }

    @Test
    public void selectedSwitchTest() {
        railwayGame = new RailwayGame(2);
        SwitchTile selected = railwayGame.getSelectedSwitchTile();
        assertEquals(0, railwayGame.getSwitchTiles().indexOf(selected));

        railwayGame.setSelectedSwitchTileNext();
        selected = railwayGame.getSelectedSwitchTile();

        assertEquals(1 , railwayGame.getSwitchTiles().indexOf(selected));
        assertEquals(0, selected.getPointingIndex());
        railwayGame.switchSelected();
        assertEquals(1,selected.getPointingIndex());
    }

    @Test
    public void selectedSwitchHasTrainTest() {
        railwayGame = new RailwayGame(2);
        SwitchTile selected = railwayGame.getSelectedSwitchTile();
        assertEquals(0, railwayGame.getSwitchTiles().indexOf(selected));

        railwayGame.setSelectedSwitchTileNext();
        selected = railwayGame.getSelectedSwitchTile();

        assertEquals(1 , railwayGame.getSwitchTiles().indexOf(selected));
        assertEquals(0, selected.getPointingIndex());
        selected.assignCargo(new Cargo(selected,null));
        railwayGame.switchSelected();
        assertEquals(0,selected.getPointingIndex());
    }

    @Test
    public void getTileTest() {
        assertEquals(20,railwayGame.getTiles().size());
    }




}
