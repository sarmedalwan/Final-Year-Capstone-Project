package Project;

/**
 * Created by Sarmed on 10/11/2018.
 */

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

public class UnitPanel extends JPanel
{
    int w;
    int h;
    int faction;
    float alpha = 1.0f;
    float alpha2 = 1.0f;
    Unit hurt1 = null;
    Unit hurt2 = null;
    Unit combatant1 = null;
    Unit combatant2 = null;
    static int size = 60;
    static ArrayList<ArrayList<Unit>> gameGrid;
    Unit selected;
    Color transparent = new Color(0,0,0,0);
    int[][] territories;
    TerritoriesPanel territoriesPanel;
    MainFrame frame;
    MainPanel mainPanel;
    final JFXPanel fxPanel = new JFXPanel();
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
    MediaPlayer artMoveSoundPlayer = new MediaPlayer(artMoveSound);

    class Listener extends MouseInputAdapter {
        UnitPanel panel;

        Listener(UnitPanel panel){
            this.panel = panel;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            faction = GameState.getFaction();
            if (GameState.getLastMovedPlayer() == faction){
                System.out.println("It isn't your turn");
            } else {
                int x = (e.getX() / size);
                int y = (e.getY() / size);
                int oldX;
                int oldY;
                System.out.println(x + ", " + y);
                if (gameGrid.get(x).get(y) == null) {
                    System.out.println("empty");
                }
                gameGrid = GameState.getBoard();
                //copyBoard();
                faction = GameState.getFaction();
                System.out.println("faction: " + faction);
                for (int i = 0; i < GameState.width; i++) {
                    for (int j = 0; j < GameState.height; j++) {
                        if (gameGrid.get(i).get(j) != null && gameGrid.get(i).get(j).getSelection()) {
                            try {
                                selected = new Unit(gameGrid.get(i).get(j));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            System.out.println("Selected unit: " + selected.getName());
                            System.out.println(selected.getHealth());
                        }
                    }
                }
                if (gameGrid.get(x).get(y) != null) {
                    System.out.println(gameGrid.get(x).get(y).getName());
                    Unit selectedUnit = null;
                    if (currentSelected(gameGrid) != null) {
                        selectedUnit = currentSelected(gameGrid);
                    }
                    if (currentSelected(gameGrid) != null) {
                        System.out.println("Unit " + selectedUnit.getName() + " is already selected");
                    } else if (gameGrid.get(x).get(y).getFaction() == faction) {
                        gameGrid.get(x).get(y).setSelection(true);
                        System.out.println(gameGrid.get(x).get(y).getName() + " selection set true");
                    }
                }
                if (selected != null && (gameGrid.get(x).get(y) == null || ((gameGrid.get(x).get(y) != null && (gameGrid.get(x).get(y).getFaction() != faction))) || ((gameGrid.get(x).get(y) != null && (gameGrid.get(x).get(y).getFaction() == faction))))) {
                    try {
                        if(GameState.isLegal(selected, x, y)) {
                            if (gameGrid.get(x).get(y) == null) { //moving to empty square
                                combatant1 = null;
                                combatant2 = null;
                                oldX = selected.getxLocation();
                                oldY = selected.getyLocation();
                                selected.setxLocation(x);
                                selected.setyLocation(y);
                                gameGrid.get(x).set(y, new Unit(selected));
                                gameGrid.get(oldX).remove(oldY);
                                gameGrid.get(oldX).add(oldY, null);
                                //System.out.println("moved to: " + selected.getxLocation() + " " + selected.getyLocation());
                                territories = Arrays.copyOf(territoriesPanel.getTerritories(), territoriesPanel.getTerritories().length);
                                territories[x][y] = selected.getFaction() - 1;
                                territoriesPanel.updateTerritories(territories);
                                playMoveSound(selected);
                            } else if (gameGrid.get(x).get(y).getFaction() != faction) {
                                try {                            //attacking an enemy unit
                                    //System.out.println("Combat!");
                                    territories = Arrays.copyOf(territoriesPanel.getTerritories(), territoriesPanel.getTerritories().length);
                                    GameState.combat(gameGrid.get(selected.getxLocation()).get(selected.getyLocation()), gameGrid.get(x).get(y), territories);
                                    System.out.println(selected.getHealth());
                                    System.out.println(gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).getHealth());
                                    System.out.println(gameGrid.get(x).get(y).getHealth());
                                    //revalidate();
                                    getCombatants(selected, gameGrid.get(x).get(y));
                                    repaint();
                                    if (gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).getHealth() < 1) {
                                        if (gameGrid.get(x).get(y)!=null && gameGrid.get(x).get(y).getVet()<6) {
                                            gameGrid.get(x).get(y).setVet(gameGrid.get(x).get(y).getVet() + 1);
                                        }
                                        gameGrid.get(selected.getxLocation()).remove(selected.getyLocation());
                                        gameGrid.get(selected.getxLocation()).add(selected.getyLocation(), null);
                                    }
                                    if (gameGrid.get(x).get(y).getHealth() < 1) {
                                        if (gameGrid.get(selected.getxLocation()).get(selected.getyLocation())!=null && gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).getVet()<6) {
                                            gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).setVet(gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).getVet() + 1);
                                        }
                                        gameGrid.get(x).remove(y);
                                        gameGrid.get(x).add(y, null);
                                    }
                                    playCombatSound(selected);
                                } catch (Exception h) {
                                    h.printStackTrace();
                                }
                            } else { //swap
                                oldX = selected.getxLocation();
                                oldY = selected.getyLocation();
                                selected.setxLocation(x);
                                selected.setyLocation(y);
                                Unit temp = new Unit(gameGrid.get(x).get(y));
                                temp.setxLocation(oldX);
                                temp.setyLocation(oldY);
                                //gameGrid.get(oldX).remove(oldY);
                                gameGrid.get(oldX).set(oldY, new Unit(temp));
                                gameGrid.get(x).set(y, new Unit(selected));
                                //gameGrid.get(x).add(new Unit(selected));
                                territories = Arrays.copyOf(territoriesPanel.getTerritories(), territoriesPanel.getTerritories().length);
                                territories[x][y] = selected.getFaction() - 1;
                                territories[oldX][oldY] = temp.getFaction() - 1;
                                territoriesPanel.updateTerritories(territories);
                            }
                            deselect();
                            GameState.updateBoard(gameGrid);
                            alpha2 = 1.0f;
                            ClientThread.makeMove(GameState.getBoard());
                            alpha = 1.0f;
                            GameState.setLastMovedPlayer(faction);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                GameState.updateBoard(gameGrid);
                repaint();
                selected = null;
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
        public void mouseMoved(MouseEvent e) {
            try {
                int x = (e.getX() / size);
                int y = (e.getY() / size);
                JComponent component = (JComponent) e.getSource();
                if (gameGrid.get(x).get(y) != null) {
                    component.setFont(new Font("BahnSchrift", Font.BOLD, 12));
                    String fontFamily = component.getFont().getFamily();
                    component.setToolTipText("<html><body style=\"font-family:" + fontFamily + "\"<b>" + gameGrid.get(x).get(y).getName() + "<br>" + "Health: " + gameGrid.get(x).get(y).getHealth() + "<br>" + "Veterancy: " + gameGrid.get(x).get(y).getVet() + "</b></html>");
                    MouseEvent phantom = new MouseEvent(component, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, e.getX(), e.getY(), 0, false);
                    ToolTipManager.sharedInstance().mouseMoved(phantom);
                } else {
                    component.setToolTipText(null);
                }
            } catch (Exception e1){
                System.out.println("Mouse out of bounds");
            }
        }
    }

    public UnitPanel(ArrayList<ArrayList<Unit>> gameGrid, int faction, TerritoriesPanel territoriesPanel, MainFrame frame, MainPanel mainPanel)
    {
        UIManager.put("ToolTip.background", Color.BLACK);
        UIManager.put("ToolTip.foreground", Color.WHITE);
        this.setFocusable(true);
        this.grabFocus();
        this.gameGrid = gameGrid;
        this.frame = frame;
        this.mainPanel = mainPanel;
        w = 10;
        h = 10; //Defines the dimensions of the grid
        System.out.println("newfaction: "+faction);
        this.faction = faction;
        GameState.setFaction(this.faction);
        this.territoriesPanel = territoriesPanel;
        Listener listener = new Listener(this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        this.requestFocus();
        this.setBackground(transparent);
        this.setOpaque(false);
        GameState.setLastMovedPlayer(2);
        double effectVolume = 0.7;
        turnSoundPlayer.setVolume(effectVolume);
        infSoundPlayer.setVolume(effectVolume);
        armSoundPlayer.setVolume(effectVolume);
        artSoundPlayer.setVolume(effectVolume);
        infMoveSoundPlayer.setVolume(effectVolume);
        armMoveSoundPlayer.setVolume(effectVolume);
        artMoveSoundPlayer.setVolume(effectVolume);
    }

    public void paintComponent(Graphics g) {
        //copyBoard();
        Graphics2D g2d = (Graphics2D) g.create();
        Graphics2D g2d2 = (Graphics2D) g.create();
        setOpaque(false);
        gameGrid = GameState.getBoard();
        try {
                for (int i = 0; i < GameState.width; i++) {
                    for (int j = 0; j < GameState.height; j++) {
                        if (gameGrid.get(i).get(j) != null && gameGrid.get(i).get(j).getxLocation() != 30 && gameGrid.get(i).get(j).getHealth() > 0) {
                            if (gameGrid.get(i).get(j).getSelection()) {
                                g.setColor(Color.WHITE);
                                g.fillRoundRect((gameGrid.get(i).get(j).getxLocation() * size) + 8, (gameGrid.get(i).get(j).getyLocation() * size) + 8, 44, 44, 10, 10);
                            }
                            g.drawImage(gameGrid.get(i).get(j).getIcon(), (gameGrid.get(i).get(j).getxLocation() * size) + 10, (gameGrid.get(i).get(j).getyLocation() * size) + 10, 40, 40, null); //Draws each Unit object's corresponding icon
                            //g.setColor(Color.GREEN);
                            //g.drawLine((gameGrid.get(i).get(j).getxLocation() * size) + 15, (gameGrid.get(i).get(j).getyLocation() * size) + 15, (gameGrid.get(i).get(j).getxLocation() * size) + 15 + (gameGrid.get(i).get(j).getHealth()/3), (gameGrid.get(i).get(j).getyLocation() * size) + 15);
                            g2d.setColor(new Color(127,255,0));
                            g2d.setStroke(new BasicStroke(2));
                            g2d.draw(new Line2D.Float((gameGrid.get(i).get(j).getxLocation() * size) + 16, (gameGrid.get(i).get(j).getyLocation() * size) + 12, (gameGrid.get(i).get(j).getxLocation() * size) + 16 + (gameGrid.get(i).get(j).getHealth()/3.5f), (gameGrid.get(i).get(j).getyLocation() * size) + 12));
                            if (gameGrid.get(i).get(j).getVet()>0) {
                                if (gameGrid.get(i).get(j).getVet()<=2) {
                                    g2d.setColor(new Color(214,175,54)); //Bronze
                                } else if (gameGrid.get(i).get(j).getVet()<=4) {
                                    g2d.setColor(new Color(212,212,212)); //Silver
                                } else if (gameGrid.get(i).get(j).getVet()>4) {
                                    g2d.setColor(new Color(255, 223, 0)); //Gold
                                }
                                    //int fontSize = gameGrid.get(i).get(j).getVet()*10;
                                    g2d.setFont(new Font("BahnSchrift", Font.BOLD, 12));
                                    g2d.drawString(Integer.toString(gameGrid.get(i).get(j).getVet()), (gameGrid.get(i).get(j).getxLocation() * size) + 12, (gameGrid.get(i).get(j).getyLocation() * size) + 48);
                            }
                        }
                    }
                }
            //System.out.println(GameState.getLastMovedPlayer()!=GameState.getFaction());
            if(GameState.getLastMovedPlayer()!=GameState.getFaction()) {
                    AlphaComposite alCom = AlphaComposite.getInstance(
                            AlphaComposite.SRC_OVER, alpha);
                    g2d.setPaint(Color.WHITE);
                    g2d.setComposite(alCom);
                    g2d.setFont(new Font("BahnSchrift", Font.BOLD, 50));
                    if (hurt1!=null && hurt2 != null){
                        BufferedImage explosion = ImageIO.read(getClass().getResource("/media/explosion.png"));
                        g2d.drawImage(explosion, ((((hurt1.getxLocation()*size)+(hurt2.getxLocation()*size)))/2)+10, ((((hurt1.getyLocation() * size)+(hurt2.getyLocation()*size)))/2)+10, 40, 40, null);
                    }
                    g2d.drawString("YOUR TURN", 155, 310);
                    if (faction == 1) {
                        frame.setTitle("Operation Mars | Faction: Soviets | Your Turn");
                    } else if (faction == 2) {
                        frame.setTitle("Operation Mars | Faction: Germans | Your Turn");
                    }
                    alpha -= 0.02f;
                    if (alpha <= 0.0f) {
                        alpha = 0.0f;
                    } else {
                        repaint();
                    }
                    try {
                        Thread.sleep(70);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            } else {
                if (faction == 1) {
                    frame.setTitle("Operation Mars | Faction: Soviets | Opponent's Turn");
                } else if (faction == 2) {
                    frame.setTitle("Operation Mars | Faction: Germans | Opponent's Turn");
                }
            }
            if (combatant1!=null && combatant2 != null){
                AlphaComposite alcom = AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, alpha2);
                g2d2.setComposite(alcom);
                BufferedImage explosion = ImageIO.read(getClass().getResource("/media/explosion.png"));
                g2d2.drawImage(explosion, ((((combatant1.getxLocation()*size)+(combatant2.getxLocation()*size)))/2)+10, ((((combatant1.getyLocation() * size)+(combatant2.getyLocation()*size)))/2)+10, 40, 40, null);
                alpha2 -= 0.02f;
                if (alpha2 <= 0.0f) {
                    alpha2 = 0.0f;
                } else {
                    repaint();
                }
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception e){
            System.out.println("In drawing method");
            System.out.println(e);
        }
        this.requestFocus();
    }

    public void copyBoard() {
        gameGrid = GameState.getBoard();
        /*for (int i = 0; i < GameState.width; i++) {
            for (int j = 0; j < GameState.height; j++) {
                if(GameState.getBoard().get(i).get(j) != null) {
                    try {
                        gameGrid = GameState.getBoard();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }*/
    }

    public void deselect() {
        for (int i = 0; i < GameState.width; i++) {
            for (int j = 0; j < GameState.height; j++) {
                if(GameState.getBoard().get(i).get(j) != null) {
                    gameGrid.get(i).get(j).setSelection(false);
                }
            }
        }
    }

    public void getCombatants(Unit attacker, Unit defender){
        try{
            combatant1 = new Unit(attacker);
            combatant2 = new Unit(defender);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateGrid(ArrayList<ArrayList<Unit>> newGrid){
        hurt1 = null;
        hurt2 = null;
        for (int i = 0; i < GameState.width; i++) {
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
                    newGrid.get(i).get(j).setIcon();
                }
                gameGrid.get(i).set(j, newGrid.get(i).get(j));
                //gameGrid.get(i).add(j, newGrid.get(i).get(j));
            }
        }
        turnSoundPlayer.seek(new Duration(0));
        turnSoundPlayer.play();
        repaint();
    }

    public void updateTerritories(){
        territories = Arrays.copyOf(territoriesPanel.getTerritories(), territoriesPanel.getTerritories().length);
        for (int i = 0; i < GameState.width; i++) {
            for (int j = 0; j < GameState.height; j++) {
                if(gameGrid.get(i).get(j) != null) {
                    territories[i][j] =
                            gameGrid.get(i).get(j).getFaction() - 1;
                }
            }
        }
        territoriesPanel.updateTerritories(territories);
    }

    public void playCombatSound(Unit attacker){
        if (attacker.getType().equals("inf")) {
            infSoundPlayer.seek(new Duration(0));
            infSoundPlayer.play();
        } else if (attacker.getType().equals("arm")) {
            armSoundPlayer.seek(new Duration(0));
            armSoundPlayer.play();
        } else if (attacker.getType().equals("art")) {
            artSoundPlayer.seek(new Duration(0));
            artSoundPlayer.play();
        }
    }

    public void playMoveSound(Unit selected) {
        if (selected.getType().equals("inf")) {
          infMoveSoundPlayer.seek(new Duration(0));
          infMoveSoundPlayer.play();
        } else if (selected.getType().equals("arm")) {
            armMoveSoundPlayer.seek(new Duration(0));
            armMoveSoundPlayer.play();
        } else if (selected.getType().equals("art")) {
            artMoveSoundPlayer.seek(new Duration(0));
            artMoveSoundPlayer.play();
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size, h * size);
    }
}