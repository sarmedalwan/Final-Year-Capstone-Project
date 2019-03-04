package Project;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {
    public MenuFrame(String title) { //Creates the main menu frame which contains the JLabel created in MenuElements, which displays the main menu
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MenuElements menuElements = new MenuElements("menuvideo.gif"); //Creates menu elements, original background video obtained from publicdomaintorrents.info, edited by Sarmed Alwan
        add(BorderLayout.CENTER, menuElements); //Adds the menu elements to the frame
        pack();
        this.setVisible(true);
        repaint();
    }
}