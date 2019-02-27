package Project;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

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
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
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
        JLabel title1 = new JLabel("Sarmed Alwan's");
        JLabel title2 = new JLabel("Operation Mars");
        title1.setFont(new Font ("BahnSchrift", Font.ITALIC, 14));
        title1.setForeground(Color.RED);
        title2.setForeground(Color.WHITE);
        title2.setFont(new Font ("BahnSchrift", Font.BOLD, 80));
        JButton startGame = new JButton("Join Game");
        startGame.addActionListener(this);
        startGame.setActionCommand("start");
        JButton hostGame = new JButton("Host Game");
        hostGame.addActionListener(this);
        hostGame.setActionCommand("host");
        JButton credits = new JButton("Credits");
        credits.addActionListener(this);
        credits.setActionCommand("credits");
        JButton quitGame = new JButton("Quit");
        quitGame.addActionListener(this);
        quitGame.setActionCommand("quit");
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(title1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10,10,100,10);
        this.add(title2, gbc);
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(startGame, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(hostGame, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(credits, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        this.add(quitGame, gbc);
    }

    public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
            try {
                String ip = JOptionPane.showInputDialog("Enter IP Address of Host: ");
                new MainFrame("Operation Mars", ip);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            mediaPlayer.stop();
        } else if ("host".equals(e.getActionCommand())) {
            new Server().start();
        } else if ("quit".equals(e.getActionCommand())) {
            System.exit(0);
        } else if ("credits".equals(e.getActionCommand())) {
            openCredits();
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(1104, 832);
    } //Defines the dimensions

    public void openCredits(){
        JPanel panel = new JPanel();
        JLabel dev = new JLabel("Design and Programming");
        JLabel dev1 = new JLabel("Sarmed Alwan");
        JLabel art = new JLabel("Art");
        JLabel art1 = new JLabel("Sarmed Alwan");
        JLabel music = new JLabel("Music");
        JLabel music1 = new JLabel("Bensound");
        JLabel music2 = new JLabel("Richard Ampleford");
        JLabel video = new JLabel("Main Menu Video");
        JLabel video1 = new JLabel("publicdomaintorrents.info");
        dev.setFont(new Font("Aharoni", Font.BOLD, 25));
        art.setFont(new Font("Aharoni", Font.BOLD, 25));
        music.setFont(new Font("Aharoni", Font.BOLD, 25));
        video.setFont(new Font("Aharoni", Font.BOLD, 25));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(dev, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(dev1,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(art,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(art1,gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(music,gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(music1,gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(music2,gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(video,gbc);
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(video1,gbc);
        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.setBackground(Color.white);

        UIManager UI=new UIManager();
        UI.put("OptionPane.background", Color.white);
        UI.put("Panel.background", Color.white);
        JOptionPane.showMessageDialog(this, panel, "Credits", JOptionPane.PLAIN_MESSAGE);
    }
}
