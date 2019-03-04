package Project;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static Project.Selection.*;

public class UnitPanel extends JPanel //The panel in which all of the units are displayed and where their logic is handled
{
int w;
int h;
int faction;
int totalVPs;
int totalEnemyVPs;
float alpha = 1.0f;
float alpha2 = 1.0f;
float alpha3 = 1.0f;
Unit hurt1 = null;
Unit hurt2 = null;
Unit combatant1 = null;
Unit combatant2 = null;
static int size = 60;
static ArrayList<ArrayList<Unit>> gameGrid;
static Boolean won = false;
static Boolean lost = false;
Unit selected;
Color transparent = new Color(0,0,0,0);
int[][] territories;
TerritoriesPanel territoriesPanel;
MainFrame frame;
MainPanel mainPanel;
final JFXPanel fxPanel = new JFXPanel(); //fxPanel needs to be created for the media players to work, even if it isn't used
//Gets all of the sound effects for the game
Media turnSound = new Media(getClass().getResource("/media/yourturn.mp3").toString()); // freesound.org
Media infMoveSound = new Media(getClass().getResource("/media/infmove.mp3").toString()); // freesound.org
Media infSound = new Media(getClass().getResource("/media/inf.mp3").toString()); // freesound.org
Media armSound = new Media(getClass().getResource("/media/arm.mp3").toString());// freesound.org
Media armMoveSound = new Media(getClass().getResource("/media/armmove.mp3").toString());// freesound.org
Media artSound = new Media(getClass().getResource("/media/art.mp3").toString());// freesound.org
Media artMoveSound = new Media(getClass().getResource("/media/artmove.mp3").toString()); // freesound.org
MediaPlayer turnSoundPlayer = new MediaPlayer(turnSound);
MediaPlayer infSoundPlayer = new MediaPlayer(infSound);
MediaPlayer infMoveSoundPlayer = new MediaPlayer(infMoveSound);
MediaPlayer armSoundPlayer = new MediaPlayer(armSound);
MediaPlayer armMoveSoundPlayer = new MediaPlayer(armMoveSound);
MediaPlayer artSoundPlayer = new MediaPlayer(artSound);
MediaPlayer artMoveSoundPlayer = new MediaPlayer(artMoveSound); //Creates a media player for each sound effect so that they are all independently controlled and played

class Listener extends MouseInputAdapter {
    UnitPanel panel;
    Listener(UnitPanel panel){
        this.panel = panel;
    } //Constructor for the MouseInputAdapter; takes the unit panel and operates with it
    @Override
    public void mouseClicked(MouseEvent e) {
        faction = GameState.getFaction();
        if (!(won || lost)) { //If the player hasn't won or lost (if the game hasn't ended)
            if (GameState.getLastMovedPlayer() == faction) { //If this client was the last moved player, they can't move again
                System.out.println("It isn't your turn");
            } else { //Otherwise, the click is processed
                int x = (e.getX() / size);
                int y = (e.getY() / size); //Grid coordinates of the mouse click
                int oldX;
                int oldY; //Will be used to store the previous location of the moved unit
                if (gameGrid.get(x).get(y) == null) {
                    System.out.println("empty");
                }
                gameGrid = GameState.getBoard();
                //copyBoard();
                faction = GameState.getFaction();

                for (int i = 0; i < GameState.width; i++) {
                    for (int j = 0; j < GameState.height; j++) {
                        if (gameGrid.get(i).get(j) != null && gameGrid.get(i).get(j).getSelection()) {
                            try {
                                selected = new Unit(gameGrid.get(i).get(j)); //If a unit is clicked on, save a clone of it as the selected unit
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }

                if (gameGrid.get(x).get(y) != null) {
                    Unit selectedUnit = null; //While this variable is not directly used, this If statement allows the selected unit to be changed immediately rather than first deselecting when you click another unit of yours
                    if (currentSelected(gameGrid) != null) {
                       selectedUnit = currentSelected(gameGrid);
                    }
                    if (currentSelected(gameGrid) == null && gameGrid.get(x).get(y).getFaction() == faction) {
                        gameGrid.get(x).get(y).setSelection(true); //If there currently isn't a selected unit and the unit you've clicked belongs to you, set that unit as selected
                    }
                }

                if (selected != null){
                    //If a unit is selected and you've clicked somewhere
                    try {
                        if (GameState.isLegal(selected, x, y)) { //If the move is legal (within 1 square, allowing diagonal movement for armour units)
                            if (gameGrid.get(x).get(y) == null) { //Moving to an empty (null) square
                                combatant1 = null;
                                combatant2 = null;
                                oldX = selected.getxLocation(); //The old location of the unit
                                oldY = selected.getyLocation();
                                selected.setxLocation(x); //The new location of the unit
                                selected.setyLocation(y);
                                gameGrid.get(x).set(y, new Unit(selected)); //Copy the unit into the new location
                                gameGrid.get(oldX).remove(oldY);
                                gameGrid.get(oldX).add(oldY, null); //Remove the unit from the old location and replace it with null
                                territories = Arrays.copyOf(territoriesPanel.getTerritories(), territoriesPanel.getTerritories().length); //Update the territory board from the stored one
                                territories[x][y] = selected.getFaction() - 1; //Sets the location on the territories panel which the unit just moved to to that unit's faction (-1 because the territories are stores as 0 and 1 rather than 1 and 2)
                                territoriesPanel.updateTerritories(territories); //Store the newly adjusted territories grid in the territories panel
                                playMoveSound(selected); //Play the movement sound effect for the unit that moved

                            } else if (gameGrid.get(x).get(y).getFaction() != faction) { //If the selected unit has been clicked onto an adjacent enemy unit
                                try {                            //Attacking an enemy unit
                                    territories = Arrays.copyOf(territoriesPanel.getTerritories(), territoriesPanel.getTerritories().length); //Update the territories board from the stored one
                                    GameState.combat(gameGrid.get(selected.getxLocation()).get(selected.getyLocation()), gameGrid.get(x).get(y), territories); //Simulate combat between the selected unit and the one it moved onto
                                    setCombatants(selected, gameGrid.get(x).get(y)); //Gets current combatants (allows for the explosion graphical effect to be displayed between the two units)
                                    repaint();
                                    if (gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).getHealth() < 1) { //If the selected (attacking) unit dies
                                        if (gameGrid.get(x).get(y) != null && gameGrid.get(x).get(y).getVet() < 6) { //Add veterancy to the defending unit if it's below the limit of 6
                                            gameGrid.get(x).get(y).setVet(gameGrid.get(x).get(y).getVet() + 1);
                                        }
                                        gameGrid.get(selected.getxLocation()).remove(selected.getyLocation());
                                        gameGrid.get(selected.getxLocation()).add(selected.getyLocation(), null); //Delete the attacking unit
                                        GameState.setEnemyVictoryPoints(GameState.getEnemyVictoryPoints() + 1); //Give the enemy 1 victory point
                                    }
                                    if (gameGrid.get(x).get(y).getHealth() < 1) { //If the defending unit dies
                                        if (gameGrid.get(selected.getxLocation()).get(selected.getyLocation()) != null && gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).getVet() < 6) {
                                            gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).setVet(gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).getVet() + 1);
                                        } //Add veterancy to the attacking unit if it's below the limit of 6
                                        gameGrid.get(x).remove(y);
                                        gameGrid.get(x).add(y, null); //Delete the defending unit
                                        GameState.setVictoryPoints(GameState.getVictoryPoints() + 1); //Give the player 1 victory point
                                    }
                                    playCombatSound(selected); //Play the combat sound for the selected unit
                                } catch (Exception h) {
                                    h.printStackTrace();
                                }

                            } else { //If a friendly adjacent unit is clicked, a swap will take place
                                oldX = selected.getxLocation();
                                oldY = selected.getyLocation(); //Stores location of the selected unit
                                selected.setxLocation(x);
                                selected.setyLocation(y); //Sets the location of the selected unit to the location of the clicked unit
                                Unit temp = new Unit(gameGrid.get(x).get(y)); //Stores the clicked unit in a temporary location
                                temp.setxLocation(oldX);
                                temp.setyLocation(oldY);
                                gameGrid.get(oldX).set(oldY, new Unit(temp)); //Moves the clicked unit into the location of the selected unit
                                gameGrid.get(x).set(y, new Unit(selected)); //Moves the selected unit into the location of the clicked unit
                                territories = Arrays.copyOf(territoriesPanel.getTerritories(), territoriesPanel.getTerritories().length); //Updates the territories board from the stored one
                                territories[x][y] = selected.getFaction() - 1;
                                territories[oldX][oldY] = temp.getFaction() - 1; //Appropriately updates the territories
                                territoriesPanel.updateTerritories(territories); //Saves the territories board back to the territories panel
                            }

                            deselect(); //Deselects whichever unit is selected
                            alpha2 = 1.0f; //Sets the alpha for the explosion image to 1.0 (100%) so it can appear, i.e. be shown to represent the combat which may have occurred
                            GameState.setTurnCount(GameState.getTurnCount() + 1); //Adds 1 to the turn counter
                            totalVPs = GameState.getVictoryPoints() + GameState.getTerritoryVictoryPoints(territories); //Calculates and stores total victory points for the player
                            totalEnemyVPs = GameState.getEnemyVictoryPoints() + GameState.getEnemyTerritoryVictoryPoints(territories); //Calculates and stores total victory points for the opponent
                            if (GameState.getTurnCount() >= 100) { //If the turn limit of 100 turns has been reached
                                if (totalVPs > totalEnemyVPs) { //If the player's victory points are higher than that of the opponent
                                    won = true;
                                    GameState.setFaction(GameState.getFaction() + 10); //Signals to the server that the player has won
                                    alpha3 = 1.0f; //Sets the alpha for the "YOU WON" message to 1.0 (100%) so it can appear
                                    repaint();
                                }
                            }
                            alpha = 1.0f; //Sets the alpha of the "YOUR TURN" message so it can appear again next time
                            GameState.setLastMovedPlayer(GameState.getFaction()); //Records that this player is the last moved player because they've just finished their turn
                            ClientThread.makeMove(GameState.getBoard()); //Sends the move information to the server
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                GameState.updateBoard(gameGrid); //Saves the new board state
                repaint();
                selected = null; //Resets the selected unit
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) { //Enables tooltips in the game via a mouseMoved listener
        try {
            int x = (e.getX() / size); //Coordinates of the mouse
            int y = (e.getY() / size);
            JComponent component = (JComponent) e.getSource(); //Component where the mouse event is taking place
            if (gameGrid.get(x).get(y) != null) { //If the mouse isn't over an empty location
                component.setFont(new Font("BahnSchrift", Font.BOLD, 12));
                String fontFamily = component.getFont().getFamily();
                component.setToolTipText("<html><body style=\"font-family:" + fontFamily + "\"<b>" + gameGrid.get(x).get(y).getName() + "<br>" + "Health: " + gameGrid.get(x).get(y).getHealth() + "<br>" + "Veterancy: " + gameGrid.get(x).get(y).getVet() + "</b></html>");
                //Set the tooltip text for the component to the above, which includes the unit's name, health, and veterancy. I've used embedded HTML to allow me to put line breaks in the tooltip so it isn't just one long line
                MouseEvent phantom = new MouseEvent(component, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, e.getX(), e.getY(), 0, false);
                ToolTipManager.sharedInstance().mouseMoved(phantom);
                //Mouse event which triggers the tooltip to be set
            } else {
                component.setToolTipText(null); //If the mouse is over an empty location, set the tooltip text to null so that it will disappear
            }
        } catch (Exception e1){
            System.out.println("Mouse out of bounds"); //If the user somehow gets the mouse to go outside of the panel but still within the frame, the exception will be caught
        }
    }
}

public UnitPanel(ArrayList<ArrayList<Unit>> gameGrid, int faction, TerritoriesPanel territoriesPanel, MainFrame frame, MainPanel mainPanel) //Constructor for the unit panel
{
    UIManager.put("ToolTip.background", Color.BLACK);
    UIManager.put("ToolTip.foreground", Color.WHITE); //Sets the background of the tooltip to black and the text to white
    this.setFocusable(true);
    this.grabFocus();
    this.gameGrid = gameGrid;
    this.frame = frame;
    this.mainPanel = mainPanel;
    w = 10;
    h = 10; //Defines the dimensions of the grid
    this.faction = faction;
    GameState.setFaction(this.faction);
    this.territoriesPanel = territoriesPanel;
    Listener listener = new Listener(this);
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    this.requestFocus();
    this.setBackground(transparent);
    this.setOpaque(false);
    GameState.setLastMovedPlayer(2); //The last moved player at the start of the game is always 2 so that player 1 always goes first
    double effectVolume = 0.7; //Sets the volume of the sound effects to 0.7 (70%). This can be higher than the music because it only lasts briefly so it isn't so jarring to have it at a moderate level
    turnSoundPlayer.setVolume(effectVolume);
    infSoundPlayer.setVolume(effectVolume);
    armSoundPlayer.setVolume(effectVolume);
    artSoundPlayer.setVolume(effectVolume);
    infMoveSoundPlayer.setVolume(effectVolume);
    armMoveSoundPlayer.setVolume(effectVolume);
    artMoveSoundPlayer.setVolume(effectVolume); //Applies the volume value to the sound effect players
}

public void paintComponent(Graphics g) { //Renders the unit panel
    Graphics2D g2d = (Graphics2D) g.create();
    Graphics2D g2d2 = (Graphics2D) g.create();
    Graphics2D g2d3 = (Graphics2D) g.create();
    setOpaque(false);
    gameGrid = GameState.getBoard(); //Updates the game grid from the stored one
    try {
            for (int i = 0; i < GameState.width; i++) { //Loop through the game grid
                for (int j = 0; j < GameState.height; j++) {
                    if (gameGrid.get(i).get(j) != null && gameGrid.get(i).get(j).getHealth() > 0) { //If a unit exists and has more than 0 health, render it
                        if (gameGrid.get(i).get(j).getSelection()) { //If that unit is selected, put a round rectangle behind it to give it a white outline to show that it's selected
                            g.setColor(Color.WHITE);
                            g.fillRoundRect((gameGrid.get(i).get(j).getxLocation() * size) + 8, (gameGrid.get(i).get(j).getyLocation() * size) + 8, 44, 44, 10, 10);
                        }
                        g.drawImage(gameGrid.get(i).get(j).getIcon(), (gameGrid.get(i).get(j).getxLocation() * size) + 10, (gameGrid.get(i).get(j).getyLocation() * size) + 10, 40, 40, null); //Draws each Unit object's corresponding icon
                        //Draw the unit's icon in its appropriate position and with its correct size
                        g2d.setColor(new Color(127,255,0)); //Colour of the health bar
                        g2d.setStroke(new BasicStroke(2)); //Width of the health bar; 2 pixels
                        g2d.draw(new Line2D.Float((gameGrid.get(i).get(j).getxLocation() * size) + 16, (gameGrid.get(i).get(j).getyLocation() * size) + 12, (gameGrid.get(i).get(j).getxLocation() * size) + 16 + (gameGrid.get(i).get(j).getHealth()/3.5f), (gameGrid.get(i).get(j).getyLocation() * size) + 12));
                        //Draw the health bar with its length based on how much health the unit has

                        if (gameGrid.get(i).get(j).getVet()>0) {
                            if (gameGrid.get(i).get(j).getVet()<=2) {
                                g2d.setColor(new Color(214,175,54)); //Bronze
                            } else if (gameGrid.get(i).get(j).getVet()<=4) {
                                g2d.setColor(new Color(212,212,212)); //Silver
                            } else if (gameGrid.get(i).get(j).getVet()>4) {
                                g2d.setColor(new Color(255, 223, 0)); //Gold
                            } //Set the colour of the veterancy display to correspond with the amount of veterancy the unit has; bronze for 1-2, silver for 3-4, gold for 5-6
                                g2d.setFont(new Font("BahnSchrift", Font.BOLD, 12));
                                g2d.drawString(Integer.toString(gameGrid.get(i).get(j).getVet()), (gameGrid.get(i).get(j).getxLocation() * size) + 12, (gameGrid.get(i).get(j).getyLocation() * size) + 48);
                                //Write the unit's veterancy over the bottom corner of its icon (+48 to Y puts it low down, +12 puts it a little bit to the right)
                        }
                    }
                }
            }
        if(GameState.getLastMovedPlayer()!=GameState.getFaction() && !(won||lost)) { //If the player wasn't the last to move and he has neither won nor lost
                AlphaComposite alCom = AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, alpha);
                g2d.setPaint(Color.WHITE);
                g2d.setComposite(alCom);
                g2d.setFont(new Font("BahnSchrift", Font.BOLD, 50));
                if (hurt1!=null && hurt2 != null){
                    BufferedImage explosion = ImageIO.read(getClass().getResource("/media/explosion.png"));
                    g2d.drawImage(explosion, ((((hurt1.getxLocation()*size)+(hurt2.getxLocation()*size)))/2)+10, ((((hurt1.getyLocation() * size)+(hurt2.getyLocation()*size)))/2)+10, 40, 40, null);
                } //If the opponent attacked a unit, draw an explosion between the opponent's unit and the one they attacked to show what the opponent just did
                g2d.drawString("YOUR TURN", 155, 310); //Write that it's the user's turn
                if (faction == 1) {
                    frame.setTitle("Operation Mars | Faction: Soviets | Your Turn"); //Set the frame title as a reminder that it's their turn
                } else if (faction == 2) {
                    frame.setTitle("Operation Mars | Faction: Germans | Your Turn");
                }
                alpha -= 0.02f; //Reduces the alpha of the Graphics2D object so that the YOUR TURN message and the explosion can slowly fade out
                if (alpha <= 0.0f) { //Once the alpha hits 0, leave it there
                    alpha = 0.0f;
                } else {
                    repaint(); //If it hasn't hit 0 yet, keep repainting until it does
                }
                try {
                    Thread.sleep(70); //Wait 70ms between each reduction in opacity, for a gradual fade out
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        } else {
            if (faction == 1 && GameState.getFaction() != GameState.getLastMovedPlayer()-10) { //Otherwise, set the frame title as a reminder that it's the opponent's turn, as long as they haven't won the game
                frame.setTitle("Operation Mars | Faction: Soviets | Opponent's Turn");
            } else if (faction == 2) {
                frame.setTitle("Operation Mars | Faction: Germans | Opponent's Turn");
            }
        }
        if (combatant1!=null && combatant2 != null){ //If the player just made two units fight
            AlphaComposite alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha2);
            g2d2.setComposite(alcom);
            BufferedImage explosion = ImageIO.read(getClass().getResource("/media/explosion.png"));
            g2d2.drawImage(explosion, ((((combatant1.getxLocation()*size)+(combatant2.getxLocation()*size)))/2)+10, ((((combatant1.getyLocation() * size)+(combatant2.getyLocation()*size)))/2)+10, 40, 40, null);
            //Draw an explosion between the two units
            alpha2 -= 0.02f; //Make the explosion fade out
            if (alpha2 <= 0.0f) {
                alpha2 = 0.0f;
                combatant1 = null;
                combatant2 = null;
            } else {
                repaint();
            }
            try {
                Thread.sleep(70); //Gradual fade
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (won || lost){ //If the player has won or lost
            g2d3.setPaint(Color.WHITE);
            g2d3.setFont(new Font("BahnSchrift", Font.BOLD, 50));
            if (won) {
                g2d3.drawString("YOU WON", 155, 310); //If they've won, put a big message up saying so
            } else {
                g2d3.drawString("YOU LOST", 155, 310); //If they've lost, put a big message up saying so
            }
        }
    } catch(Exception e){
        System.out.println(e);
    }
    this.requestFocus();
}

public void deselect() {
    for (int i = 0; i < GameState.width; i++) {
        for (int j = 0; j < GameState.height; j++) {
            if(GameState.getBoard().get(i).get(j) != null) {
                gameGrid.get(i).get(j).setSelection(false); //Clears the selection from any space on the board if it isn't empty
            }
        }
    }
}

public void setCombatants(Unit attacker, Unit defender){ //Sets the two units which the player just made fight
    try{
        combatant1 = new Unit(attacker);
        combatant2 = new Unit(defender);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void updateGrid(ArrayList<ArrayList<Unit>> newGrid){ //Updates the game grid when the opponent makes a move
    GameState.setTurnCount(GameState.getTurnCount()+1); //Adds to the turn counter once the opponent has moved
    if (GameState.getLastMovedPlayer()>2){ //If the last moved player is above 2, i.e. if the opponent has sent 11 or 12, that's a signal that the opponent won, so you've lost
        lost = true;
        repaint(); //Repaint to show the YOU LOST message
    }
    hurt1 = null;
    hurt2 = null;
    for (int i = 0; i < GameState.width; i++) { //Checks the grid to see if any units got hurt. If they did, an explosion will be rendered between the two to show that the opponent just made them fight
        for (int j = 0; j < GameState.height; j++) {
            if (newGrid.get(i).get(j) != null) {
                if (gameGrid.get(i).get(j) != null) {
                    if (newGrid.get(i).get(j).getHealth() < gameGrid.get(i).get(j).getHealth()) {
                        try {
                            if (hurt1 == null) {
                                hurt1 = new Unit(newGrid.get(i).get(j));
                            } else {
                                hurt2 = new Unit(newGrid.get(i).get(j));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                newGrid.get(i).get(j).setIcon(); //Sets the units' icons based on their icon file locations, since the icons themselves aren't sent over by the server
            }
            gameGrid.get(i).set(j, newGrid.get(i).get(j)); //Copies the new grid into the current grid
        }
    }
    turnSoundPlayer.seek(new Duration(0));
    turnSoundPlayer.play(); //Plays the sound effect to notify the player that it has just become their turn
    repaint();
}

public void updateTerritories(){ //Triggered when the opponent has just moved, to update the territories to reflect the possibly new unit locations
    territories = Arrays.copyOf(territoriesPanel.getTerritories(), territoriesPanel.getTerritories().length);
    for (int i = 0; i < GameState.width; i++) {
        for (int j = 0; j < GameState.height; j++) {
            if(gameGrid.get(i).get(j) != null) {
                territories[i][j] =
                        gameGrid.get(i).get(j).getFaction() - 1; //Updates the territory value on each tile to be correct based on the unit in each location
            }
        }
    }
    territoriesPanel.updateTerritories(territories); //Saves the changes to the territories panel
}

public void playCombatSound(Unit attacker){ //Plays the combat sound effect based on which type of unit the attacker is
    if (attacker.getType().equals("inf")) { //If the attacker was infantry, play the infantry combat sound effect
        infSoundPlayer.seek(new Duration(0));
        infSoundPlayer.play();
    } else if (attacker.getType().equals("arm")) { //If the attacker was armour, play the armour combat sound effect
        armSoundPlayer.seek(new Duration(0));
        armSoundPlayer.play();
    } else if (attacker.getType().equals("art")) { //If the attacker was artillery, play the artillery combat sound effect
        artSoundPlayer.seek(new Duration(0));
        artSoundPlayer.play();
    }
}

public void playMoveSound(Unit selected) { //Plays the movement sound effect based on which type of unit the moved unit is
    if (selected.getType().equals("inf")) {
      infMoveSoundPlayer.seek(new Duration(0)); //If the moved unit was infantry, play the infantry movement sound effect
      infMoveSoundPlayer.play();
    } else if (selected.getType().equals("arm")) {
        armMoveSoundPlayer.seek(new Duration(0)); //If the moved unit was armour, play the armour movement sound effect
        armMoveSoundPlayer.play();
    } else if (selected.getType().equals("art")) {
        artMoveSoundPlayer.seek(new Duration(0)); //If the moved unit was artillery, play the artillery movement sound effect
        artMoveSoundPlayer.play();
    }
}

public Dimension getPreferredSize() {
    return new Dimension(w * size, h * size);
} //Defines the preferred size of the unit panel
}