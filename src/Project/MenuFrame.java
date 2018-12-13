package Project;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {
    public MenuFrame(String title) {
        super(title);
        MenuElements menuElements = new MenuElements("menuvideo.gif"); //Original video obtained from publicdomaintorrents.info
        add(BorderLayout.CENTER, menuElements);
        pack();
        this.setVisible(true);
        repaint();
    }
}