package Project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Color.*;

public class Background extends JPanel
{
    int w, h;
    static int size = 12;
    static BufferedImage image;
    String fileName;

    public Background(String fileName)
    {
        w = 10;
        h = 10;
        this.fileName = fileName;
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try {
            image = ImageIO.read(getClass().getResource("/media/"+this.fileName));
        } catch (Exception e) {
            System.out.println("Background file not found");
        }
        g.drawImage(image, 0, 0, null);
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size*5, h * size*5);
    } //Defines the dimensions
}
