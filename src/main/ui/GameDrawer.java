package ui;

import javafx.stage.Screen;
import model.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;

//The class that draw the output of the game graphically.
public class GameDrawer extends JPanel {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;
    public static final int TOP_LEFT_X = 30;
    public static final int TOP_LEFT_Y = 30;
    public static final int BLOCK_SIZE = 50;
    public static final int SPACE = 2;
    public static final int SPACED_BLOCK_SIZE = BLOCK_SIZE - 2 * SPACE;

    RailwayGame game;

    public GameDrawer(RailwayGame game) {
        this.game = game;
        Color bgColor = new Color(107, 173, 99);
        setBackground(Color.PINK);
        setSize(WIDTH, HEIGHT);
    }

    // MODIFIES: graphics
    // EFFECTS:  draws snake game onto graphics
    void draw(Graphics graphics) {

        graphics.setColor(Color.BLACK);
        drawMap(graphics);
        drawSelectedSwitchTile(graphics);

        drawTrain(graphics);
        drawStation(graphics);
        drawScore(graphics);
    }

    @Override
    //MODIFIES: g
    //EFFECTS: paint every thing after repaint is called
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }

    //MODIFIES: graphics
    //EFFECTS: paint the Tile Object in its own coordinate.
    public void drawMap(Graphics graphics) {
        List<Tile> tiles = game.getTiles();

        for (Tile tile : tiles) {

            Image railPic = null;
            try {
                railPic = ImageIO.read(new File(getRailFileName(tile)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            graphics.drawImage(railPic,tile.getPx() * BLOCK_SIZE + TOP_LEFT_X,
                    tile.getPy() * BLOCK_SIZE + TOP_LEFT_Y, BLOCK_SIZE, BLOCK_SIZE,this);

        }

    }

    //MODIFIES: graphics
    //EFFECTS: paint the Train Object in its own coordinate and paint the selected Train by circle.
    public void drawTrain(Graphics graphics) {
        List<Train> trains = game.getTrains();

        for (Train train : trains) {
            drawTrainInformation(graphics, train);

            for (Cargo cart : train.getCargos()) {
                Tile tile = cart.getTile();
                Image railPic;
                try {
                    if (train.getCargos().indexOf(cart) == 0) {
                        railPic = ImageIO.read(new File(getTrainFileName(tile)));
                    } else {
                        railPic = ImageIO.read(new File(getTrainBodyFileName(tile)));
                    }

                    graphics.drawImage(railPic, TOP_LEFT_X + tile.getPx() * BLOCK_SIZE,
                            TOP_LEFT_Y + tile.getPy() * BLOCK_SIZE, BLOCK_SIZE,BLOCK_SIZE,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        drawSelectedTrain(graphics, game.getSelectedTrain().getFrontTile(), 187, 232, 146);
    }

    //MODIFIES: graphics
    //EFFECTS: paint the highlight selected train.
    private void drawSelectedTrain(Graphics graphics, Tile frontTile, int i, int i2, int i3) {
        Tile tile = frontTile;
        Color color = new Color(i, i2, i3, 64);
        graphics.setColor(color);
        graphics.fillOval(tile.getPx() * BLOCK_SIZE + TOP_LEFT_X + 1, tile.getPy() * BLOCK_SIZE + TOP_LEFT_Y + 1,
                SPACED_BLOCK_SIZE, SPACED_BLOCK_SIZE);
        Color colorB = new Color(0, 0, 0);
        graphics.setColor(colorB);
    }

    //MODIFIES: graphics
    //EFFECTS: draw the number of passenger and the train number.
    private void drawTrainInformation(Graphics graphics, Train train) {
        String out = "";
        String out2 = "";
        out += train.getPassenger() + " ";
        out2 += "no." + train.getTrainNum();
        graphics.drawString(out, train.getFrontTile().getPx() * BLOCK_SIZE + TOP_LEFT_X,
                train.getFrontTile().getPy() * BLOCK_SIZE + TOP_LEFT_Y + BLOCK_SIZE);

        graphics.drawString(out2, train.getFrontTile().getPx() * BLOCK_SIZE + TOP_LEFT_X,
                train.getFrontTile().getPy() * BLOCK_SIZE + TOP_LEFT_Y);
    }

    //MODIFIES: graphics
    //EFFECTS: draw the score that the player currently has.
    public void drawScore(Graphics graphics) {
        String outPrint = "";
        outPrint += game.getScore() + "/" + RailwayGame.SCORE_TO_WIN;
        Color color = new Color(210, 204, 204);
        graphics.setColor(color);
        graphics.fillRect(0, 0, 45, 17);
        Color color1 = new Color(0, 0, 0);
        graphics.setColor(color1);
        graphics.drawString(outPrint, 1, 15);

    }

    //MODIFIES: graphics
    //EFFECTS: Paint to indicate which switchTile that player selected.
    public void drawSelectedSwitchTile(Graphics graphics) {
        Tile tile = game.getSelectedSwitchTile();
        Color color = new Color(238, 0, 81, 64);
        graphics.setColor(color);
        graphics.fillOval(tile.getPx() * BLOCK_SIZE + TOP_LEFT_X + 1, tile.getPy() * BLOCK_SIZE + TOP_LEFT_Y + 1,
                SPACED_BLOCK_SIZE, SPACED_BLOCK_SIZE);
        Color colorB = new Color(0, 0, 0);
        graphics.setColor(colorB);
    }

    //MODIFIES: graphics
    //EFFECTS: Draw the train station on its location and indicate the station number.
    public void drawStation(Graphics graphics) {
        List<TrainStation> stations = game.getStations();

        for (TrainStation station : stations) {

            String out2 = "";

            out2 += "no." + station.getStationNum();

            Tile tile = station;
            Image railPic = null;
            try {
                railPic = ImageIO.read(new File("./data/stations/station_01.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            graphics.drawImage(railPic,tile.getPx() * BLOCK_SIZE + TOP_LEFT_X,
                    tile.getPy() * BLOCK_SIZE + TOP_LEFT_Y, BLOCK_SIZE, BLOCK_SIZE,this);

            graphics.drawString(out2,station.getPx() * BLOCK_SIZE + TOP_LEFT_X,
                    station.getPy() * BLOCK_SIZE + TOP_LEFT_Y + BLOCK_SIZE);
        }
    }

    //EFFECTS: Method that help to identify which file to pick to get the correct rail direction.
    public String getRailFileName(Tile tile) {
        int dxPrev = tile.getPx() - tile.getsPrev().getPx();
        int dyPrev = tile.getPy() - tile.getsPrev().getPy();
        int dxNext = tile.getNext().getPx() - tile.getPx();
        int dyNext = tile.getNext().getPy() - tile.getPy();

        String directionPrev = getDirection(dxPrev,dyPrev);
        String directionNext = getDirection(dxNext, dyNext);

        return "./data/rails/rail_" + directionPrev + "_" + directionNext + ".png";

    }

    //EFFECTS: determine the direction of train's head and return the appropriate file name.
    public String getTrainFileName(Tile tile) {

        int dxNext = tile.getNext().getPx() - tile.getPx();
        int dyNext = tile.getNext().getPy() - tile.getPy();

        String directionNext = getDirection(dxNext, dyNext);

        return "./data/trains/train_" + directionNext + ".png";

    }

    //EFFECTS: determine the direction of train's body and return the appropriate file name.
    public String getTrainBodyFileName(Tile tile) {

        int dxNext = tile.getNext().getPx() - tile.getPx();
        int dyNext = tile.getNext().getPy() - tile.getPy();

        String directionNext = getDirection(dxNext, dyNext);

        return "./data/trains/train_body_" + directionNext + ".png";

    }



    //EFFECTS: return the String of direction of difference coordinates.
    public String getDirection(int dx, int dy) {
        String direction;
        if (dx == 1 && dy == 0) {
            direction = "RIGHT";
        } else if (dx == -1 && dy == 0) {
            direction = "LEFT";
        } else if (dx == 0 && dy == -1) {
            direction = "UP";
        } else {
            direction = "DOWN";
        }

        return direction;
    }

    public static Train getTrainByPosition(int x, int y, RailwayGame game) {
        int adjustedX = (x - TOP_LEFT_X) / BLOCK_SIZE;
        int adjustedY = (y - TOP_LEFT_Y) / BLOCK_SIZE;

        for (Tile tile : game.getTiles()) {
            if (tile.isPosition(adjustedX, adjustedY)) {
                if (tile.hasTrain()) {
                    return tile.getCargo().getTrain();
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public static SwitchTile getSwitchByPosition(int x, int y, RailwayGame game) {
        int adjustedX = (x - TOP_LEFT_X) / BLOCK_SIZE;
        int adjustedY = (y - TOP_LEFT_Y) / BLOCK_SIZE;

        for (Tile tile : game.getTiles()) {
            if (tile.isPosition(adjustedX, adjustedY)) {
                if (tile instanceof SwitchTile) {
                    return (SwitchTile)tile;
                }
            }
        }
        return null;
    }

}
