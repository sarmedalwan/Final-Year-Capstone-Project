package Project;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sarmed on 23/11/2018.
 */
public class MainPanel extends JPanel {
    MainFrame frame;
    public MainPanel(MainFrame frame)
    {
        this.frame = frame;
        this.setFocusable(true);
        this.grabFocus();
        TerritoriesPanel territoriesPanel = new TerritoriesPanel(GameState.getNewTerritories());
        UnitPanel unitPanel = new UnitPanel(GameState.getNewBoard(), 1, territoriesPanel, frame, this);
        GridPanel gridPanel = new GridPanel();
        Background background = new Background("operationmarsmapbigger.png");
        this.add(BorderLayout.CENTER, unitPanel);
        this.add(BorderLayout.CENTER, territoriesPanel);
        this.add(BorderLayout.CENTER, gridPanel);
        this.add(BorderLayout.CENTER, background);
    }
}
