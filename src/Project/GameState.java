package Project;

import java.io.IOException;
import java.util.*;

// This class (not yet fully implemented) will give access to the current state of the game.
public final class GameState {
    public static final int height = 10;
    public static final int width = 10;
    public static Unit[][] gameBoard;

    public static Unit[][] getNewBoard() {
        int width = 10;
        int height = 10;
        gameBoard = new Unit[width][height];
        for (int i = 0; i < GameState.width; i++)
        {
            for (int j = 0; j < GameState.height; j++)
            {
                gameBoard[i][j] = null;
            }
        } //Makes all of the tiles blank
        try {
            Unit s1stMC = new Unit("1st Mechanised Corps", "sovietinfcounter", 0, 8, "inf", 1, false);
            gameBoard[0][8] = s1stMC;
            Unit s1stMCArt = new Unit("1st MC Artillery", "sovietartillerycounter", 0, 7, "artillery", 1, false);
            gameBoard[0][7] = s1stMCArt;
            Unit s6thRC = new Unit("6th Rifle Corps", "sovietinfcounter", 0, 6, "inf", 1, false);
            gameBoard[0][6] = s6thRC;
            Unit s6thRCArt = new Unit("6th RC Artillery", "sovietartillerycounter", 0, 5, "artillery", 1, false);
            gameBoard[0][5] = s6thRCArt;
            Unit s3rdMCArt = new Unit("3rd MC Artillery", "sovietartillerycounter", 0, 4, "artillery", 1, false);
            gameBoard[0][4] = s3rdMCArt;
            Unit s3rdMC = new Unit("3rd Mechanised Corps", "sovietinfcounter", 1, 4, "inf", 1, false);
            gameBoard[1][4] = s3rdMC;
            Unit s238thInf = new Unit("238th Infantry Division", "sovietinfcounter", 1, 5, "inf", 1, false);
            gameBoard[1][5] = s238thInf;
            Unit s185thInf = new Unit("185th Infantry Division", "sovietinfcounter", 0, 3, "inf", 1, false);
            gameBoard[0][3] = s185thInf;
        } catch(Exception e){
            e.printStackTrace();
        }
        return gameBoard;
    }

    public static Unit[][] getBoard() {
        return gameBoard;
    }

    public static boolean isMoveAllowed(Unit[][] a, Move move, int player) {
        return true;
    }

    public static void updateBoard(Unit[][] updatedBoard){
        for (int i = 0; i < GameState.width; i++) {
            for (int j = 0; j < GameState.height; j++) {
                if(updatedBoard[i][j] != null) {
                    try {
                        gameBoard[i][j] =  new Unit(updatedBoard[i][j]);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}

