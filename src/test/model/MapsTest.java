package model;
import exception.CantSwitchWhileHasTrainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class MapsTest {
    private static final int X_POSITION = 5;
    private static final int Y_POSITION = 5;

    Maps maps;

    @BeforeEach
    public void runBefore() {
        maps = new Maps(1);
        maps.clear();//empty map
    }

    @Test
    public void clearTest() {
        assertEquals(0, maps.getTiles().size());
        assertEquals(0, maps.getStations().size());
        assertEquals(0, maps.getSwitches().size());
        assertEquals(0, maps.getTrains().size());
    }

    @Test
    public void newTileTest() {
        maps.newTile(0,0);
        assertEquals(1, maps.getTiles().size());
    }

    @Test
    public void buildUpTest() {
        maps.buildUp(X_POSITION, Y_POSITION, 2);
        assertEquals(3, maps.getTiles().size());
    }

    @Test
    public void buildDownTest() {
        maps.buildDown(X_POSITION, Y_POSITION, 2);
        assertEquals(3, maps.getTiles().size());
    }

    @Test
    public void buildRightTest() {
        maps.buildRight(X_POSITION, Y_POSITION, 2);
        assertEquals(3, maps.getTiles().size());
    }

    @Test
    public void buildLeftTest() {
        maps.buildLeft(X_POSITION, Y_POSITION, 2);
        assertEquals(3, maps.getTiles().size());
    }

    @Test
    public void crossingTest() {
        maps.buildRight(3,3, 5);
        maps.buildDown(3,1,5);
        maps.makeItCrossing();
        assertTrue(maps.findTileByPosition(3,3).getCrossing() != null);
    }

    @Test
    public void makeTileToSwitchTest() {
        maps.buildRight(0,1,1);
        maps.newTile(1,2);
        maps.newTile(2,1);
        maps.makeTileToSwitch(1,1,1,2,2,1);
        SwitchTile swt = maps.getSwitches().get(0);
        assertEquals(swt.getNext(), maps.findTileByPosition(2,1));
        try {
            swt.switchLine();
        } catch (CantSwitchWhileHasTrainException e) {
            //
        }
        assertEquals(swt.getNext(), maps.findTileByPosition(1,2));
    }

    @Test
    public void mapTwoTest() {
        maps = new Maps(2);
        assertEquals(2, maps.getTrains().size());
    }

    @Test
    public void mapThreeTest() {
        maps = new Maps(3);
        assertEquals(1, maps.getTrains().size());
    }

}
