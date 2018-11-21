package Project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Unit {

    private Image icon;
    private String name;
    private int xLocation;
    private int yLocation;
    private int health;
    private String type;
    private int faction;
    public boolean selected;

    public Unit(String name, String iconFileName, int xLocation, int yLocation, String type, int faction, boolean selected) throws IOException {

        icon = ImageIO.read(getClass().getResource("/media/"+iconFileName+".png"));
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.type = type;
        health = 100;
        this.name = name;
        this.faction = faction;
        this.selected = selected;
    }

    public Image getIcon(){
        return icon;
    }

    public String getName(){
        return name;
    }

    public int getFaction(){
        return faction;
    }

    public int getxLocation(){
        return xLocation;
    }

    public int getyLocation(){
        return yLocation;
    }

    public void setSelection(boolean selected)
    {
        this.selected = selected;
    }

    //public void paintComponent(Graphics g) {
   //     super.paintComponent(g);
     //   g.drawImage(backgroundImage, 0, 0, this);
 //   }
}