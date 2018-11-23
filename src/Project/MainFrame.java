package Project;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    final JFXPanel fxPanel = new JFXPanel();
    Media song = new Media(getClass().getResource("/media/a.mp3").toString()); //'Instinct' from https://www.bensound.com
    MediaPlayer mediaPlayer = new MediaPlayer(song);
    public MainFrame(String title) {
        super(title);
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setFocusable(false);
        getContentPane().add(fxPanel);
        mediaPlayer.play();
        fxPanel.setFocusable(false);
        MainPanel main = new MainPanel();
        LayoutManager overlay = new OverlayLayout(main);
        main.setLayout(overlay);
        setLayout(new BorderLayout());
        getContentPane().add(main, BorderLayout.CENTER);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }

}