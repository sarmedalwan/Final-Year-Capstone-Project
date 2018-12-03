package Project;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sarmed on 23/11/2018.
 */
public class MainPanel extends JPanel {

    public MainPanel()
    {
        this.setFocusable(true);
        this.grabFocus();
        UnitPanel unitPanel = new UnitPanel(GameState.getNewBoard(), 1);
        TerritoriesPanel territoriesPanel = new TerritoriesPanel();
        Grid grid = new Grid();
        Background background = new Background();
        this.add(BorderLayout.CENTER, unitPanel);
        this.add(BorderLayout.CENTER, territoriesPanel);
        this.add(BorderLayout.CENTER, grid);
        this.add(BorderLayout.CENTER, background);
    }
}
