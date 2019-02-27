package Project;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainFrame extends JFrame {
    final JFXPanel fxPanel = new JFXPanel();
    Media hit = new Media(getClass().getResource("/media/gamesong.mp3").toString()); //Instinct, by BenSound https://www.bensound.com/royalty-free-music/track/instinct
    MediaPlayer mediaPlayer = new MediaPlayer(hit);
    public MainFrame(String title, String ip) throws Exception {
        super(title);
        setResizable(false);
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setFocusable(false);
        add(fxPanel);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        MainPanel main = new MainPanel(this, ip);
        LayoutManager overlay = new OverlayLayout(main);
        main.setLayout(overlay);
        setLayout(new BorderLayout());
        getContentPane().add(main, BorderLayout.CENTER);
        pack();
        this.setVisible(true);
        repaint();
    }
}