package Project;

/**
 * Created by Sarmed on 10/11/2018.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;

public class UnitPanel extends JPanel
{
    int w;
    int h;
    int faction;
    static int size = 60;
    static Unit[][] gameGrid;

    class Listener implements MouseListener {
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
            System.out.println(x + ", " + y);
            for (int i = 0; i < GameState.width; i++) {
                for (int j = 0; j < GameState.height; j++) {
                    if(GameState.getBoard()[i][j] != null) {
                        try {
                            gameGrid[i][j] =  new Unit(GameState.getBoard()[i][j]);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
            for (int i = 0; i < GameState.width; i++) {
                for (int j = 0; j < GameState.height; j++) {
                    if(gameGrid[i][j] != null && gameGrid[i][j].getSelection()) {
                        System.out.println(gameGrid[i][j].getName());
                    }
                }
            }
            if (gameGrid[x][y] != null) {
                System.out.println(gameGrid[x][y].getName());
                Unit selectedUnit = Selection.selected(gameGrid);
                if (selectedUnit != null) {
                    System.out.println("Unit " + selectedUnit.getName() + " is already selected");
                } else {
                    gameGrid[x][y].setSelection(true);
                    GameState.updateBoard(gameGrid);
                    System.out.println(gameGrid[x][y].getName() + " selection set true");
                }
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

    public UnitPanel(Unit[][] gameGrid, int faction)
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
        for (int i = 0; i < GameState.width; i++) {
            for (int j = 0; j < GameState.height; j++) {
                if(GameState.getBoard()[i][j] != null) {
                    try {
                        gameGrid[i][j] =  new Unit(GameState.getBoard()[i][j]);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        try {
            for (int i = 0; i < GameState.width; i++) {
                for (int j = 0; j < GameState.height; j++) {
                    if(gameGrid[i][j] != null) {
                        g.drawImage(gameGrid[i][j].getIcon(), (gameGrid[i][j].getxLocation() * size) + 10, (gameGrid[i][j].getyLocation() * size) + 10, 40, 40, null); //Draws each Unit object's corresponding icon
                    }
                }
            }
        } catch(Exception e){
            System.out.println(e);
        }
        this.requestFocus();
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size, h * size);
    }
}
