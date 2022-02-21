package model;

import exception.CantSwitchWhileHasTrainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwitchTileTest {
    private static final int X_POSITION = 5;
    private static final int Y_POSITION = 5;

    @Test
    public void connectTest() {
        Tile tileA = new Tile(X_POSITION, Y_POSITION);
        Tile tileB = new Tile(X_POSITION, Y_POSITION);
        SwitchTile switchTile = new SwitchTile(X_POSITION, Y_POSITION);
        switchTile.connectAfter(tileA);
        switchTile.connectAfter(tileB);

        assertEquals(tileB, switchTile.getNext());
        try {
            switchTile.switchLine();
            assertEquals(tileA, switchTile.getNext());
            switchTile.switchLine();
            assertEquals(tileB, switchTile.getNext());
        } catch (CantSwitchWhileHasTrainException e) {
            fail("no exception should be made");
        }

    }

    @Test
    public void tryFailSwitchTest() {
        SwitchTile swt = new SwitchTile(X_POSITION, Y_POSITION);
        swt.assignCargo(new Cargo(swt, null));
        try {
            swt.switchLine();
            fail("excepption expected");
        } catch (CantSwitchWhileHasTrainException e) {
            //pass
        }

    }

    @Test
    public void changePointingIndexTest() {
        SwitchTile swt = new SwitchTile(X_POSITION, Y_POSITION);
        SwitchTile swt1 = new SwitchTile(X_POSITION, Y_POSITION);
        SwitchTile swt2 = new SwitchTile(X_POSITION, Y_POSITION);
        swt.connectAfter(swt2);
        swt.connectAfter(swt1);
        assertTrue(swt.setPointingIndex(1));
        assertEquals(1, swt.getPointingIndex());
    }

    @Test
    public void changePointingIndexHasTrainTest() {
        SwitchTile swt = new SwitchTile(X_POSITION, Y_POSITION);
        swt.assignCargo(new Cargo(swt, null));
        SwitchTile swt1 = new SwitchTile(X_POSITION, Y_POSITION);
        SwitchTile swt2 = new SwitchTile(X_POSITION, Y_POSITION);
        swt.connectAfter(swt2);
        swt.connectAfter(swt1);
        assertFalse(swt.setPointingIndex(1));
    }

}
