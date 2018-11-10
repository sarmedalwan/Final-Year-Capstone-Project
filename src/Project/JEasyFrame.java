package Project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static Project.Main.*;

public class JEasyFrame extends JFrame {
    public Component comp;
    private Image backgroundImage;
    private JLayeredPane lpane = new JLayeredPane();
    public JEasyFrame(GameView comp, String title) {
        super(title);
        this.comp = comp;
        /*try {
            setLayout(new BorderLayout());
            ImageIcon image = new ImageIcon();
            setContentPane(new JLabel(new ImageIcon("media/testbackground.png")));
            //getContentPane().add(new JLabel("media/testbackground.png"));
        } catch (Exception e){
            e.printStackTrace();
        }*/
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Initialises the game window
        repaint();
    }

}



































// move currently active block at fixed intervals:
    /*public static void startTimer(boolean game_going, JEasyFrame app, long time_interval, GameView comp)
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
                        for (int i = 0; i < comp.a.length; i++) {
                            for (int j = 0; j < comp.a[i].length; j++) {
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
                                    if (comp.a[i][j]==1&&(Math.round(Math.sqrt((yDiff*yDiff)+(xDiff*xDiff)))>= Main.score)){
                                        comp.a[i][j]=0;
                                    }
                                } else {
                                    if (comp.a[i][j] == 1 && (Math.round(Math.sqrt((yDiff * yDiff) + (xDiff * xDiff))) >= Main.score) && (comp.a[i - 1][j] != 1 || comp.a[i + 1][j] != 1 || comp.a[i][j + 1] != 1 || comp.a[i][j - 1] != 1)) {
                                        comp.a[i][j] = 0;
                                    } //Uses pythagoras' formula to calculate distance of player tile away from player's head, to see if it exceeds the length of the snake and needs to be cleared
                                }
                            }
                        } //Clears tiles which are of the player's colour but are further away from the player than their score; trims the snake to the length of how much food it's eaten
                        nextMove(app);
                        switch (Main.direction) {
                            case 1: //If the player presses the Up arrow
                                Main.yPlayer--; //Move the head up one space
                                if (comp.a[Main.xPlayer][Main.yPlayer] == 1) {
                                    JOptionPane.showMessageDialog(null, "You lose! Your score is " + Main.score); //If the player touches their own tail
                                    try {
                                        Path path = Paths.get("highScores.txt");
                                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                                    }catch (IOException e) {
                                        System.out.println(e);
                                    } //If the player runs into themselves, they lose
                                    System.exit(0);
                                } else if (comp.a[Main.xPlayer][Main.yPlayer] == 2) {
                                    Main.score++;
                                    do {
                                        Main.xFood = x.nextInt(GameView.size);
                                        Main.yFood = x.nextInt(GameView.size);
                                        if (comp.a[Main.xFood][Main.yFood]==0){
                                            comp.a[Main.xFood][Main.yFood] = 2;
                                        }
                                    } while (comp.a[Main.xFood][Main.yFood]==0);
                                } //If the player touches the food, their score increases by 1 and a new location is chosen for the food. If the new location is occupied, another one is chosen until an empty one is found
                                comp.a[Main.xPlayer][Main.yPlayer] = 1;
                                if (Main.score < 1){
                                    comp.a[Main.xPlayer][Main.yPlayer+1] = 0;
                                }
                                break;

                            case 2: //If the player presses the Down arrow
                                Main.yPlayer++; //Move the head down one space
                                if (comp.a[Main.xPlayer][Main.yPlayer] == 1) {
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
                                } else if (comp.a[Main.xPlayer][Main.yPlayer] == 2) {
                                    Main.score++;
                                    do {
                                        Main.xFood = x.nextInt(GameView.size);
                                        Main.yFood = x.nextInt(GameView.size);
                                        if (comp.a[Main.xFood][Main.yFood]==0){
                                            comp.a[Main.xFood][Main.yFood] = 2;
                                        }
                                    } while (comp.a[Main.xFood][Main.yFood]==0);
                                } //If the player touches the food, their score increases by 1 and a new location is chosen for the food. If the new location is occupied, another one is chosen until an empty one is found
                                comp.a[Main.xPlayer][Main.yPlayer] = 1;
                                if (Main.score < 1){
                                    comp.a[Main.xPlayer][Main.yPlayer-1] = 0;
                                }
                                break;

                            case 3: //If the player presses the Left arrow
                                Main.xPlayer--; //Move the head left one space
                                if (comp.a[Main.xPlayer][Main.yPlayer] == 1) {
                                    JOptionPane.showMessageDialog(null, "You lose! Your score is " + Main.score); //If the player touches their own tail
                                    try {
                                        Path path = Paths.get("highScores.txt");
                                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                                    }catch (IOException e) {
                                        System.out.println(e);
                                    } //If the player runs into themselves, they lose
                                    System.exit(0);
                                } else if (comp.a[Main.xPlayer][Main.yPlayer] == 2) {
                                    Main.score++;
                                    do {
                                        Main.xFood = x.nextInt(GameView.size);
                                        Main.yFood = x.nextInt(GameView.size);
                                        if (comp.a[Main.xFood][Main.yFood]==0){
                                            comp.a[Main.xFood][Main.yFood] = 2;
                                        }
                                    } while (comp.a[Main.xFood][Main.yFood]==0);
                                } //If the player touches the food, their score increases by 1 and a new location is chosen for the food. If the new location is occupied, another one is chosen until an empty one is found
                                comp.a[Main.xPlayer][Main.yPlayer] = 1;
                                if (Main.score < 1){
                                    comp.a[Main.xPlayer+1][Main.yPlayer] = 0;
                                } //If the player runs into themselves, they lose
                                break;
                            case 4: //If the player presses the Right arrow
                                Main.xPlayer++; //Move the head right one space
                                if (comp.a[Main.xPlayer][Main.yPlayer] == 1) {
                                    JOptionPane.showMessageDialog(null, "You lose! Your score is " + Main.score); //If the player touches their own tail
                                    try {
                                        Path path = Paths.get("highScores.txt");
                                        Files.write(path, Arrays.asList(Integer.toString(Main.score)), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                                        System.out.println("Score added to high scores file"); //Appends the high score to the high scores file if it exists. If it doesn't exist, creates one
                                    }catch (IOException e) {
                                        System.out.println(e);
                                    } //If the player runs into themselves, they lose
                                    System.exit(0);
                                } else if (comp.a[Main.xPlayer][Main.yPlayer] == 2) {
                                    Main.score++;
                                    do {
                                        Main.xFood = x.nextInt(GameView.size);
                                        Main.yFood = x.nextInt(GameView.size);
                                        if (comp.a[Main.xFood][Main.yFood]==0){
                                            comp.a[Main.xFood][Main.yFood] = 2;
                                        }
                                    } while (comp.a[Main.xFood][Main.yFood]==0);
                                } //If the player touches the food, their score increases by 1 and a new location is chosen for the food. If the new location is occupied, another one is chosen until an empty one is found
                                comp.a[Main.xPlayer][Main.yPlayer] = 1;
                                if (Main.score < 1){
                                    comp.a[Main.xPlayer-1][Main.yPlayer] = 0;
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
                        }catch (IOException a) {
                            System.out.println(a);
                        }
                        System.exit(0);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 50, time_interval);
    }
    public static void nextMove(JEasyFrame app){
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
                    }catch (IOException a) {
                        System.out.println(a);
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
                    }catch (IOException a) {
                        System.out.println(a);
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
                    }catch (IOException a) {
                        System.out.println(a);
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
                    }catch (IOException a) {
                        System.out.println(a);
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
                    }catch (IOException a) {
                        System.out.println(a);
                    }
                    System.exit(0);
                }
            }
        }); //Listens for a mouse click, which will end the game

        app.setVisible(true);
        app.repaint(); //Dedraws the window
    }
}*/