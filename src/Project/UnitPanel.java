package Project;

/**
 * Created by Sarmed on 10/11/2018.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static Project.GameState.columns;
import static Project.GameState.rows;

public class UnitPanel extends JPanel
{
    int w;
    int h;
    int faction;
    static int size = 60;
    Unit[][] gameGrid;

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
            if (gameGrid[x][y] != null) {
                System.out.println(gameGrid[x][y].getName());
                Unit selectedUnit = Selection.selected(gameGrid);
                if (selectedUnit != null) {
                    System.out.println("Unit already selected");
                } else {
                    panel.gameGrid[x][y].setSelection(true);
                    System.out.println(panel.gameGrid[x][y].getName() + " selection set true");
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
        gameGrid = GameState.getNewBoard();
        try {
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
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
