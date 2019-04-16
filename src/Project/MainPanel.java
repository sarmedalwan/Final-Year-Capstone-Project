package Project;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class MainPanel extends JPanel {
    MainFrame frame;
    public MainPanel(MainFrame frame, String ip) throws IOException //Creates the main game panel which contains all of the gameplay panels
    {
        this.frame = frame;
        this.setFocusable(true);
        this.grabFocus();
        TerritoriesPanel territoriesPanel = new TerritoriesPanel(GameState.getNewTerritories()); //Creates a panel to show the players' territories
        UnitPanel unitPanel = new UnitPanel(GameState.getNewBoard(), GameState.getFaction(), territoriesPanel, frame, this); //Creates the panel to show the units
        ClientThread clientThread = new ClientThread(unitPanel, territoriesPanel, ip); //Initialises the thread which communicates with the server on the client's behalf
        clientThread.start(); //Runs the client communication thread
        GridPanel gridPanel = new GridPanel();
        Background background = new Background("operationmarsmap.png"); //Creates the grid and background panels
        this.add(BorderLayout.CENTER, unitPanel); //Adds the panels in this order so that the unit panel is visually on the top (front) and the background is at the back (because this panel was set to OverlayLayout by the MainFrame)
        this.add(BorderLayout.CENTER, territoriesPanel);
        this.add(BorderLayout.CENTER, gridPanel);
        this.add(BorderLayout.CENTER, background);
        if (GameState.getFaction() == 1) {
            frame.setTitle("Operation Mars | Faction: Soviets | Your Turn | Turn: " + GameState.getTurnCount());
        } else if (GameState.getFaction() == 2) {
            frame.setTitle("Operation Mars | Faction: Germans | Opponent's Turn | Turn: " + GameState.getTurnCount()); //Sets the initial titles for the frame. This is done here rather than in the frame because this is after the client thread has received the player number
        }
    }
}