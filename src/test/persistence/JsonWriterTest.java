package persistence;


import exception.CantSwitchWhileHasTrainException;
import model.RailwayGame;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            RailwayGame rg = new RailwayGame(1);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterDefaultGame() {
        try {
            RailwayGame rg = new RailwayGame(1);
            JsonWriter writer = new JsonWriter("./data/testWriterDefaultGame.json");
            writer.open();
            writer.write(rg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefaultGame.json");
            rg = reader.read();
            assertEquals(0, rg.getScore());
            assertEquals(2, rg.getTrains().size());
            assertEquals(1, rg.getSwitchTiles().size());
            assertEquals(2, rg.getStations().size());
            assertTrue(rg.getTrains().get(0).isStop());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {

        try {
            RailwayGame rg = new RailwayGame(1);
            rg.switchStopperThatTrain(0);
            rg.switchLineSwitchTile(0);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.write(rg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            rg = reader.read();
            assertEquals(0, rg.getScore());
            assertEquals(2, rg.getTrains().size());
            assertEquals(1, rg.getSwitchTiles().size());
            assertEquals(2, rg.getStations().size());
            assertFalse(rg.getTrains().get(0).isStop());
            assertEquals(1, rg.getSwitchTiles().get(0).getPointingIndex());
        } catch (IOException | CantSwitchWhileHasTrainException e) {
            fail("Exception should not have been thrown");
        }

    }

}
