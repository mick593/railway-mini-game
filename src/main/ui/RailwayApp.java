package ui;

import exception.CantSwitchWhileHasTrainException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.rmi.server.ExportException;
import java.util.List;
import java.util.Scanner;

public class RailwayApp extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    RailwayGame game;
    private static final String JSON_STORE = "./data/railwayGame.json";
    private static final String SOUND_STORE = "./data/gameSong.wav";

//    private Scanner input;
//    private JsonWriter jsonWriter;
//    private JsonReader jsonReader;

    JPanel gameDrawer;
    JPanel controller;
    Clip clip;

    public RailwayApp(RailwayGame game) {
        super("Game");

        setSize(WIDTH, HEIGHT);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

//        jsonWriter = new JsonWriter(JSON_STORE);
        //jsonReader = new JsonReader(JSON_STORE);
        this.game  = game;
        System.out.println("2313");
        gameDrawer = new GameDrawer(game);
        add(gameDrawer);
        controller = new ControllerPanel(game);
        add(controller);
        //addKeyListener(new KeyHandler());
        setVisible(true);
        setLayout(null);
        addTimer();
        playMusic();
    }


    //MODIFIES: this
    // EFFECTS: initializes a timer that updates game each
    //          INTERVAL milliseconds
    private void addTimer() {
        final Timer time = new Timer(670, null);
        time.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (game.isCrash() || game.isWin()) {
                    time.stop();
                    clip.stop();
                    System.out.println("game Over");
                } else {
                    game.update();
                    gameDrawer.repaint();
                    //requestFocus();
                }
            }
        });

        time.start();
    }

//    private class KeyHandler extends KeyAdapter {
//        @Override
//        // MODIFIES: this
//        // EFFECTS:  updates game in response to a keyboard event
//        public void keyPressed(KeyEvent e) {
//            switch (e.getKeyCode()) {
////                case 68:
////                case KeyEvent.VK_KP_LEFT:
////                    game.getTrains().get(0).switchStopState();
////                    break;
////                case KeyEvent.VK_RIGHT:
////                case KeyEvent.VK_KP_RIGHT:
////                    game.getTrains().get(1).switchStopState();
////                    break;
//                case KeyEvent.VK_DOWN:
//                case KeyEvent.VK_KP_DOWN:
//                    loadGame();
//                    break;
//                default:
//                    break;
//            }
//        }
//    }

    //MODIFIES: this
    //EFFECTS: open the music and play in loop
    private void playMusic() {
        try {
            File musicPath = new File(SOUND_STORE);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (FileNotFoundException ex) {
            System.out.println("error");
        } catch (IOException e) {
            System.out.println("error");
        } catch (UnsupportedAudioFileException e) {
            System.out.println("error");
        } catch (LineUnavailableException e) {
            System.out.println("error");
        }
    }
}
