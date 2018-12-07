package Project;

/**
 * Created by Sarmed on 10/11/2018.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

public class UnitPanel extends JPanel
{
    int w;
    int h;
    int faction;
    static int size = 60;
    static ArrayList<ArrayList<Unit>> gameGrid;
    Unit selected;

    class Listener implements MouseListener {
        UnitPanel panel;

        Listener(UnitPanel panel){
            this.panel = panel;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            Unit[][] newGrid = new Unit[10][10];
            panel.grabFocus();
            panel.requestFocus();
            int x = (e.getX()/size);
            int y = (e.getY()/size);
            int oldX;
            int oldY;
            System.out.println(x + ", " + y);
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
                if(Selection.selected(gameGrid)!=null) {
                    try {
                        selectedUnit = new Unit(Selection.selected(gameGrid));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
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
                gameGrid.get(oldX).remove(oldY);
                try {
                    newGrid[x][y] = new Unit(selected);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                GameState.updateBoard(gameGrid);
            }
            repaint();
            //panel.gameGrid[x][y] = ;
//            System.out.println("playernumber " + panel.faction);
//            System.out.println(Arrays.deepToString(a));
//            Move move = new Move(null, new Coordinates(x,y), null);
//            System.out.println("x " + move.getFirstMove().getX() + " y " + move.getFirstMove().getY());
//            //if (GameState.isMoveAllowed(gameBoard, move, faction)){
//            GameState.updateBoard(panel.a);
//            GameClient.makeMove(move);
//            //revalidate();
//            repaint();
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
    }

    public UnitPanel(ArrayList<ArrayList<Unit>> gameGrid, int faction)
    {
        this.setFocusable(true);
        this.grabFocus();
        this.gameGrid = gameGrid;
        w = 10;
        h = 10; //Defines the dimensions of the grid
        this.faction = faction;
        this.addMouseListener(new Listener(this));
        this.requestFocus();
    }

    public void paintComponent(Graphics g) {
        this.grabFocus();
        copyBoard();
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
