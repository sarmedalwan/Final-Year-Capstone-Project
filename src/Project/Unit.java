package Project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public class Unit implements Serializable {

    private transient BufferedImage icon;
    private String iconFileName;
    private String name;
    private int xLocation;
    private int yLocation;
    private int health;
    private int vet;
    private String type;
    private int faction;
    public boolean selected;

    public Unit(String name, String iconFileName, int xLocation, int yLocation, String type, int faction, int vet, boolean selected) throws IOException {

        this.iconFileName = iconFileName;
        this.icon = ImageIO.read(getClass().getResource("/media/"+this.iconFileName+".png"));
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.type = type;
        this.health = 100;
        this.name = name;
        this.faction = faction;
        this.selected = selected;
        this.vet = vet;
    }

    public Unit(Unit unit) throws IOException {
        this(unit.getName(), unit.getIconFileName(), unit.getxLocation(), unit.getyLocation(), unit.getType(), unit.getFaction(), unit.getVet(), unit.getSelection());
        this.icon = ImageIO.read(getClass().getResource("/media/"+this.iconFileName+".png"));
        this.health = unit.getHealth();
    }

    public String getIconFileName(){
        return iconFileName;
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

    public int getyLocation(){ return yLocation; }

    public String getType(){
        return type;
    }

    public int getHealth(){
        return health;
    }

    public void setxLocation(int location){
        this.xLocation = location;
    }

    public void setyLocation(int location){
        this.yLocation = location;
    }

    public void setSelection(boolean selected)
    {
        this.selected = selected;
    }

    public void setHealth(int newHealth){ this.health = newHealth; }

    public void setIcon(){
        try {
            this.icon = ImageIO.read(getClass().getResource("/media/" + this.iconFileName + ".png"));
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("Couldn't find texture file for a unit");
        }
    }

    public void removeUnit()
    {
        this.xLocation = 30;
        this.yLocation = 30;
    }

    public boolean getSelection(){ return selected; }

    public int getVet(){return vet;}

    public void setVet(int newVet){this.vet = newVet;}
}