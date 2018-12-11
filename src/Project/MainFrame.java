package Project;

import javax.swing.*;
import java.awt.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainFrame extends JFrame {
    public MainFrame(String title) {
        super(title);
        MainPanel main = new MainPanel(this);
        LayoutManager overlay = new OverlayLayout(main);
        main.setLayout(overlay);
        setLayout(new BorderLayout());
        getContentPane().add(main, BorderLayout.CENTER);
        pack();
        this.setVisible(true);
        repaint();
    }
}