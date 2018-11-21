package Project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Color.*;

public class Background extends JPanel
{
    int[][] a;
    int w, h;
    static int size = 12;
    static BufferedImage image;

    public Background()
    {
        w = 10;
        h = 10; //Defines the dimensions of the grid
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try {
            image = ImageIO.read(getClass().getResource("/media/operationmarsmapbigger.png"));
        } catch (Exception e) {
            System.out.println("File not found");
        }
        g.drawImage(image, 0, 0, null);
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size*5, h * size*5);
    } //Defines the dimensions of the game
}
