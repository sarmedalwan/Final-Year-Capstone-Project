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
            Unit s1stMCArt = new Unit("1st MC Artillery", "sovietartillerycounter", 0, 7, "art", 1, false);
            gameBoard.get(0).set(7, s1stMCArt);
            Unit s6thRC = new Unit("6th Rifle Corps", "sovietinfcounter", 0, 6, "inf", 1, false);
            gameBoard.get(0).set(6, s6thRC);
            Unit s6thRCArt = new Unit("6th RC Artillery", "sovietartillerycounter", 0, 5, "art", 1, false);
            gameBoard.get(0).set(5, s6thRCArt);
            Unit s3rdMCArt = new Unit("3rd MC Artillery", "sovietartillerycounter", 0, 4, "art", 1, false);
            gameBoard.get(0).set(4, s3rdMCArt);
            Unit s3rdMC = new Unit("Katukov's 3rd Mechanised Corps", "sovietinfcounter", 1, 4, "inf", 1, false);
            gameBoard.get(1).set(4, s3rdMC);
            Unit s238thInf = new Unit("238th Infantry Division", "sovietinfcounter", 1, 5, "inf", 1, false);
            gameBoard.get(1).set(5, s238thInf);
            Unit s185thInf = new Unit("185th Infantry Division", "sovietinfcounter", 0, 3, "inf", 1, false);
            gameBoard.get(0).set(3, s185thInf);
            Unit s3rdMCArm = new Unit("Katukov's 3rd Mechanised Corps Tank Brigade", "sovietarmourcounter", 1, 2, "arm", 1, false);
            gameBoard.get(1).set(2, s3rdMCArm);
            Unit s3rdMCRes = new Unit("Katukov's 3rd Mechanised Corps Infantry Reserve", "sovietinfcounter", 0, 2, "inf", 1, false);
            gameBoard.get(0).set(2, s3rdMCRes);
            Unit s3rdMCArtRes = new Unit("Katukov's 3rd Mechanised Corps Artillery Reserve", "sovietartillerycounter", 0, 1, "art", 1, false);
            gameBoard.get(0).set(1, s3rdMCArtRes);
            Unit s11thCC = new Unit("11th Cavalry Corps", "sovietcavcounter", 2, 0, "inf", 1, false);
            gameBoard.get(2).set(0, s11thCC);
            Unit s18thCav = new Unit("11th Cavalry Division", "sovietcavcounter", 5, 1, "inf", 1, false);
            gameBoard.get(5).set(1, s18thCav);
            Unit s24thCav = new Unit("24th Cavalry Division", "sovietcavcounter", 4, 1, "inf", 1, false);
            gameBoard.get(4).set(1, s24thCav);
            Unit s82ndCav = new Unit("82nd Cavalry Division", "sovietcavcounter", 1, 1, "inf", 1, false);
            gameBoard.get(1).set(1, s82ndCav);
            Unit s11thCCArt = new Unit("11th Cavalry Corps Artillery Support", "sovietartillerycounter", 3, 0, "art", 1, false);
            gameBoard.get(3).set(0, s11thCCArt);
            Unit s355thInf = new Unit("355th Infantry Division", "sovietinfcounter", 1, 1, "inf", 1, false);
            gameBoard.get(1).set(1, s355thInf);
            Unit s357thInf = new Unit("357th Infantry Division", "sovietinfcounter", 0, 0, "inf", 1, false);
            gameBoard.get(0).set(0, s357thInf);
            Unit s361stInf = new Unit("361st Infantry Division", "sovietinfcounter", 1, 0, "inf", 1, false);
            gameBoard.get(1).set(0, s361stInf);
            Unit s369thInf = new Unit("369th Infantry Division", "sovietinfcounter", 4, 0, "inf", 1, false);
            gameBoard.get(4).set(0, s369thInf);
            Unit s373rdInf = new Unit("373rd Infantry Division", "sovietinfcounter", 5, 0, "inf", 1, false);
            gameBoard.get(5).set(0, s373rdInf);
            Unit s377thInf = new Unit("377th Infantry Division", "sovietinfcounter", 6, 0, "inf", 1, false);
            gameBoard.get(6).set(0, s377thInf);
            Unit s381stInf = new Unit("381st Infantry Division", "sovietinfcounter", 6, 1, "inf", 1, false);
            gameBoard.get(6).set(1, s381stInf);
            Unit s39thArmy = new Unit("39th Army", "sovietarmourcounter", 3, 1, "arm", 1, false);
            gameBoard.get(3).set(1, s39thArmy);
            Unit s30thArmy = new Unit("30th Army", "sovietarmourcounter", 6, 2, "arm", 1, false);
            gameBoard.get(6).set(2, s30thArmy);
            Unit s174thInf = new Unit("174th Rifle Division", "sovietinfcounter", 7, 2, "inf", 1, false);
            gameBoard.get(7).set(2, s174thInf);
            Unit s178thInf = new Unit("178th Rifle Division", "sovietinfcounter", 8, 2, "inf", 1, false);
            gameBoard.get(8).set(2, s178thInf);
            Unit s243rdInf = new Unit("243rd Rifle Division", "sovietinfcounter", 9, 2, "inf", 1, false);
            gameBoard.get(9).set(2, s243rdInf);
            Unit s348thInf = new Unit("348th Rifle Division", "sovietinfcounter", 7, 1, "inf", 1, false);
            gameBoard.get(7).set(1, s348thInf);
            Unit s359thInf = new Unit("359th Rifle Division", "sovietinfcounter", 7, 0, "inf", 1, false);
            gameBoard.get(7).set(0, s359thInf);
            Unit s363rdInf = new Unit("343rd Rifle Division", "sovietinfcounter", 8, 0, "inf", 1, false);
            gameBoard.get(8).set(0, s363rdInf);
            Unit s371stInf = new Unit("371st Rifle Division", "sovietinfcounter", 8, 1, "inf", 1, false);
            gameBoard.get(8).set(1, s371stInf);
            Unit s375thInf = new Unit("375th Rifle Division", "sovietinfcounter", 9, 1, "inf", 1, false);
            gameBoard.get(9).set(1, s375thInf);
            Unit s379thInf = new Unit("379th Rifle Division", "sovietinfcounter", 9, 0, "inf", 1, false);
            gameBoard.get(9).set(0, s379thInf);
            Unit s110thArm = new Unit("110th Tank Division", "sovietarmourcounter", 8, 2, "arm", 1, false);
            gameBoard.get(8).set(2, s110thArm);
            Unit s43CArt = new Unit("43rd Corps Artillery", "sovietartillerycounter", 8, 3, "art", 1, false);
            gameBoard.get(8).set(3, s43CArt);
            Unit s5thTC = new Unit("5th Tank Corps", "sovietarmourcounter", 7, 3, "arm", 1, false);
            gameBoard.get(7).set(3, s5thTC);
            Unit s6thTC = new Unit("6th Tank Corps", "sovietarmourcounter", 7, 4, "arm", 1, false);
            gameBoard.get(7).set(4, s6thTC);
            Unit s8thGRC = new Unit("8th Guards Rifle Corps", "sovietinfcounter", 7, 5, "inf", 1, false);
            gameBoard.get(7).set(5, s8thGRC);
            Unit s2ndGCC = new Unit("2nd Guards Cavalry Corps", "sovietcavcounter", 8, 5, "inf", 1, false);
            gameBoard.get(8).set(5, s2ndGCC);
            Unit s331stInf = new Unit("331st Rifle Division", "sovietinfcounter", 9, 5, "inf", 1, false);
            gameBoard.get(9).set(5, s331stInf);
            Unit s350thInf = new Unit("350th Rifle Division", "sovietinfcounter", 8, 4, "inf", 1, false);
            gameBoard.get(8).set(4, s350thInf);
            Unit s20thArmy = new Unit("20th Army", "sovietarmourcounter", 9, 4, "arm", 1, false);
            gameBoard.get(9).set(4, s20thArmy);
            Unit s9thTC = new Unit("9th Tank Corps", "sovietarmourcounter", 8, 6, "arm", 1, false);
            gameBoard.get(8).set(6, s9thTC);
            Unit s5thArmy = new Unit("5th Army", "sovietarmourcounter", 8, 7, "arm", 1, false);
            gameBoard.get(8).set(7, s5thArmy);
            Unit s10thTC = new Unit("10th Tank Corps", "sovietarmourcounter", 8, 8, "arm", 1, false);
            gameBoard.get(8).set(8, s10thTC);
            Unit s3rdTC = new Unit("3rd Tank Corps", "sovietarmourcounter", 7, 8, "arm", 1, false);
            gameBoard.get(7).set(8, s3rdTC);
            Unit s33rdArmy = new Unit("33rd Army", "sovietinfcounter", 7, 9, "inf", 1, false);
            gameBoard.get(7).set(9, s33rdArmy);

            Unit g41stPzC = new Unit("XXXXI Panzer Corps", "germanarmourcounter", 0, 9, "arm", 2, false);
            gameBoard.get(0).set(9, g41stPzC);
            Unit g19thPz = new Unit("19th Panzer Division", "germanarmourcounter", 1, 8, "arm", 2, false);
            gameBoard.get(1).set(8, g19thPz);
            Unit g56thInf = new Unit("56th Infantry Division", "germaninfcounter", 1, 9, "inf", 2, false);
            gameBoard.get(1).set(9, g56thInf);
            Unit g11thPz = new Unit("11th Panzer Division", "germanarmourcounter", 1, 7, "arm", 2, false);
            gameBoard.get(1).set(7, g11thPz);
            Unit g1stPz = new Unit("1st Panzer Division", "germanarmourcounter", 1, 6, "arm", 2, false);
            gameBoard.get(1).set(6, g1stPz);
            Unit gHMB441 = new Unit("Heavy Mortar Battalion 441", "germanartillerycounter", 2, 6, "art", 2, false);
            gameBoard.get(2).set(6, gHMB441);
            Unit gEB441 = new Unit("Eastern Battalion 441", "germaninfcounter", 1, 6, "inf", 2, false);
            gameBoard.get(1).set(6, gEB441);

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

