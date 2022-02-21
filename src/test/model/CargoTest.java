package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CargoTest {
    private static final int X_POSITION = 5;
    private static final int Y_POSITION = 5;

    Cargo cart;
    Tile tile;

    @BeforeEach
    public void runBefore() {
        tile = new Tile(X_POSITION, Y_POSITION);
        cart = new Cargo(tile, null);
    }
    @Test
    public void moveTest() {
        Tile nextT = new Tile(X_POSITION, Y_POSITION);
        tile.connectAfter(nextT);
        assertEquals(cart, tile.getCargo());
        cart.move();
        assertEquals(cart, nextT.getCargo());
        assertEquals(nextT, cart.getTile());
    }


    @Test
    public void whichTrainTest() {
        assertEquals(null, cart.getTrain());
    }


}
