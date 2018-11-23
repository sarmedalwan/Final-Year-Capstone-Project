package Project;

import javax.swing.*;
import java.awt.*;
import static java.awt.Color.darkGray;

public class Grid extends JPanel
{
    int w = 10;
    int h = 10;
    static int size = 60;
    public Grid()
    {
        w = 10;
        h = 10; //Defines the dimensions of the grid
    }


    public void paintComponent(Graphics g) {
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                g.drawRect(i*size,j*size, 60, 60);
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size, h * size);
    } //Defines the dimensions of the game
}
