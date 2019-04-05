package Project;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuElements extends JLabel implements ActionListener
{
    int w, h;
    final JFXPanel fxPanel = new JFXPanel();
    Media hit = new Media(getClass().getResource("/media/song.mp3").toString()); //Instinct, by BenSound https://www.bensound.com/royalty-free-music/track/instinct
    MediaPlayer mediaPlayer = new MediaPlayer(hit);
    public MenuElements(String fileName) //Creates a JLabel which contains all of the buttons, background etc making up the main menu. A label is used because it allows the background to be an animated GIF by setting it as its icon
    {
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setFocusable(false);
        add(fxPanel);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.play(); //Plays the main menu music at 30% volume
        fxPanel.setFocusable(false);
        w = 10;
        h = 10;
        try {
            StretchIcon image = new StretchIcon(this.getClass().getResource("/media/"+fileName)); //Uses the StretchIcon class created by Darryl Burke so that the GIF fills the label and stretches when it's resized
            this.setIcon(image); //StretchIcon is available from https://tips4java.wordpress.com/2012/03/31/stretch-icon/
            this.setVisible(true);
        } catch (Exception e) {
            System.out.println("Background file not found");
        }
        this.setLayout(new GridBagLayout()); //Uses GridBagLayout so that the title and buttons can be laid precisely and neatly one over the other
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel title1 = new JLabel("Sarmed Alwan's");
        JLabel title2 = new JLabel("OPERATION MARS"); //The title
        title1.setFont(new Font ("BahnSchrift", Font.ITALIC, 14));
        title1.setForeground(Color.RED);
        title2.setForeground(Color.WHITE);
        title2.setFont(new Font ("BahnSchrift", Font.BOLD, 80));
        JButton startGame = new JButton("Join Game"); //Allows a user to enter an IP to join a hosted game
        startGame.addActionListener(this);
        startGame.setActionCommand("start");
        JButton hostGame = new JButton("Host Game"); //Allows a user to start a server which hosts a game
        hostGame.addActionListener(this);
        hostGame.setActionCommand("host");
        JButton credits = new JButton("Credits"); //Shows the game credits
        credits.addActionListener(this);
        credits.setActionCommand("credits");
        JButton quitGame = new JButton("Quit"); //Closes the program
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
        this.add(quitGame, gbc); //Places all of the menu elements one on top of the other using GridBagConstraints, essentially coordinates on a grid
    }

    public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
            try {
                if(!GameState.getServerJoined()) {
                    String ip = JOptionPane.showInputDialog("Enter IP Address of Host: "); //Message comes up with an input box and asks user for the IP. If the connection fails, the main frame isn't started and the media player isn't stopped
                    new MainFrame("Operation Mars", ip);
                    mediaPlayer.stop();
                } else{ //Tells the user that they've already joined a server if they click the Join Game button more than once
                    JOptionPane.showMessageDialog(null, "Game already joined", "Game Already Joined", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e2){
                JOptionPane.showMessageDialog(null, "Could not connect to the specified server. Please enter a valid IP address", "Connection Failed", JOptionPane.INFORMATION_MESSAGE);
            } //If the connection fails, the user is notified by a message box
        } else if ("host".equals(e.getActionCommand())) {
            if(!GameState.getServerHosted()) {
                new Server().start(); //If the user clicks Host, a game server is started and they're notified
                GameState.setServerHosted(true); //Saves that a server has been hosted, so they can't try to host twice
                JOptionPane.showMessageDialog(null, "You are now hosting a game. To join your own game, click Join Game and press Enter", "Game Hosted", JOptionPane.INFORMATION_MESSAGE);
            } else{ //Tells the user that they're already hosting a server if they click the Host Game button more than once
                JOptionPane.showMessageDialog(null, "Game already hosted. To join your own game, click Join Game and press Enter", "Game Already Hosted", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if ("quit".equals(e.getActionCommand())) {
            System.exit(0); //Closes the program if they click Quit
        } else if ("credits".equals(e.getActionCommand())) {
            openCredits(); //Shows the credits if they click Credits
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(1104, 832);
    } //Defines the dimensions, set to the size of the animated GIF

    public void openCredits(){
        JPanel creditsPanel = new JPanel(); //Shows the credits in a message dialog, and lays them out in much the same way as the main menu is laid out
        JLabel dev = new JLabel("Design and Programming");
        JLabel dev1 = new JLabel("Sarmed Alwan");
        JLabel art = new JLabel("Art");
        JLabel art1 = new JLabel("Sarmed Alwan");
        JLabel music = new JLabel("Sound");
        JLabel music1 = new JLabel("Bensound");
        JLabel music2 = new JLabel("Richard Ampleford");
        JLabel music3 = new JLabel("freesound.org");
        JLabel video = new JLabel("Main Menu Video");
        JLabel video1 = new JLabel("publicdomaintorrents.info");
        dev.setFont(new Font("BahnSchrift", Font.BOLD, 25));
        art.setFont(new Font("BahnSchrift", Font.BOLD, 25));
        music.setFont(new Font("BahnSchrift", Font.BOLD, 25));
        video.setFont(new Font("BahnSchrift", Font.BOLD, 25));
        creditsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        creditsPanel.add(dev, gbc); //Places all of the text one on top of the other using the GridBagLayout grid system
        gbc.gridx = 0;
        gbc.gridy = 2;
        creditsPanel.add(dev1,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        creditsPanel.add(art,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        creditsPanel.add(art1,gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        creditsPanel.add(music,gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        creditsPanel.add(music1,gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        creditsPanel.add(music2,gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        creditsPanel.add(music3,gbc);
        gbc.gridx = 0;
        gbc.gridy = 9;
        creditsPanel.add(video,gbc);
        gbc.gridx = 0;
        gbc.gridy = 10;
        creditsPanel.add(video1,gbc);
        creditsPanel.setBackground(Color.white); //Makes the background of the credits panel white rather than the default grey

        UIManager UI=new UIManager();
        UI.put("OptionPane.background", Color.white); //Makes the message box surrounding the credits panel also white
        UI.put("Panel.background", Color.white); //Makes the area around the Ok button white
        JOptionPane.showMessageDialog(this, creditsPanel, "Credits", JOptionPane.PLAIN_MESSAGE); //Displays the credits
    }
}
