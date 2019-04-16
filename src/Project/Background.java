package Project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends JPanel
{
    int w, h;
    static int size = GameState.getSquarePixelSize();
    static BufferedImage image;
    String fileName;

    public Background(String fileName)
    {
        w = GameState.getWidth(); //Width of the grid
        h = GameState.getHeight(); //Height of the grid
        this.fileName = fileName;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try {
            image = ImageIO.read(getClass().getResource("/media/"+this.fileName)); //Loads the background file specified
        } catch (Exception e) {
            System.out.println("Background file not found");
        }
        g.drawImage(image, 0, 0, null); //Draws the background from the top-left corner
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size, h * size);
    } //Defines the dimensions of the background
}
