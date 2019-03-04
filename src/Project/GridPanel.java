package Project;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel //Provides a panel with the game grid lines on it
{
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
                g.drawRect(i*size*5,j*size*5, 60, 60); //Draws a 10x10 array of grid squares to show the grid lines to the user
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size*5, h * size*5);
    } //Defines the dimensions of the grid
}
