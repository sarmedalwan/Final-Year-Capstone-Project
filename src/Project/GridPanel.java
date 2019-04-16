package Project;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel //Provides a panel with the game grid lines on it
{
    int w;
    int h;
    static int size = GameState.getSquarePixelSize();

    public GridPanel()
    {
        w = GameState.getWidth();
        h = GameState.getHeight(); //Defines the dimensions of the grid
    }


    public void paintComponent(Graphics g) {
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                g.drawRect(i*size,j*size, size, size); //Draws a 10x10 array of grid squares to show the grid lines to the user
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size, h * size);
    } //Defines the dimensions of the grid
}
