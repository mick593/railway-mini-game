package ui;

import exception.CantSwitchWhileHasTrainException;
import javafx.scene.paint.Stop;
import model.RailwayGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//The panel that let user give the input mostly by clicking button.
public class ControllerPanel extends JPanel {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 400;

    public static final int TOP_LEFT_X = 0;
    public static final int TOP_LEFT_Y = 600;
    RailwayGame game;

    private static final String JSON_STORE = "./data/railwayGame.json";
    private JsonWriter jsonWriter;

    public ControllerPanel(RailwayGame game) {
        this.game = game;
        jsonWriter = new JsonWriter(JSON_STORE);
        setSize(WIDTH,HEIGHT);
        setLayout(null);
        setBounds(TOP_LEFT_X,TOP_LEFT_Y,WIDTH,HEIGHT);
        setBackground(Color.PINK);
        makeAddCartButton();
        makeStopButton(game);
        makePrevButton(game);
        makeNextButtonForSwitch(game);
        makeSwitchButton(game);
        makeSaveButton();

        makeMouseControl(game);
    }

    //MODIFIES: this
    //EFFECTS: add mouse click option to the object and addCargo of train or switchLine of SwitchTile depending on
    // type.
    private void makeMouseControl(RailwayGame game) {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (GameDrawer.getTrainByPosition(e.getX(),e.getY(), game) != null) {
                    GameDrawer.getTrainByPosition(e.getX(),e.getY(), game).addCargoBehind();
//                    GameDrawer.getTrainByPosition(e.getX(),e.getY(),game).switchStopState();
                } else if (GameDrawer.getSwitchByPosition(e.getX(),e.getY(), game) != null) {
                    switchClickedSwitchTile(e, game);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
    }

    //MODIFIES: game
    //EFFECTS: switch the clicked switch tile.
    private void switchClickedSwitchTile(MouseEvent e, RailwayGame game) {
        try {
            GameDrawer.getSwitchByPosition(e.getX(), e.getY(), game).switchLine();
        } catch (CantSwitchWhileHasTrainException cantSwitchWhileHasTrainException) {
            //cantSwitchWhileHasTrainException.printStackTrace();
        }
    }

    //MODIFIES: this
    //EFFECTS: make a button for game.switchSelected()
    private void makeSwitchButton(RailwayGame game) {
        JButton switchButton = new JButton("switch");

        switchButton.setBounds(TOP_LEFT_X + 100,TOP_LEFT_Y + 140,100,20);

        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.switchSelected();
            }
        });
        add(switchButton);
    }

    //MODIFIES: this
    //EFFECTS: make a button for saveGame().
    private void makeSaveButton() {
        JButton saveButton = new JButton("save");
        saveButton.setBounds(TOP_LEFT_X + 300,TOP_LEFT_Y + 60,150,15);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });
        add(saveButton);
    }

    //MODIFIES: this
    //EFFECTS: make a button for game.setSelectedSwitchTileNext().
    private void makeNextButtonForSwitch(RailwayGame game) {
        JButton nextButtonForSwitch = new JButton("=>");

        nextButtonForSwitch.setBounds(TOP_LEFT_X + 100,TOP_LEFT_Y + 120,100,20);
        nextButtonForSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setSelectedSwitchTileNext();
            }
        });
        add(nextButtonForSwitch);
    }

    //MODIFIES: this
    //EFFECTS: make a button for adding more cargo on the selected train.
    private void makeAddCartButton() {
        JButton addCartButton = new JButton("add cargo");

        addCartButton.setBounds(TOP_LEFT_X + 100,TOP_LEFT_Y + 60,150,15);
        addCartAction(addCartButton);
        add(addCartButton);
    }

    //MODIFIES: this, game
    //EFFECTS: create a JButton for stopSelected()
    private void makeStopButton(RailwayGame game) {
        JButton stopButton = new JButton("un/stop");
        stopButton.setBounds(TOP_LEFT_X + 100,TOP_LEFT_Y + 40,150,15);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.stopSelected();
            }
        });
        add(stopButton);
    }

    //MODIFIES:  this, game
    //EFFECTS: create a button for setSelectedTrainNext
    private void makePrevButton(RailwayGame game) {
        JButton prevButton = new JButton("->");

        prevButton.setBounds(TOP_LEFT_X + 100,TOP_LEFT_Y + 20,150,15);
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setSelectedTrainNext();
            }
        });
        add(prevButton);
    }

    //MODIFIES: addCartButton
    //EFFECTS: Add the action listener for addCartButton
    private void addCartAction(JButton addCartButton) {
        addCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getSelectedTrain().addCargoBehind();
            }
        });
    }

    // EFFECTS: saves the game to Json file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved "  + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


}
