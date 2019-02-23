package Project;

/**
 * Created by Sarmed on 10/11/2018.
 */

import sun.font.FontFamily;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static Project.Selection.*;
import static java.awt.MouseInfo.getPointerInfo;

public class UnitPanel extends JPanel
{
    int w;
    int h;
    int faction;
    static int size = 60;
    static ArrayList<ArrayList<Unit>> gameGrid;
    Unit selected;
    Color transparent = new Color(0,0,0,0);
    int[][] territories;
    TerritoriesPanel territoriesPanel;
    MainFrame frame;
    MainPanel mainPanel;

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
                        GameState.updateBoard(gameGrid);
                        System.out.println(gameGrid.get(x).get(y).getName() + " selection set true");
                    }
                }
                if (selected != null && (gameGrid.get(x).get(y) == null || ((gameGrid.get(x).get(y) != null && (gameGrid.get(x).get(y).getFaction() != faction))))) {
                    try {
                        if (gameGrid.get(x).get(y) == null) { //moving to empty square
                            oldX = selected.getxLocation();
                            oldY = selected.getyLocation();
                            selected.setxLocation(x);
                            selected.setyLocation(y);
                            gameGrid.get(x).set(y, new Unit(selected));
                            gameGrid.get(oldX).remove(oldY);
                            gameGrid.get(oldX).add(oldY, null);
                            System.out.println("moved to: " + selected.getxLocation() + " " + selected.getyLocation());
                            GameState.updateBoard(gameGrid);
                            territories = Arrays.copyOf(territoriesPanel.getTerritories(), territoriesPanel.getTerritories().length);
                            territories[x][y] = selected.getFaction() - 1;
                            territoriesPanel.updateTerritories(territories);
                        } else {
                            try {                             //attacking an enemy unit
                                System.out.println("Combat!");
                                GameState.combat(gameGrid.get(selected.getxLocation()).get(selected.getyLocation()), gameGrid.get(x).get(y));
                                System.out.println(selected.getHealth());
                                System.out.println(gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).getHealth());
                                System.out.println(gameGrid.get(x).get(y).getHealth());
                                revalidate();
                                repaint();
                                if (gameGrid.get(selected.getxLocation()).get(selected.getyLocation()).getHealth() < 1) {
                                    gameGrid.get(selected.getxLocation()).remove(selected.getyLocation());
                                    gameGrid.get(selected.getxLocation()).add(selected.getyLocation(), null);
                                }
                                if (gameGrid.get(x).get(y).getHealth() < 1) {
                                    gameGrid.get(x).remove(y);
                                    gameGrid.get(x).add(y, null);
                                }
                            } catch (Exception h) {
                                h.printStackTrace();
                            }
                        }
                        //selected = null;
                        ClientThread.makeMove(GameState.getBoard());
                        GameState.setLastMovedPlayer(faction);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                GameState.updateBoard(gameGrid);
                //revalidate();
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
            int x = (e.getX() / size);
            int y = (e.getY() / size);
            JComponent component = (JComponent) e.getSource();
            if (gameGrid.get(x).get(y) != null) {
                component.setFont(new Font("Segoe", Font.BOLD, 12));
                String fontFamily = component.getFont().getFamily();
                component.setToolTipText("<html><body style=\"font-family:" + fontFamily + "\"<b>" + gameGrid.get(x).get(y).getName()  + "<br>" + "Health: " + gameGrid.get(x).get(y).getHealth() + "</b></html>");
                MouseEvent phantom = new MouseEvent(component, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, e.getX(), e.getY(), 0, false);
                ToolTipManager.sharedInstance().mouseMoved(phantom);
            } else{
                component.setToolTipText(null);
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
        this.territoriesPanel = territoriesPanel;
        Listener listener = new Listener(this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        this.requestFocus();
        this.setBackground(transparent);
        this.setOpaque(false);
    }

    public void paintComponent(Graphics g) {
        //copyBoard();
        setOpaque(false);
        gameGrid = GameState.getBoard();
        try {
            for (int i = 0; i < GameState.width; i++) {
                for (int j = 0; j < GameState.height; j++) {
                    if(gameGrid.get(i).get(j) != null && gameGrid.get(i).get(j).getxLocation()!=30&&gameGrid.get(i).get(j).getHealth()>0) {
                        if (gameGrid.get(i).get(j).getSelection()){
                            g.setColor(Color.WHITE);
                            g.fillRoundRect((gameGrid.get(i).get(j).getxLocation() * size) + 8, (gameGrid.get(i).get(j).getyLocation() * size) + 8, 44, 44, 10, 10);
                        }
                        g.drawImage(gameGrid.get(i).get(j).getIcon(), (gameGrid.get(i).get(j).getxLocation() * size) + 10, (gameGrid.get(i).get(j).getyLocation() * size) + 10, 40, 40, null); //Draws each Unit object's corresponding icon
                    }
                }
            }
        } catch(Exception e){
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

    public void updateGrid(ArrayList<ArrayList<Unit>> newGrid){
        for (int i = 0; i < GameState.width; i++) {
            for (int j = 0; j < GameState.height; j++) {
                if (newGrid.get(i).get(j) != null) {
                    newGrid.get(i).get(j).setIcon();
                    newGrid.get(i).get(j).setSelection(false);
                    //gameGrid.get(i).set(j, null);
                }
                gameGrid.get(i).remove(j);
                gameGrid.get(i).add(j, newGrid.get(i).get(j));
                //gameGrid.get(i).get(j).setSelection(false);
            }
        }
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

    public Dimension getPreferredSize() {
        return new Dimension(w * size, h * size);
    }
}