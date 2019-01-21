package Project;

/**
 * Created by Sarmed on 10/11/2018.
 */

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
    ArrayList<ArrayList<Unit>> gameGrid;
    Unit selected;
    Color transparent = new Color(0,0,0,0);
    int[][] territories;
    TerritoriesPanel territoriesPanel;
    MainFrame frame;
    MainPanel mainPanel;
    Thread worker;

    class Listener extends MouseInputAdapter {
        UnitPanel panel;

        Listener(UnitPanel panel){
            this.panel = panel;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.grabFocus();
            panel.requestFocus();
            int x = (e.getX()/size);
            int y = (e.getY()/size);
            int oldX;
            int oldY;
            System.out.println(x + ", " + y);
            if(gameGrid.get(x).get(y) == null){
                System.out.println("empty");
            }
            copyBoard();
            for (int i = 0; i < GameState.width; i++) {
                for (int j = 0; j < GameState.height; j++) {
                    if(gameGrid.get(i).get(j) != null && gameGrid.get(i).get(j).getSelection()) {
                        try {
                            selected = new Unit(gameGrid.get(i).get(j));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        System.out.println("Selected unit: " + selected.getName());
                    }
                }
            }
            if (gameGrid.get(x).get(y) != null) {
                System.out.println(gameGrid.get(x).get(y).getName());
                Unit selectedUnit = null;
                if(currentSelected(gameGrid)!=null) {
                    selectedUnit = currentSelected(gameGrid);
                }
                if (selectedUnit != null) {
                    System.out.println("Unit " + selectedUnit.getName() + " is already selected");
                } else {
                    gameGrid.get(x).get(y).setSelection(true);
                    GameState.updateBoard(gameGrid);
                    System.out.println(gameGrid.get(x).get(y).getName() + " selection set true");
                }
            }
            if (selected != null && (gameGrid.get(x).get(y) == null || ((gameGrid.get(x).get(y) != null && (gameGrid.get(x).get(y).getFaction()!=selected.getFaction()))))){
                oldX = selected.getxLocation();
                oldY = selected.getyLocation();
                selected.setxLocation(x);
                selected.setyLocation(y);
                gameGrid.get(x).set(y, selected);
                gameGrid.get(oldX).remove(oldY);
                gameGrid.get(oldX).add(oldY, null);
                GameState.updateBoard(gameGrid);
                territories = Arrays.copyOf(territoriesPanel.getTerritories(), territoriesPanel.getTerritories().length);
                territories[x][y] = selected.getFaction()-1;
                territoriesPanel.updateTerritories(territories);
                territoriesPanel.repaint();
                mainPanel.repaint();
                frame.repaint();
                repaint();
            }
            removeAll();
            revalidate();
            repaint();
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
            if (gameGrid.get(x).get(y) != null) {
                JComponent component = (JComponent) e.getSource();
                component.setToolTipText("<html>" + gameGrid.get(x).get(y).getName()  + "<br>" + "Health: " + gameGrid.get(x).get(y).getHealth() + "</html>");
                MouseEvent phantom = new MouseEvent(component, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, e.getX(), e.getY(), 0, false);
                ToolTipManager.sharedInstance().mouseMoved(phantom);
            }
            /*final Timer timer = new Timer(50, new ActionListener() {
                private int id = 1;

                @Override
                public void actionPerformed(ActionEvent e) {
                    ++id;
                    panel.setToolTipText(gameGrid.get(x).get(y).getName());
                }
            });
            timer.start();
            */
        }
    }

    public UnitPanel(ArrayList<ArrayList<Unit>> gameGrid, int faction, TerritoriesPanel territoriesPanel, MainFrame frame, MainPanel mainPanel)
    {
        this.setFocusable(true);
        this.grabFocus();
        this.gameGrid = GameState.getBoard();
        this.frame = frame;
        this.mainPanel = mainPanel;
        w = 10;
        h = 10; //Defines the dimensions of the grid
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
        copyBoard();
        setOpaque(false);
        try {
            for (int i = 0; i < GameState.width; i++) {
                for (int j = 0; j < GameState.height; j++) {
                    if(gameGrid.get(i).get(j) != null && gameGrid.get(i).get(j).getxLocation()!=30) {
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
        for (int i = 0; i < GameState.width; i++) {
            for (int j = 0; j < GameState.height; j++) {
                if(GameState.getBoard().get(i).get(j) != null) {
                    try {
                        gameGrid.get(i).set(j, new Unit(GameState.getBoard().get(i).get(j)));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size, h * size);
    }
}






/*
        @Override
        public void mouseEntered(MouseEvent e) {
            class Worker implements Runnable {
                public Worker(MouseEvent e){

                }
                public void run() {
                    while (true) {
                        int x = (e.getX() / size);
                        int y = (e.getY() / size);
                        if (gameGrid.get(x).get(y) != null) {
                            JComponent component = (JComponent) e.getSource();
                            x = (e.getX() / size);
                            //Point p = getPointerInfo().getLocation();
                            //x = p.x;
                            //x = (int) Math.round((getPointerInfo().getLocation().getX()-component.getLocationOnScreen().getX())/size);
                            //System.out.println("tooltipx" + x);
                            y = (e.getY() / size);
                            //y = (int) Math.round((getPointerInfo().getLocation().getY()-component.getLocationOnScreen().getY())/size);
                            //System.out.println("tooltipy" + y);
                            component.setToolTipText(gameGrid.get(x).get(y).getName());
                            MouseEvent phantom = new MouseEvent(component, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, 0, 0, 0, false);
                            ToolTipManager.sharedInstance().mouseMoved(phantom);
                        }
                    }
                }
            }
            Runnable worker = new Worker(e);
            new Thread(worker).start();
        }
 */