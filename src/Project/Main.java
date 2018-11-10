package Project;

import java.util.Random;

public class Main
{
    static int[][] a;
    static int score = 0;
    static int direction = 0; //Declares all of the important static variables for the game
    public static void main(String[] args) {
        Random r = new Random();
        int w = 10;
        int h = 10;
        a = new int[w][h]; //Sets the width of the board

        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                a[i][j] = 3;
            }
        } //Makes all of the tiles transparent
        /*Random x = new Random();
        Random y = new Random();
        xPlayer = x.nextInt(GameView.size);
        yPlayer = y.nextInt(GameView.size);
        */
        GameView comp = new GameView(a);
        new JEasyFrame(comp, "Game by Sarmed Alwan, 1603088");
    }
}