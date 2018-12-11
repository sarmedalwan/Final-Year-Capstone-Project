package Project;

import java.io.IOException;
import java.util.*;

// This class (not yet fully implemented) will give access to the current state of the game.
public final class GameState {
    public static final int height = 10;
    public static final int width = 10;
    public static ArrayList<ArrayList<Unit>> gameBoard;
    public static int[][] territories;

    public static ArrayList<ArrayList<Unit>> getNewBoard() {
        gameBoard = new ArrayList<ArrayList<Unit>>(10);
        for (int i = 0; i < GameState.width; i++)
        {
            gameBoard.add(new ArrayList<Unit>(10));
            for (int j = 0; j < GameState.height; j++)
            {
                gameBoard.get(i).add(null);
            }
        } //Makes all of the tiles blank
        try {
            Unit s1stMC = new Unit("1st Mechanised Corps", "sovietinfcounter", 0, 8, "inf", 1, false);
            gameBoard.get(0).set(8, s1stMC);
            Unit s1stMCArt = new Unit("1st MC Artillery", "sovietartillerycounter", 0, 7, "artillery", 1, false);
            gameBoard.get(0).set(7, s1stMCArt);
            Unit s6thRC = new Unit("6th Rifle Corps", "sovietinfcounter", 0, 6, "inf", 1, false);
            gameBoard.get(0).set(6, s6thRC);
            Unit s6thRCArt = new Unit("6th RC Artillery", "sovietartillerycounter", 0, 5, "artillery", 1, false);
            gameBoard.get(0).set(5, s6thRCArt);
            Unit s3rdMCArt = new Unit("3rd MC Artillery", "sovietartillerycounter", 0, 4, "artillery", 1, false);
            gameBoard.get(0).set(4, s3rdMCArt);
            Unit s3rdMC = new Unit("3rd Mechanised Corps", "sovietinfcounter", 1, 4, "inf", 1, false);
            gameBoard.get(1).set(4, s3rdMC);
            Unit s238thInf = new Unit("238th Infantry Division", "sovietinfcounter", 1, 5, "inf", 1, false);
            gameBoard.get(1).set(5, s238thInf);
            Unit s185thInf = new Unit("185th Infantry Division", "sovietinfcounter", 0, 3, "inf", 1, false);
            gameBoard.get(0).set(3, s185thInf);
        } catch(Exception e){
            e.printStackTrace();
        }
        return gameBoard;
    }

    public static ArrayList<ArrayList<Unit>> getBoard() {
        return gameBoard;
    }

    public static boolean isMoveAllowed(ArrayList<ArrayList<Unit>> gameBoard, int player) {
        return false;
    }

    public static int[][] getTerritories(){
        return territories;
    }

    public static void updateTerritories(){

    }

    public static int[][] getNewTerritories(){
        int w = 10;
        int h = 10;
        int[][] owners = new int[w][h];
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                owners[i][j] = 0;
            }
        }
        for (int i = 0; i<7; i++){
            owners[i][9] = 1;
        }
        for (int i = 1; i<7; i++){
            owners[i][8] = 1;
        }
        for (int i = 1; i<8; i++){
            owners[i][7] = 1;
        }
        for (int i = 1; i<8; i++){
            owners[i][6] = 1;
        }
        for (int i = 2; i<7; i++){
            owners[i][5] = 1;
        }
        for (int i = 2; i<7; i++){
            owners[i][4] = 1;
        }
        for (int i = 1; i<7; i++){
            owners[i][3] = 1;
        }
        for (int i = 2; i<6; i++){
            owners[i][2] = 1;
        }
        owners[2][1] = 1;
        return owners;
    }

    public static void updateBoard(ArrayList<ArrayList<Unit>> updatedBoard){
        for (int i = 0; i < GameState.width; i++) {
            for (int j = 0; j < GameState.height; j++) {
                if(gameBoard.get(i).get(j) != null) {
                    try {
                        gameBoard.get(i).set(j, new Unit(gameBoard.get(i).get(j)));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}

