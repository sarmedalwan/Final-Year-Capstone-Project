package Project;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class DrawImage {
    public static Image BACKGROUND;
    static {
        try {
            BACKGROUND = ImageManager.loadImage("testbackground");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Image image;

    public DrawImage(Image image) {
        super();
        this.image = image;
    }

    public void drawImage(Graphics g) {
    }
}
