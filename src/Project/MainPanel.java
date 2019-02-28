package Project;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sarmed on 23/11/2018.
 */
public class MainPanel extends JPanel {
    MainFrame frame;
    public MainPanel(MainFrame frame, String ip) throws IOException
    {
        this.frame = frame;
        this.setFocusable(true);
        this.grabFocus();
        TerritoriesPanel territoriesPanel = new TerritoriesPanel(GameState.getNewTerritories());
        UnitPanel unitPanel = new UnitPanel(GameState.getNewBoard(), GameState.getFaction(), territoriesPanel, frame, this);
        ClientThread clientThread = new ClientThread(unitPanel, territoriesPanel, ip);
        clientThread.start();
        GridPanel gridPanel = new GridPanel();
        Background background = new Background("operationmarsmap.png");
        this.add(BorderLayout.CENTER, unitPanel);
        this.add(BorderLayout.CENTER, territoriesPanel);
        this.add(BorderLayout.CENTER, gridPanel);
        this.add(BorderLayout.CENTER, background);
        if (GameState.getFaction() == 1) {
            frame.setTitle("Operation Mars | Faction: Soviets | Your Turn");
        } else if (GameState.getFaction() == 2) {
            frame.setTitle("Operation Mars | Faction: Germans | Opponent's Turn");
        }
    }
}