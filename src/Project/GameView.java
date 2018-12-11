package Project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

import static java.awt.Color.*;

public class GameView extends JComponent
{
    static Color transparent = new Color(255,255,255,255);
    static Color[] colors =
            {black, white, yellow, transparent};
    int[][] a;
    int w, h;
    static int size = 10;
    static BufferedImage image;

    public GameView(int[][] a)
    {
        this.a = a;
        w = a.length;
        h = a[0].length; //Defines the dimensions of the window
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try {
            image = ImageIO.read(getClass().getResource("/media/testbackground.png"));
        } catch (Exception e) {
            System.out.println("File not found");
        }
        g.drawImage(image, 0, 0, this);
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                //g.setColor(colors[gameBoard[i][j]]);
                //g.fill3DRect(i * size*5, j * size*5, 50, 50, true); //Sets all tiles to be transparent squares
                g.drawRect(i*size*5,j*size*5, 50, 50);
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size*5, h * size*5);
    } //Defines the dimensions of the game
}
