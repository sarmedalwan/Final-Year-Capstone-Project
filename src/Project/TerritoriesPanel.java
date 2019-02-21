package Project;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TerritoriesPanel extends JPanel
{
    static Color[] colors =
            {new Color(200, 0,0, 80), new Color(200, 200, 200, 80)};
    public int[][] territories;
    int w;
    int h;
    static int size = 12;

    public TerritoriesPanel(int[][] owners)
    {
        w = 10;
        h = 10;
        this.territories = owners;
    }

    public void updateTerritories(int[][] owners){
        territories = Arrays.copyOf(owners, owners.length);
    }

    public void rePaint(){
        repaint();
    }

    public int[][] getTerritories(){
        return territories;
    }

    public void paintComponent(Graphics g) {

        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                g.setColor(colors[territories[i][j]]);
                g.fill3DRect(i * size*5, j * size*5, 60, 60, true); //Sets all tile colours
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size*5, h * size*5);
    } //Defines the dimensions of the game
}
