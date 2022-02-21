package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TileTest {
    Tile tile;
    private static final int X_POSITION = 5;
    private static final int Y_POSITION = 5;

    @BeforeEach
    public void runBefore(){
        tile = new Tile(X_POSITION, Y_POSITION);

    }

    @Test
    public void connectAfterTest() {
        Tile testerT = new Tile(X_POSITION, Y_POSITION);
        tile.connectAfter(testerT);

        assertEquals(testerT, tile.getNext());
        assertEquals(tile, testerT.getsPrev());
    }

    @Test
    public void connectBeforeTest() {
        Tile testerT = new Tile(X_POSITION, Y_POSITION);
        tile.connectBefore(testerT);

        assertEquals(testerT, tile.getsPrev());
        assertEquals(tile, testerT.getNext());
    }

    @Test
    public void assignCargoTest() {
        assertFalse(tile.hasTrain());
        Cargo cart = new Cargo(tile, null);
        assertEquals(cart, tile.getCargo());
        assertTrue(tile.hasTrain());
    }

    @Test
    public void delCargoTest() {
        Cargo cart = new Cargo(tile, null);
        assertEquals(cart, tile.getCargo());
        tile.deleteCargo();
        assertEquals(null, tile.getCargo());
    }

//    @Test
//    public void getTileNumTest() {
//        Tile test = new Tile(X_POSITION, Y_POSITION);
//        int alreadyHave = test.getTileNumber();
//        for (int i = alreadyHave + 1; i <= alreadyHave + 10; i++) {
//            test = new Tile(X_POSITION, Y_POSITION);
//            assertEquals(i, test.getTileNumber());
//        }
//    }

    @Test
    public void crossingTest() {
        Tile primary = new Tile(X_POSITION, Y_POSITION);
        Tile secondary = new Tile(X_POSITION, Y_POSITION);
        Cargo cart = new Cargo(primary, null);
        assertTrue(primary.hasTrain());
        assertFalse(secondary.hasTrain());
        Tile.makeItCrossing(primary, secondary);
        assertTrue(primary.hasTrain());
        assertTrue(secondary.hasTrain());
    }

    @Test
    public void crossingMoreTest() {
        Tile primary = new Tile(X_POSITION, Y_POSITION);
        Tile secondary = new Tile(X_POSITION, Y_POSITION);
        Cargo cart = new Cargo(primary, null);
        new Cargo(secondary, null);
        assertTrue(primary.hasTrain());
        assertTrue(secondary.hasTrain());
        Tile.makeItCrossing(primary, secondary);
        assertTrue(primary.hasTrain());
        assertTrue(secondary.hasTrain());
    }

    @Test
    public void crossingNoneTest() {
        Tile primary = new Tile(X_POSITION, Y_POSITION);
        Tile secondary = new Tile(X_POSITION, Y_POSITION);
        assertFalse(primary.hasTrain());
        assertFalse(secondary.hasTrain());
        Tile.makeItCrossing(primary, secondary);
        assertFalse(primary.hasTrain());
        assertFalse(secondary.hasTrain());
    }

    @Test
    public void positionTest() {
        assertEquals(X_POSITION, tile.getPx());
        assertEquals(Y_POSITION, tile.getPy());
        assertTrue(tile.isPosition(X_POSITION,Y_POSITION));
    }



}
