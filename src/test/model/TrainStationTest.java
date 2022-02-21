package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainStationTest {
    private static final int X_POSITION = 5;
    private static final int Y_POSITION = 5;

    TrainStation trainStation;
    Train train;

    @BeforeEach
    public void runBefore() {
        //create train number 1 and station number 1
        trainStation = new TrainStation(X_POSITION, Y_POSITION, 1);
        train = new Train(1,trainStation,1);
    }

    @Test
    public void deployTest() {
        //deploy only max cap of train and left some people
        trainStation.addPassenger(1, train.getCapacity());
        assertEquals(train.getCapacity(), trainStation.getPassenger(1));
        trainStation.deployPassenger(1);
        assertEquals(train.getCapacity() - train.getCapacity(), trainStation.getPassenger(1));
    }

    @Test
    public void deployManyTest() {
        //deploy only max cap of train and left some people
        trainStation.addPassenger(1, train.getCapacity() + 5);
        assertEquals(train.getCapacity() + 5, trainStation.getPassenger(1));
        trainStation.deployPassenger(1);
        assertEquals(train.getCapacity() + 5 - train.getCapacity(), trainStation.getPassenger(1));
    }

    @Test
    public void getStationNumTest() {
        assertEquals(1, trainStation.getStationNum());
    }
}
