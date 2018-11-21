package Project;

import javax.swing.*;
import java.awt.*;

public class Territories extends JPanel
{
    static Color transparent = new Color(255,255,255,255);
    static Color[] colors =
            {new Color(200, 0,0, 140), new Color(200, 200, 200, 140)};
    int[][] owners;
    int w = 10;
    int h = 10;
    static int size = 12;

    public Territories()
    {
        w = 10;
        h = 10; //Defines the dimensions of the grid
        owners = new int[w][h];
    }


    public void paintComponent(Graphics g) {
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                owners[i][j] = 0;
            }
        }
        for (int i = 0; i<7; i++){
            owners[i][9] = 1;
        }
        for (int i = 1; i<7; i++){
            owners[i][8] = 1;
        }
        for (int i = 1; i<8; i++){
            owners[i][7] = 1;
        }
        for (int i = 1; i<8; i++){
            owners[i][6] = 1;
        }
        for (int i = 2; i<7; i++){
            owners[i][5] = 1;
        }
        for (int i = 2; i<7; i++){
            owners[i][4] = 1;
        }
        for (int i = 1; i<7; i++){
            owners[i][3] = 1;
        }
        for (int i = 2; i<6; i++){
            owners[i][2] = 1;
        }
        owners[2][1] = 1;
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                g.setColor(colors[owners[i][j]]);
                g.fill3DRect(i * size*5, j * size*5, 60, 60, true); //Sets all tile colours
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size*5, h * size*5);
    } //Defines the dimensions of the game
}
