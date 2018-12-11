package Project;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MenuElements extends JLabel implements ActionListener
{
    int w, h;
    static int size = 12;
    final JFXPanel fxPanel = new JFXPanel();
    Media hit = new Media(getClass().getResource("/media/song.mp3").toString()); //Instinct, by BenSound https://www.bensound.com/royalty-free-music/track/instinct
    MediaPlayer mediaPlayer = new MediaPlayer(hit);
    public MenuElements(String fileName)
    {
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setFocusable(false);
        add(fxPanel);
        mediaPlayer.play();
        fxPanel.setFocusable(false);
        w = 10;
        h = 10;
        try {
            StretchIcon image = new StretchIcon(this.getClass().getResource("/media/"+fileName));
            this.setIcon(image);
            this.setVisible(true);
        } catch (Exception e) {
            System.out.println("Background file not found");
        }
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.NORTH;
        JButton startGame = new JButton("Start Game");
        startGame.addActionListener(this);
        startGame.setActionCommand("start");
        JButton hostGame = new JButton("Host Game");
        hostGame.addActionListener(this);
        hostGame.setActionCommand("host");
        JButton quitGame = new JButton("Quit");
        quitGame.addActionListener(this);
        quitGame.setActionCommand("quit");
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(startGame, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(hostGame, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(quitGame, gbc);
    }

    public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
            new MainFrame("Game by Sarmed Alwan, 1603088");
            mediaPlayer.stop();
        } else if ("host".equals(e.getActionCommand())) {

        } else if ("quit".equals(e.getActionCommand())) {
            System.exit(0);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(1104, 832);
    } //Defines the dimensions
}
