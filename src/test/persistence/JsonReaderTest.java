package persistence;


import model.RailwayGame;

import model.SwitchTile;
import model.Train;
import model.TrainStation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RailwayGame rg = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testEmptyReader.json");
        try {
            RailwayGame rg = reader.read();
            assertEquals(0, rg.getScore());
            assertEquals(0, rg.getTrains().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            RailwayGame rg = reader.read();
            assertEquals(12, rg.getScore());
            List<Train> trains = rg.getTrains();
            assertEquals(2, trains.size());
            assertEquals(3,trains.get(1).getSize());
            assertTrue(trains.get(0).isStop());

            List<SwitchTile> switches = rg.getSwitchTiles();
            assertEquals(1, switches.size());
            assertEquals(0,switches.get(0).getPointingIndex());

            List<TrainStation> stations = rg.getStations();
            assertEquals(2, stations.size());
            assertEquals(5, stations.get(0).getPassenger(1));
            assertEquals(25, stations.get(1).getPassenger(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
