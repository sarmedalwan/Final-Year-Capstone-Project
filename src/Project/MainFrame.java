package Project;

import javax.swing.*;
import java.awt.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

class MainFrame extends JFrame {
    final JFXPanel fxPanel = new JFXPanel();
    Media hit = new Media(getClass().getResource("/media/gamesong.mp3").toString()); //Instinct, by BenSound https://www.bensound.com/royalty-free-music/track/instinct
    MediaPlayer mediaPlayer = new MediaPlayer(hit);
    public MainFrame(String title, String ip) throws Exception { //Creates the main gameplay JFrame that the game is contained within
        super(title);
        MainPanel main = new MainPanel(this, ip); //Creates the main panel that all of the game panels are contained within
        setResizable(false); //Stops the user from resizing the game frame
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setFocusable(false);
        add(fxPanel);
        mediaPlayer.setVolume(0.2); //20% volume for the game music so it isn't too intrusive
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //Makes the music loop indefinitely
        mediaPlayer.play();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LayoutManager overlay = new OverlayLayout(main);
        main.setLayout(overlay);
        setLayout(new BorderLayout());
        getContentPane().add(main, BorderLayout.CENTER); //Adds the main game panel to the main frame
        pack();
        this.setVisible(true);
        repaint();
    }
}