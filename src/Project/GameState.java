package Project;

import java.io.IOException;
import java.util.*;

public final class GameState { //Manages current state of the game
    static final int height = 10; //Height and width of the game board
    static final int width = 10;
    static ArrayList<ArrayList<Unit>> gameBoard;
    static int faction; //Stores the player's faction
    static int lastMovedPlayer; //Stores which player moved last
    static int victoryPoints = 0; //Stores the player's victory points gained from kills
    static int enemyVictoryPoints = 0; //Stores the enemy's victory points gained from kills
    static int turnCount = 1; //Stores the current turn of the game; starts at 1
    static int territoryVictoryPoints = 0; //Stores the amount of victory points the player is getting from held territories
    static int enemyTerritoryVictoryPoints = 0;//Stores the amount of victory points the enemy is getting from held territories
    static boolean serverHosted = false; //Stores whether or not this client has already started a server
    static boolean serverJoined = false; //Stores whether or not this client has already joined a server
    static boolean someoneHasMoved = false; //Stores for the server whether or not someone has moved yet. If they have, no one else can join

    public static ArrayList<ArrayList<Unit>> getNewBoard() { //Generates a new historically-based unit board to use for the client
        gameBoard = new ArrayList<ArrayList<Unit>>(10);
        for (int i = 0; i < GameState.width; i++)
        {
            gameBoard.add(new ArrayList<Unit>(10));
            for (int j = 0; j < GameState.height; j++)
            {
                gameBoard.get(i).add(null); //Fills a 2D 10x10 ArrayList with null (blank) values to begin with
            }
        }
        try {
            //Defines all of the Soviet units and puts them in their appropriate locations on the board
            Unit s1stMC = new Unit("1st Mechanised Corps", "sovietinfcounter", 0, 8, "inf", 1, 0, false);
            gameBoard.get(0).set(8, s1stMC);
            Unit s1stMCArt = new Unit("1st MC Artillery", "sovietartillerycounter", 0, 7, "art", 1, 0, false);
            gameBoard.get(0).set(7, s1stMCArt);
            Unit s6thRC = new Unit("6th Rifle Corps", "sovietinfcounter", 0, 6, "inf", 1, 0, false);
            gameBoard.get(0).set(6, s6thRC);
            Unit s6thRCArt = new Unit("6th RC Artillery", "sovietartillerycounter", 0, 5, "art", 1, 0, false);
            gameBoard.get(0).set(5, s6thRCArt);
            Unit s3rdMCArt = new Unit("3rd MC Artillery", "sovietartillerycounter", 0, 4, "art", 1, 0, false);
            gameBoard.get(0).set(4, s3rdMCArt);
            Unit s3rdMC = new Unit("Katukov's 3rd Mechanised Corps", "sovietinfcounter", 1, 4, "inf", 1, 1, false);
            gameBoard.get(1).set(4, s3rdMC);
            Unit s238thInf = new Unit("238th Infantry Division", "sovietinfcounter", 1, 5, "inf", 1, 0, false);
            gameBoard.get(1).set(5, s238thInf);
            Unit s185thInf = new Unit("185th Infantry Division", "sovietinfcounter", 0, 3, "inf", 1, 0, false);
            gameBoard.get(0).set(3, s185thInf);
            Unit s3rdMCArm = new Unit("Katukov's 3rd Mechanised Corps Tank Brigade", "sovietarmourcounter", 1, 2, "arm", 1, 0, false);
            gameBoard.get(1).set(2, s3rdMCArm);
            Unit s3rdMCRes = new Unit("Katukov's 3rd Mechanised Corps Infantry Reserve", "sovietinfcounter", 0, 2, "inf", 1, 0, false);
            gameBoard.get(0).set(2, s3rdMCRes);
            Unit s3rdMCArtRes = new Unit("Katukov's 3rd Mechanised Corps Artillery Reserve", "sovietartillerycounter", 0, 1, "art", 1, 0, false);
            gameBoard.get(0).set(1, s3rdMCArtRes);
            Unit s11thCC = new Unit("11th Cavalry Corps", "sovietcavcounter", 2, 0, "inf", 1, 0, false);
            gameBoard.get(2).set(0, s11thCC);
            Unit s18thCav = new Unit("11th Cavalry Division", "sovietcavcounter", 5, 1, "inf", 1, 1, false);
            gameBoard.get(5).set(1, s18thCav);
            Unit s24thCav = new Unit("24th Cavalry Division", "sovietcavcounter", 4, 1, "inf", 1, 0, false);
            gameBoard.get(4).set(1, s24thCav);
            Unit s82ndCav = new Unit("82nd Cavalry Division", "sovietcavcounter", 1, 1, "inf", 1, 0, false);
            gameBoard.get(1).set(1, s82ndCav);
            Unit s11thCCArt = new Unit("11th Cavalry Corps Artillery Support", "sovietartillerycounter", 3, 0, "art", 1, 0, false);
            gameBoard.get(3).set(0, s11thCCArt);
            Unit s355thInf = new Unit("355th Infantry Division", "sovietinfcounter", 1, 1, "inf", 1, 0, false);
            gameBoard.get(1).set(1, s355thInf);
            Unit s357thInf = new Unit("357th Infantry Division", "sovietinfcounter", 0, 0, "inf", 1, 0, false);
            gameBoard.get(0).set(0, s357thInf);
            Unit s361stInf = new Unit("361st Infantry Division", "sovietinfcounter", 1, 0, "inf", 1, 0, false);
            gameBoard.get(1).set(0, s361stInf);
            Unit s369thInf = new Unit("369th Infantry Division", "sovietinfcounter", 4, 0, "inf", 1, 0, false);
            gameBoard.get(4).set(0, s369thInf);
            Unit s373rdInf = new Unit("373rd Infantry Division", "sovietinfcounter", 5, 0, "inf", 1, 0, false);
            gameBoard.get(5).set(0, s373rdInf);
            Unit s377thInf = new Unit("377th Infantry Division", "sovietinfcounter", 6, 0, "inf", 1, 0, false);
            gameBoard.get(6).set(0, s377thInf);
            Unit s381stInf = new Unit("381st Infantry Division", "sovietinfcounter", 6, 1, "inf", 1, 0, false);
            gameBoard.get(6).set(1, s381stInf);
            Unit s39thArmy = new Unit("39th Army", "sovietarmourcounter", 3, 1, "arm", 1, 1, false);
            gameBoard.get(3).set(1, s39thArmy);
            Unit s30thArmy = new Unit("30th Army", "sovietarmourcounter", 6, 2, "arm", 1, 1, false);
            gameBoard.get(6).set(2, s30thArmy);
            Unit s174thInf = new Unit("174th Rifle Division", "sovietinfcounter", 7, 2, "inf", 1, 0, false);
            gameBoard.get(7).set(2, s174thInf);
            Unit s178thInf = new Unit("178th Rifle Division", "sovietinfcounter", 8, 2, "inf", 1, 0, false);
            gameBoard.get(8).set(2, s178thInf);
            Unit s243rdInf = new Unit("243rd Rifle Division", "sovietinfcounter", 9, 2, "inf", 1, 0, false);
            gameBoard.get(9).set(2, s243rdInf);
            Unit s348thInf = new Unit("348th Rifle Division", "sovietinfcounter", 7, 1, "inf", 1, 0, false);
            gameBoard.get(7).set(1, s348thInf);
            Unit s359thInf = new Unit("359th Rifle Division", "sovietinfcounter", 7, 0, "inf", 1, 0, false);
            gameBoard.get(7).set(0, s359thInf);
            Unit s363rdInf = new Unit("343rd Rifle Division", "sovietinfcounter", 8, 0, "inf", 1, 0, false);
            gameBoard.get(8).set(0, s363rdInf);
            Unit s371stInf = new Unit("371st Rifle Division", "sovietinfcounter", 8, 1, "inf", 1, 0, false);
            gameBoard.get(8).set(1, s371stInf);
            Unit s375thInf = new Unit("375th Rifle Division", "sovietinfcounter", 9, 1, "inf", 1, 0, false);
            gameBoard.get(9).set(1, s375thInf);
            Unit s379thInf = new Unit("379th Rifle Division", "sovietinfcounter", 9, 0, "inf", 1, 0, false);
            gameBoard.get(9).set(0, s379thInf);
            Unit s110thArm = new Unit("110th Tank Division", "sovietarmourcounter", 8, 2, "arm", 1, 0, false);
            gameBoard.get(8).set(2, s110thArm);
            Unit s43CArt = new Unit("43rd Corps Artillery", "sovietartillerycounter", 8, 3, "art", 1, 0, false);
            gameBoard.get(8).set(3, s43CArt);
            Unit s5thTC = new Unit("5th Tank Corps", "sovietarmourcounter", 7, 3, "arm", 1, 0, false);
            gameBoard.get(7).set(3, s5thTC);
            Unit s6thTC = new Unit("6th Tank Corps", "sovietarmourcounter", 7, 4, "arm", 1, 0, false);
            gameBoard.get(7).set(4, s6thTC);
            Unit s8thGRC = new Unit("8th Guards Rifle Corps", "sovietinfcounter", 7, 5, "inf", 1, 1, false);
            gameBoard.get(7).set(5, s8thGRC);
            Unit s2ndGCC = new Unit("2nd Guards Cavalry Corps", "sovietcavcounter", 8, 5, "inf", 1, 0, false);
            gameBoard.get(8).set(5, s2ndGCC);
            Unit s331stInf = new Unit("331st Rifle Division", "sovietinfcounter", 9, 5, "inf", 1, 0, false);
            gameBoard.get(9).set(5, s331stInf);
            Unit s350thInf = new Unit("350th Rifle Division", "sovietinfcounter", 8, 4, "inf", 1, 0, false);
            gameBoard.get(8).set(4, s350thInf);
            Unit s20thArmy = new Unit("20th Army", "sovietarmourcounter", 9, 4, "arm", 1, 0, false);
            gameBoard.get(9).set(4, s20thArmy);
            Unit s9thTC = new Unit("9th Tank Corps", "sovietarmourcounter", 8, 6, "arm", 1, 0, false);
            gameBoard.get(8).set(6, s9thTC);
            Unit s5thArmy = new Unit("5th Army", "sovietarmourcounter", 8, 7, "arm", 1, 0, false);
            gameBoard.get(8).set(7, s5thArmy);
            Unit s10thTC = new Unit("10th Tank Corps", "sovietarmourcounter", 8, 8, "arm", 1, 0, false);
            gameBoard.get(8).set(8, s10thTC);
            Unit s3rdTC = new Unit("3rd Tank Corps", "sovietarmourcounter", 7, 8, "arm", 1, 0, false);
            gameBoard.get(7).set(8, s3rdTC);
            Unit s33rdArmy = new Unit("33rd Army", "sovietinfcounter", 7, 9, "inf", 1, 1, false);
            gameBoard.get(7).set(9, s33rdArmy);

            //Defines all of the German units and puts them in their appropriate locations on the board
            Unit g41stPzC = new Unit("XXXXI Panzer Corps", "germanarmourcounter", 0, 9, "arm", 2, 1, false);
            gameBoard.get(0).set(9, g41stPzC);
            Unit g19thPz = new Unit("19th Panzer Division", "germanarmourcounter", 1, 8, "arm", 2, 1, false);
            gameBoard.get(1).set(8, g19thPz);
            Unit g56thInf = new Unit("56th Infantry Division", "germaninfcounter", 1, 9, "inf", 2, 1, false);
            gameBoard.get(1).set(9, g56thInf);
            Unit g11thPz = new Unit("11th Panzer Division", "germanarmourcounter", 1, 7, "arm", 2, 2, false);
            gameBoard.get(1).set(7, g11thPz);
            Unit g1stPz = new Unit("1st Panzer Division", "germanarmourcounter", 2, 5, "arm", 2, 2, false);
            gameBoard.get(2).set(5, g1stPz);
            Unit gHMB441 = new Unit("Heavy Mortar Battalion 441", "germanartillerycounter", 2, 6, "art", 2, 1, false);
            gameBoard.get(2).set(6, gHMB441);
            Unit gEB441 = new Unit("Eastern Battalion 441", "germaninfcounter", 1, 6, "inf", 2, 0, false);
            gameBoard.get(1).set(6, gEB441);
            Unit gArt73 = new Unit("Artillery Regiment 73", "germanartillerycounter", 2, 4, "art", 2, 1, false);
            gameBoard.get(2).set(4, gArt73);
            Unit g1stMInf = new Unit("1st Motorised Infantry Brigade", "germaninfcounter", 2, 3, "inf", 2, 1, false);
            gameBoard.get(2).set(3, g1stMInf);
            Unit gPzGGD = new Unit("Panzergrenadier Division Großdeutschland", "germaninfcounter", 1, 3, "inf", 2, 4, false);
            gameBoard.get(1).set(3, gPzGGD);
            Unit g253rdInf = new Unit("253rd Infantry Division", "germaninfcounter", 2, 2, "inf", 2, 3, false);
            gameBoard.get(2).set(2, g253rdInf);
            Unit g86thInf = new Unit("86th Infantry Division", "germaninfcounter", 2, 1, "inf", 2, 2, false);
            gameBoard.get(2).set(1, g86thInf);
            Unit g1stPzD = new Unit("1st Panzer Division Detachment", "germanarmourcounter", 3, 2, "arm", 2, 2, false);
            gameBoard.get(3).set(2, g1stPzD);
            Unit g95thInf = new Unit("95th Infantry Division", "germaninfcounter", 4, 2, "inf", 2, 3, false);
            gameBoard.get(4).set(2, g95thInf);
            Unit g14thPzG = new Unit("14th Panzergrenadier Division", "germaninfcounter", 5, 2, "inf", 2, 3, false);
            gameBoard.get(5).set(2, g14thPzG);
            Unit gArt816 = new Unit("Heavy Artillery Division 816", "germanartillerycounter", 5, 3, "art", 2, 1, false);
            gameBoard.get(5).set(3, gArt816);
            Unit gArt808 = new Unit("Heavy Artillery Division 808", "germanartillerycounter", 6, 3, "art", 2, 1, false);
            gameBoard.get(6).set(3, gArt808);
            Unit gArt620 = new Unit("Heavy Artillery Division 620", "germanartillerycounter", 6, 4, "art", 2, 1, false);
            gameBoard.get(6).set(4, gArt620);
            Unit gAG189 = new Unit("Assault Gun Department 189", "germanarmourcounter", 6, 5, "arm", 2, 1, false);
            gameBoard.get(6).set(5, gAG189);
            Unit g9thPz = new Unit("9th Panzer Division", "germanarmourcounter", 6, 6, "arm", 2, 1, false);
            gameBoard.get(6).set(6, g9thPz);
            Unit g78thInf = new Unit("78th Infantry Division", "germaninfcounter", 6, 7, "inf", 2, 2, false);
            gameBoard.get(6).set(7, g78thInf);
            Unit g102ndInf = new Unit("102nd Infantry Division", "germaninfcounter", 6, 9, "inf", 2, 1, false);
            gameBoard.get(6).set(9, g102ndInf);
            Unit gArt637 = new Unit("Heavy Artillery Division 637", "germanartillerycounter", 7, 6, "art", 2, 1, false);
            gameBoard.get(7).set(6, gArt637);
            Unit gArt740 = new Unit("Heavy Artillery Division 740", "germanartillerycounter", 7, 7, "art", 2, 1, false);
            gameBoard.get(7).set(7, gArt740);
            Unit g255thInf = new Unit("255th Infantry Division", "germaninfcounter", 6, 8, "inf", 2, 1, false);
            gameBoard.get(6).set(8, g255thInf);
            Unit g31stInf = new Unit("31st Infantry Division", "germaninfcounter", 4, 5, "inf", 2, 2, false);
            gameBoard.get(4).set(5, g31stInf);
            Unit g183rdInf = new Unit("183rd Infantry Division", "germaninfcounter", 3, 7, "inf", 2, 1, false);
            gameBoard.get(3).set(7, g183rdInf);

        } catch(Exception e){
            e.printStackTrace();
        }
        return gameBoard; //Returns the generated fresh board
    }

    static ArrayList<ArrayList<Unit>> getBoard() {
        return gameBoard;
    } //Returns the currently stored game board

    static void setTurnCount(int newTurnCount){turnCount = newTurnCount;} //Lets the client update the turn counter. This has been made as a setter rather than a simple incrementation method with no arguments for expandability
    static int getTurnCount(){
        return turnCount;
    } //Lets the client check the current turn number

    static void setVictoryPoints(int newVictoryPoints){victoryPoints = newVictoryPoints;} //Lets the client update the victory points value
    static int getVictoryPoints(){
        return victoryPoints;
    } //Lets the client check the victory points value

    static void setEnemyVictoryPoints(int newVictoryPoints){enemyVictoryPoints = newVictoryPoints;} //Lets the client update the enemy victory points value
    static int getEnemyVictoryPoints(){return enemyVictoryPoints;} //Lets the client check the enemy victory points value

    static void setFaction(int newFaction){ faction = newFaction;} //Lets the client update their faction (player number)
    static int getFaction(){return faction;} //Lets the client check their faction (player number)

    static void setServerHosted(boolean hosted){ serverHosted = hosted;} //Lets the client save whether or not they've hosted a server
    static boolean getServerHosted(){return serverHosted;} //Lets the client check whether or not they've hosted a server

    static void setServerJoined(boolean joined){ serverJoined = joined;} //Lets the client save whether or not they've hosted a server
    static boolean getServerJoined(){return serverJoined;} //Lets the client check whether or not they've hosted a server

    static void setSomeoneHasMoved(boolean moved){ someoneHasMoved = moved;} //Lets the client save whether or not someone has moved yet
    static boolean getSomeoneHasMoved(){return someoneHasMoved;} //Lets the server check if someone has moved yet

    static int getLastMovedPlayer(){return lastMovedPlayer;} //Lets the client update which player last moved
    static void setLastMovedPlayer(int lastMoved){ lastMovedPlayer = lastMoved;} //Lets the client check which player last moved

    static int getTerritoryVictoryPoints(int[][] territories){ //Checks for how many victory points the player currently has from the territories they hold
        territoryVictoryPoints = 0;
        if (territories[2][1] == GameState.getFaction()-1) {territoryVictoryPoints += 5;}
        if (territories[0][2] == GameState.getFaction()-1) {territoryVictoryPoints += 5;}
        if (territories[1][6] == GameState.getFaction()-1) {territoryVictoryPoints += 5;}
        if (territories[2][3] == GameState.getFaction()-1) {territoryVictoryPoints += 5;}
        if (territories[3][7] == GameState.getFaction()-1) {territoryVictoryPoints += 5;}
        if (territories[4][6] == GameState.getFaction()-1) {territoryVictoryPoints += 5;}
        if (territories[6][3] == GameState.getFaction()-1) {territoryVictoryPoints += 5;}
        if (territories[6][5] == GameState.getFaction()-1) {territoryVictoryPoints += 5;}
        if (territories[8][0] == GameState.getFaction()-1) {territoryVictoryPoints += 5;}
        if (territories[8][5] == GameState.getFaction()-1) {territoryVictoryPoints += 5;} //All towns on the map give 5 victory points to the holder

        if (territories[5][2] == GameState.getFaction()-1) {territoryVictoryPoints += 10;}
        if (territories[6][7] == GameState.getFaction()-1) {territoryVictoryPoints += 10;} //Except for Rzhev and Vyazma, which give 10 victory points, because they're more important

        return territoryVictoryPoints;
    }

    static int getEnemyTerritoryVictoryPoints(int[][] territories){ //Checks for how many victory points the enemy currently has from the territories they hold
        enemyTerritoryVictoryPoints = 0;
        if (territories[2][1] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 5;}
        if (territories[0][2] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 5;}
        if (territories[1][6] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 5;}
        if (territories[2][3] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 5;}
        if (territories[3][7] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 5;}
        if (territories[4][6] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 5;}
        if (territories[6][3] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 5;}
        if (territories[6][5] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 5;}
        if (territories[8][0] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 5;}
        if (territories[8][4] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 5;} //All towns on the map give 5 victory points to the holder

        if (territories[5][2] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 10;}
        if (territories[6][7] != GameState.getFaction()-1) {enemyTerritoryVictoryPoints += 10;} //Except for Rzhev and Vyazma, which give 10 victory points, because they're more important

        return enemyTerritoryVictoryPoints;
    }

    static void combat(Unit attacker, Unit defender, int[][] territories){ //Simulates combat between two provided units
        Random random = new Random();
        Boolean attackerEncircled = true; //Defines whether or not a unit is encircled (whether or not it has all 4 directions around it controlled by the enemy)
        Boolean defenderEncircled = true;
        int attackerMean, defenderMean, attackerLosses, defenderLosses; //The means of the standard distributions of damage being dealt to each unit, and the final damage being dealt to each unit
        int standardDeviation = 8; //Value of 1 standard deviation from the mean
        int typeBonus = 35; //Combat bonus from unit types (Infantry vs artillery etc)
        int townDefenceBonus = 15; //Bonus for defending a town
        int vetBonus = 5; //Combat bonus from unit veterancy (5 bonus damage dealt and 5 less damage received, per veterancy level)
        int encirclementPenalty = 30; //Combat damage penalty for being encircled
        attackerMean = 36; //Mean damage taken for the attacking unit
        defenderMean = 30; //Mean damage taken for the defending unit
        if (attacker.getType().equals("inf") && defender.getType().equals("arm")){attackerMean+=typeBonus;} //Infantry performs worse against armour
        if (defender.getType().equals("inf") && attacker.getType().equals("arm")){defenderMean+=typeBonus;}
        if (attacker.getType().equals("arm") && defender.getType().equals("art")){attackerMean+=typeBonus;} //Armour performs worse against artillery
        if (defender.getType().equals("arm") && attacker.getType().equals("art")){defenderMean+=typeBonus;}
        if (attacker.getType().equals("art") && defender.getType().equals("inf")){attackerMean+=typeBonus;} //Artillery performs worse against infantry
        if (defender.getType().equals("art") && attacker.getType().equals("inf")){defenderMean+=typeBonus;}
        attackerLosses = (int)(random.nextGaussian()*standardDeviation+attackerMean); //Calculate losses based on the above calculated mean and the standard deviation. This is done with a gaussian curve (normal distribution)
        defenderLosses = (int)(random.nextGaussian()*standardDeviation+defenderMean);

        //Take unit veterancies into account by reducing damage taken and increasing damage dealt
        attackerLosses -= attacker.getVet()*vetBonus;
        defenderLosses += attacker.getVet()*vetBonus;

        attackerLosses += defender.getVet()*vetBonus;
        defenderLosses -= defender.getVet()*vetBonus;

        //Take town defence bonus into account by adding the town defence bonus to the defender if they're on one of the towns' locations
        if (((defender.getxLocation() == 2)&&(defender.getyLocation()==1))
                || ((defender.getxLocation() == 0)&&(defender.getyLocation()==2))
                || ((defender.getxLocation() == 1)&&(defender.getyLocation()==6))
                || ((defender.getxLocation() == 2)&&(defender.getyLocation()==3))
                || ((defender.getxLocation() == 3)&&(defender.getyLocation()==7))
                || ((defender.getxLocation() == 4)&&(defender.getyLocation()==6))
                || ((defender.getxLocation() == 5)&&(defender.getyLocation()==2))
                || ((defender.getxLocation() == 6)&&(defender.getyLocation()==3))
                || ((defender.getxLocation() == 6)&&(defender.getyLocation()==5))
                || ((defender.getxLocation() == 6)&&(defender.getyLocation()==7))
                || ((defender.getxLocation() == 8)&&(defender.getyLocation()==0))
                || ((defender.getxLocation() == 8)&&(defender.getyLocation()==4))){
            defenderLosses-=townDefenceBonus;
            attackerLosses+=townDefenceBonus;
        }

        int w = 10; //Width and height of the territory grid
        int h = 10;

        //Check if attacker is encircled
        if ((attacker.getyLocation() + 1) < h && (territories[attacker.getxLocation()][attacker.getyLocation() + 1] == attacker.getFaction()-1)){
            attackerEncircled = false;
        }
        if ((attacker.getyLocation() - 1) >= 0 && (territories[attacker.getxLocation()][attacker.getyLocation() - 1] == attacker.getFaction()-1)){
            attackerEncircled = false;
        }
        if ((attacker.getxLocation() + 1) < w && (territories[attacker.getxLocation()+1][attacker.getyLocation()] == attacker.getFaction()-1)){
            attackerEncircled = false;
        }
        if ((attacker.getxLocation() -1) >= 0 && (territories[attacker.getxLocation()-1][attacker.getyLocation()] == attacker.getFaction()-1)){
            attackerEncircled = false;
        }

        //Check if defender is encircled
        if ((defender.getyLocation() + 1) < h && (territories[defender.getxLocation()][defender.getyLocation() + 1] == defender.getFaction()-1)){
            defenderEncircled = false;
        }
        if ((defender.getyLocation() - 1) >= 0 && (territories[defender.getxLocation()][defender.getyLocation() - 1] == defender.getFaction()-1)){
            defenderEncircled = false;
        }
        if ((defender.getxLocation() + 1) < w && (territories[defender.getxLocation()+1][defender.getyLocation()] == defender.getFaction()-1)){
            defenderEncircled = false;
        }
        if ((defender.getxLocation() -1) >= 0 && (territories[defender.getxLocation()-1][defender.getyLocation()] == defender.getFaction()-1)){
            defenderEncircled = false;
        }

        //If attacker is encircled, make them take more damage and deal less
        if (attackerEncircled){
            attackerLosses+=encirclementPenalty;
            defenderLosses-=encirclementPenalty/2;
        }

        //If defender is encircled, make them take more damage and deal less
        if (defenderEncircled){
            defenderLosses+=encirclementPenalty;
            attackerLosses-=encirclementPenalty/2;
        }

        if (attackerLosses<7){attackerLosses = 7;} //Inflicts a minimum of 7 damage on each unit, to stop a single unit from continuing to live through many battles by chance
        if (defenderLosses<7){defenderLosses = 7;}

        attacker.setHealth(attacker.getHealth()-attackerLosses); //Finally apply the changes to the units' health
        defender.setHealth(defender.getHealth()-defenderLosses);
    }

    public static int[][] getNewTerritories(){ //Generates a new historically-based territory board to use for the client. 0 is taken as belonging to the Soviets and 1 is taken as belonging to the Germans
        int w = 10;
        int h = 10;
        int[][] owners = new int[w][h];
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                owners[i][j] = 0;
            }
        } //This is just a way of assigning the same owner to long lines of tiles
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

    static void updateBoard(ArrayList<ArrayList<Unit>> updatedBoard){ //Updates the stored board from a new board by looping through each tile in the 2D ArrayList and copying the Unit object over if there is one
        for (int i = 0; i < GameState.width; i++) {
            for (int j = 0; j < GameState.height; j++) {
                if(gameBoard.get(i).get(j) != null && updatedBoard.get(i).get(j) != null) {
                    try {
                        gameBoard.get(i).set(j, new Unit(updatedBoard.get(i).get(j)));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    static Boolean isLegal(Unit selected, int x, int y){ //Checks if a move is legal
        if (selected.getxLocation() == x && selected.getyLocation() == y){ //Illegal if the same tile on which the selected unit sits is clicked
            return false;
        }
        if (selected.getType().equals("arm") && (!(Math.abs(selected.getxLocation() - x) > 1) && !(Math.abs(selected.getyLocation() - y) > 1))) { //If the unit isn't being moved more than 1 tile//Armour can move 1 square in all directions, whereas other units can't move diagonally (coded below)
            return true;
        } else if (!selected.getType().equals("arm") && (!(Math.abs(selected.getxLocation() - x) > 1) && !(Math.abs(selected.getyLocation() - y) > 1)) //If the unit isn't being moved more than 1 tile
                && !((Math.abs(selected.getxLocation() - x) > 0) && (Math.abs(selected.getyLocation() - y) > 0))){ //And the X and Y coordinates aren't both being changed (only cardinal directions allowed for infantry and artillery)
            return true;
        }
        return false; //If none of the legal cases are met, false is returned to signal that the move is illegal
    }

    static Boolean isNear(Unit unit, ArrayList<ArrayList<Unit>> unitBoard){ //Determines whether or not a unit is nearby (adjacent) to your units
        if (unit.getFaction() == faction){ //If it's your unit, it is
            return true;
        }
        int i = unit.getxLocation();
        int j = unit.getyLocation();
        if((i<9&&j<9&&(unitBoard.get(i+1).get(j+1)!=null)&&unitBoard.get(i+1).get(j+1).getFaction()==faction) //Checks that the check isn't out of bounds or null
                || (i<9&&(unitBoard.get(i+1).get(j)!=null)&&unitBoard.get(i+1).get(j).getFaction()==faction) //Then checks all 8 adjacent positions for friendly units
                || (i<9&&j>0&&(unitBoard.get(i+1).get(j-1)!=null)&&unitBoard.get(i+1).get(j-1).getFaction()==faction)
                || (j>0&&(unitBoard.get(i).get(j-1)!=null)&&unitBoard.get(i).get(j-1).getFaction()==faction)
                || (i>0&&j>0&&(unitBoard.get(i-1).get(j-1)!=null)&&unitBoard.get(i-1).get(j-1).getFaction()==faction)
                || (i>0&&(unitBoard.get(i-1).get(j)!=null)&&unitBoard.get(i-1).get(j).getFaction()==faction)
                || (i>0&&j<9&&(unitBoard.get(i-1).get(j+1)!=null)&&unitBoard.get(i-1).get(j+1).getFaction()==faction)
                || (j<9&&(unitBoard.get(i).get(j+1)!=null)&&unitBoard.get(i).get(j+1).getFaction()==faction)
                ) {
            return true; //If one is found, the unit is nearby
        }
        return false; //Otherwise, it isn't
    }
}

