package Project;

/**
 * Created by Sarmed on 10/11/2018.
 */

import javax.swing.*;
import java.awt.*;
import static java.awt.Color.darkGray;

public class GridPanel extends JPanel
{
    static Color transparent = new Color(255,255,255,255);
    static Color[] colors =
            {new Color(128, 0,0,70), darkGray};
    int w;
    int h;
    static int size = 12;

    public GridPanel()
    {
        w = 10;
        h = 10; //Defines the dimensions of the grid
    }


    public void paintComponent(Graphics g) {
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                g.drawRect(i*size*5,j*size*5, 60, 60);
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size*5, h * size*5);
    } //Defines the dimensions of the game
}
