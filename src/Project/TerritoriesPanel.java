package Project;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TerritoriesPanel extends JPanel //Renders the coloured grid showing which player controls which tiles
{
    static Color[] colors =
            {new Color(200, 0,0, 80), new Color(200, 200, 200, 80)}; //Red and grey, with transparency
    public int[][] territories;
    int w;
    int h;
    static int size = 12;

    public TerritoriesPanel(int[][] owners)
    {
        w = 10; //Width and height of the grid
        h = 10;
        this.territories = owners;
    }

    public void updateTerritories(int[][] owners){
        territories = Arrays.copyOf(owners, owners.length);
    }

    public int[][] getTerritories(){
        return territories;
    }

    public void paintComponent(Graphics g) {

        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                g.setColor(colors[territories[i][j]]); //If it's a 0, draw red. If it's a 1, draw grey
                g.fill3DRect(i * size*5, j * size*5, 60, 60, true); //Draws the tiles in their locations
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size*5, h * size*5);
    } //Defines the dimensions of the territory grid
}
