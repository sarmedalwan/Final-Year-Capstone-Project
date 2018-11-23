package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Sarmed on 23/11/2018.
 */
public class MainPanel extends JPanel {
    class Listener implements MouseListener {
        UnitPanel panel;

        Listener(UnitPanel panel){
            this.panel = panel;
        }
        @Override
        public void mouseClicked(MouseEvent e) {

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

    public MainPanel()
    {
        this.setFocusable(true);
        this.grabFocus();
        UnitPanel unitPanel = new UnitPanel(GameState.getNewBoard(), 1);
        Territories territories = new Territories();
        Grid grid = new Grid();
        Background background = new Background();
        this.add(BorderLayout.CENTER, unitPanel);
        this.add(BorderLayout.CENTER, territories);
        this.add(BorderLayout.CENTER, grid);
        this.add(BorderLayout.CENTER, background);
    }
}
