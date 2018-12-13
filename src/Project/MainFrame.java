package Project;

import javax.swing.*;
import java.awt.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainFrame extends JFrame {
    final JFXPanel fxPanel = new JFXPanel();
    Media hit = new Media(getClass().getResource("/media/gamesong.mp3").toString()); //Instinct, by BenSound https://www.bensound.com/royalty-free-music/track/instinct
    MediaPlayer mediaPlayer = new MediaPlayer(hit);
    public MainFrame(String title) {
        super(title);
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setFocusable(false);
        add(fxPanel);
        mediaPlayer.play();
        MainPanel main = new MainPanel(this);
        LayoutManager overlay = new OverlayLayout(main);
        main.setLayout(overlay);
        setLayout(new BorderLayout());
        getContentPane().add(main, BorderLayout.CENTER);
        pack();
        this.setVisible(true);
        repaint();
    }
}