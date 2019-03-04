package Project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

class Unit implements Serializable {
    private transient BufferedImage icon; //Transient means the icon of the unit isn't serialised by Gson, which would cause issues
    private String iconFileName; //File name of the icon
    private String name; //Name of the unit
    private int xLocation; //Unit's current x and y coordinates
    private int yLocation;
    private int health; //Unit's health, starting at 100
    private int vet; //Unit's veterancy (how experienced it is)
    private String type; //Type of unit; infantry, armour or artillery
    private int faction; //Which player the unit belongs to
    public boolean selected; //Whether or not the unit is selected

    Unit(String name, String iconFileName, int xLocation, int yLocation, String type, int faction, int vet, boolean selected) throws IOException { //Constructor for the Unit object which is then stored in a 2D ArrayList
        this.iconFileName = iconFileName;
        this.icon = ImageIO.read(getClass().getResource("/media/"+this.iconFileName+".png")); //Gets the icon using the file name
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.type = type;
        this.health = 100; //Starts the health at 100
        this.name = name;
        this.faction = faction;
        this.selected = selected;
        this.vet = vet;
    }

    Unit(Unit unit) throws IOException { //Copying constructor; allows one unit to be created from another
        this(unit.getName(), unit.getIconFileName(), unit.getxLocation(), unit.getyLocation(), unit.getType(), unit.getFaction(), unit.getVet(), unit.getSelection());
        this.icon = ImageIO.read(getClass().getResource("/media/"+this.iconFileName+".png"));
        this.health = unit.getHealth();
    }

    //Getters and setters for the unit's stored values
    String getIconFileName(){
        return iconFileName;
    }

    Image getIcon(){
        return icon;
    }

    String getName(){
        return name;
    }

    int getFaction(){
        return faction;
    }

    int getxLocation(){
        return xLocation;
    }

    int getyLocation(){ return yLocation; }

    String getType(){
        return type;
    }

    int getHealth(){
        return health;
    }

    void setxLocation(int location){
        this.xLocation = location;
    }

    void setyLocation(int location){
        this.yLocation = location;
    }

    void setSelection(boolean selected)
    {
        this.selected = selected;
    }

    void setHealth(int newHealth){ this.health = newHealth; }

    boolean getSelection(){ return selected; }

    int getVet(){return vet;}

    void setVet(int newVet){this.vet = newVet;}

    void setIcon(){ //Allows the icon to be re-found from the file name after it has been serialised and the icon has been left behind
        try {
            this.icon = ImageIO.read(getClass().getResource("/media/" + this.iconFileName + ".png"));
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("Couldn't find texture file for a unit");
        }
    }
}