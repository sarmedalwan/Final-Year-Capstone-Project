package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainFrame extends JFrame {
    final JFXPanel fxPanel = new JFXPanel();
    Media hit = new Media(getClass().getResource("/media/a.mp3").toString());
    MediaPlayer mediaPlayer = new MediaPlayer(hit);
    public MainFrame(String title) {
        super(title);
        JPanel main = new JPanel();
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setFocusable(false);
        main.add(fxPanel);
        mediaPlayer.play();
        LayoutManager overlay = new OverlayLayout(main);
        main.setLayout(overlay);
        DrawUnits drawUnits = new DrawUnits(GameState.getNewBoard(), 1);
        drawUnits.setFocusable(true);
        drawUnits.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent aE) {
                drawUnits.requestFocus();
                drawUnits.grabFocus();
            }
            @Override
            public void focusLost(FocusEvent aE) {
                drawUnits.requestFocus();
                drawUnits.grabFocus();
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                drawUnits.requestFocus();
                drawUnits.grabFocus();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        main.add(drawUnits);

        Territories territories = new Territories();
        main.add(BorderLayout.CENTER, territories);

        Grid grid = new Grid();
        grid.setFocusable(false);
        main.add(BorderLayout.CENTER, grid);

        Background background = new Background();
        grid.setFocusable(false);
        main.add(BorderLayout.CENTER, background);

        setLayout(new BorderLayout());
        grid.setFocusable(false);
        getContentPane().add(main, BorderLayout.CENTER);

        drawUnits.setFocusable(true);
        drawUnits.setRequestFocusEnabled(true);
        drawUnits.requestFocus();
        drawUnits.grabFocus();

        pack();
        //mediaPlayer.play();
        this.setVisible(true);
        drawUnits.requestFocus();
        drawUnits.grabFocus();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
        drawUnits.requestFocus();
        drawUnits.grabFocus();
    }

}



































// move currently active block at fixed intervals:
    /*public static void startTimer(boolean game_going, MainFrame app, long time_interval, Background background)
    {
        java.util.Timer timer = new java.util.Timer();
        TimerTask task = new TimerTask()
        {
            public void run()
            {
                Random x = new Random();
                Random y = new Random();
                if (game_going)
                {
                    try {
                        for (int i = 0; i < background.owners.length; i++) {
                            for (int j = 0; j < background.owners[i].length; j++) {
                                int xDiff = 0;
                                if(Main.xPlayer <= i){
                                    xDiff = (i - Main.xPlayer);
                                }
                                if(Main.xPlayer >= i){
                                    xDiff = (Main.xPlayer - i);
                                }
                                int yDiff = 0;
                                if(Main.yPlayer <= j){
                                    yDiff = (j - Main.yPlayer);
                                }
                                if(Main.yPlayer >= j){
                                    yDiff = (Main.yPlayer - j);
                                }
                                if (i==20||j==20||i==0||j==0){
                                    if (background.owners[i][j]==1&&(Math.round(Math.sqrt((yDiff*yDiff)+(xDiff*xDiff)))>= Main.score)){
                                        background.owners[i][j]=0;
                                    }
                                } else {
                                    if (background.owners[i][j] == 1 && (Math.round(Math.sqrt((yDiff * yDiff) + (xDiff * xDiff))) >= Main.score) && (background.owners[i - 1][j] != 1 || background.owners[i + 1][j] != 1 || background.owners[i][j + 1] != 1 || background.owners[i][j - 1] != 1)) {
                                        background.owners[i][j] = 0;
                                    } //Uses pythagoras' formula to calculate distance of player tile away from player's head, to see if it exceeds the length of the snake and needs to be cleared
                                }
                            }
                        } //Clears tiles which are of the player's colour but are further away from the player than their score; trims the snake to the length of how much food it's eaten
                        nextMove(app);
                        switch (Main.direction) {
                            case 1: //If the player presses the Up arrow
                                Main.yPlayer--; //Move the head up one space
                                if (background.owners[Main.xPlayer][Main.yPlayer] == 1) {
                                    JOptionPane.showMessageDialog(null, "You lose! Your score is " + Main.score); //If the player touches their own tail
                                    try {
                                        Path path = Paths.get("highScores.txt");
                                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                                    }catch (IOException e) {
                                        System.out.println(e);
                                    } //If the player runs into themselves, they lose
                                    System.exit(0);
                                } else if (background.owners[Main.xPlayer][Main.yPlayer] == 2) {
                                    Main.score++;
                                    do {
                                        Main.xFood = x.nextInt(Background.size);
                                        Main.yFood = x.nextInt(Background.size);
                                        if (background.owners[Main.xFood][Main.yFood]==0){
                                            background.owners[Main.xFood][Main.yFood] = 2;
                                        }
                                    } while (background.owners[Main.xFood][Main.yFood]==0);
                                } //If the player touches the food, their score increases by 1 and owners new location is chosen for the food. If the new location is occupied, another one is chosen until an empty one is found
                                background.owners[Main.xPlayer][Main.yPlayer] = 1;
                                if (Main.score < 1){
                                    background.owners[Main.xPlayer][Main.yPlayer+1] = 0;
                                }
                                break;

                            case 2: //If the player presses the Down arrow
                                Main.yPlayer++; //Move the head down one space
                                if (background.owners[Main.xPlayer][Main.yPlayer] == 1) {
                                    JOptionPane.showMessageDialog(null, "You lose! Your score is " + Main.score); //If the player touches their own tail
                                    try {
                                        Path path = Paths.get("highScores.txt");
                                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8,
                                                Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                                    }catch (IOException e) {
                                        System.out.println(e);
                                    } //If the player runs into themselves, they lose
                                    System.exit(0);
                                } else if (background.owners[Main.xPlayer][Main.yPlayer] == 2) {
                                    Main.score++;
                                    do {
                                        Main.xFood = x.nextInt(Background.size);
                                        Main.yFood = x.nextInt(Background.size);
                                        if (background.owners[Main.xFood][Main.yFood]==0){
                                            background.owners[Main.xFood][Main.yFood] = 2;
                                        }
                                    } while (background.owners[Main.xFood][Main.yFood]==0);
                                } //If the player touches the food, their score increases by 1 and owners new location is chosen for the food. If the new location is occupied, another one is chosen until an empty one is found
                                background.owners[Main.xPlayer][Main.yPlayer] = 1;
                                if (Main.score < 1){
                                    background.owners[Main.xPlayer][Main.yPlayer-1] = 0;
                                }
                                break;

                            case 3: //If the player presses the Left arrow
                                Main.xPlayer--; //Move the head left one space
                                if (background.owners[Main.xPlayer][Main.yPlayer] == 1) {
                                    JOptionPane.showMessageDialog(null, "You lose! Your score is " + Main.score); //If the player touches their own tail
                                    try {
                                        Path path = Paths.get("highScores.txt");
                                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                                    }catch (IOException e) {
                                        System.out.println(e);
                                    } //If the player runs into themselves, they lose
                                    System.exit(0);
                                } else if (background.owners[Main.xPlayer][Main.yPlayer] == 2) {
                                    Main.score++;
                                    do {
                                        Main.xFood = x.nextInt(Background.size);
                                        Main.yFood = x.nextInt(Background.size);
                                        if (background.owners[Main.xFood][Main.yFood]==0){
                                            background.owners[Main.xFood][Main.yFood] = 2;
                                        }
                                    } while (background.owners[Main.xFood][Main.yFood]==0);
                                } //If the player touches the food, their score increases by 1 and owners new location is chosen for the food. If the new location is occupied, another one is chosen until an empty one is found
                                background.owners[Main.xPlayer][Main.yPlayer] = 1;
                                if (Main.score < 1){
                                    background.owners[Main.xPlayer+1][Main.yPlayer] = 0;
                                } //If the player runs into themselves, they lose
                                break;
                            case 4: //If the player presses the Right arrow
                                Main.xPlayer++; //Move the head right one space
                                if (background.owners[Main.xPlayer][Main.yPlayer] == 1) {
                                    JOptionPane.showMessageDialog(null, "You lose! Your score is " + Main.score); //If the player touches their own tail
                                    try {
                                        Path path = Paths.get("highScores.txt");
                                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                                    }catch (IOException e) {
                                        System.out.println(e);
                                    } //If the player runs into themselves, they lose
                                    System.exit(0);
                                } else if (background.owners[Main.xPlayer][Main.yPlayer] == 2) {
                                    Main.score++;
                                    do {
                                        Main.xFood = x.nextInt(Background.size);
                                        Main.yFood = x.nextInt(Background.size);
                                        if (background.owners[Main.xFood][Main.yFood]==0){
                                            background.owners[Main.xFood][Main.yFood] = 2;
                                        }
                                    } while (background.owners[Main.xFood][Main.yFood]==0);
                                } //If the player touches the food, their score increases by 1 and owners new location is chosen for the food. If the new location is occupied, another one is chosen until an empty one is found
                                background.owners[Main.xPlayer][Main.yPlayer] = 1;
                                if (Main.score < 1){
                                    background.owners[Main.xPlayer-1][Main.yPlayer] = 0;
                                }
                                break;
                            default:
                                break;
                        }

                        app.repaint();
                    } catch (Exception e){
                        JOptionPane.showMessageDialog(null, "Your score is " + Main.score);
                        try {
                            Path path = Paths.get("highScores.txt");
                            Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                            System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                        }catch (IOException owners) {
                            System.out.println(owners);
                        }
                        System.exit(0);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 50, time_interval);
    }
    public static void nextMove(MainFrame app){
        app.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP && Main.direction != 2){
                    Main.direction = 1;
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP && Main.direction != 2){
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP && Main.direction != 2){
                }
            }
        }); //Listens for the Up arrow key, if the player isn't travelling down

        app.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DOWN && Main.direction != 1){
                    Main.direction = 2;
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DOWN && Main.direction != 1){
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DOWN && Main.direction != 1){
                }
            }
        }); //Listens for the Down arrow key, if the player isn't travelling up

        app.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT && Main.direction != 4){
                    Main.direction = 3;
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT && Main.direction != 4){
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT && Main.direction != 4){
                }
            }
        }); //Listens for the Left arrow key, if the player isn't travelling right

        app.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_RIGHT && Main.direction != 3){
                    Main.direction = 4;
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_RIGHT && Main.direction != 3){
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_RIGHT && Main.direction != 3){
                }
            }
        }); //Listens for the Right arrow key, if the player isn't travelling left

        app.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()>0){
                    JOptionPane.showMessageDialog(null, "Exiting game. Your score is " + Main.score);
                    try {
                        Path path = Paths.get("highScores.txt");
                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                    }catch (IOException owners) {
                        System.out.println(owners);
                    }
                    System.exit(0);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(e.getClickCount()>0){
                    JOptionPane.showMessageDialog(null, "Exiting game. Your score is " + Main.score);
                    try {
                        Path path = Paths.get("highScores.txt");
                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                    }catch (IOException owners) {
                        System.out.println(owners);
                    }
                    System.exit(0);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getClickCount()>0){
                    JOptionPane.showMessageDialog(null, "Exiting game. Your score is " + Main.score);
                    try {
                        Path path = Paths.get("highScores.txt");
                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                    }catch (IOException owners) {
                        System.out.println(owners);
                    }
                    System.exit(0);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(e.getClickCount()>0){
                    JOptionPane.showMessageDialog(null, "Exiting game. Your score is " + Main.score);
                    try {
                        Path path = Paths.get("highScores.txt");
                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                    }catch (IOException owners) {
                        System.out.println(owners);
                    }
                    System.exit(0);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getClickCount()>0){
                    JOptionPane.showMessageDialog(null, "Exiting game. Your score is " + Main.score);
                    try {
                        Path path = Paths.get("highScores.txt");
                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                    }catch (IOException owners) {
                        System.out.println(owners);
                    }
                    System.exit(0);
                }
            }
        }); //Listens for owners mouse click, which will end the game

        app.setVisible(true);
        app.repaint(); //Dedraws the window
    }
}*/