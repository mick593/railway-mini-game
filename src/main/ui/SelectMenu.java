package ui;

import model.RailwayGame;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//This is the start menu that player select options[start game, load game, select map, quit]
public class SelectMenu extends JFrame {
    private static final String JSON_STORE = "./data/railwayGame.json";

    String[] mapStrings = {"1","2","3"};
    JsonReader reader;
    private int selectedMap = 1;


    public SelectMenu() {
        super("Select Screen");
        reader = new JsonReader(JSON_STORE);
        setSize(450,100);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        makeStartButton();
        makeMapListButton();
        makeLoadButton();
        makeExitButton();



        setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: create and return the JButton that start the game
    private void makeStartButton() {
        JButton startButton = new JButton("Start New Game on Map");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RailwayGame game = new RailwayGame(selectedMap);
                new RailwayApp(game);
            }
        });

        startButton.setBounds(0,0,160,20);
        add(startButton);
    }

    //MODIFIES: this
    //EFFECTS: create and return the JButton that load the saved game.
    private void makeLoadButton() {
        JButton loadButton = new JButton("Load saved game");

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    RailwayGame loaded = reader.read();
                    new RailwayApp(loaded);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        loadButton.setBounds(0,20,160,20);

        add(loadButton);
    }

    //MODIFIES: this
    //EFFECTS: create and return the JButton that use for quit the game
    private void makeExitButton() {
        JButton exitButton = new JButton("Quit");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitButton.setBounds(0,40,160,20);
        add(exitButton);
    }

    //MODIFIES: this
    //EFFECTS: create and return the List(comboBox) that let user change the map options
    private void makeMapListButton() {
        JComboBox mapList = new JComboBox(mapStrings);
        mapList.setSelectedIndex(0);
        mapList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String num = (String)cb.getSelectedItem();
                System.out.println(num);
                selectedMap = Integer.parseInt(num);
//                updateLabel(petName);
            }
        });
        mapList.setBounds(160,0,75,20);
        add(mapList);
    }


}
