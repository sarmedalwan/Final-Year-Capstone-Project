package Project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread extends Thread {
    static int faction;
    static PrintWriter out;
    String host;
    int portNumber;
    Gson gson;
    Socket socket;
    UnitPanel unitPanel;
    TerritoriesPanel territoriesPanel;
    BufferedReader br;
    ArrayList<ArrayList<Unit>> unitBoard;
    public ClientThread(UnitPanel panel, TerritoriesPanel terrPanel, String ip) throws IOException{ //Initialises the thread before starting it
        territoriesPanel = terrPanel;
        unitPanel = panel; //The live unit panel which displays the board
        host = ip;
        portNumber = 8888;
        gson = new Gson();
        socket = new Socket(host, portNumber); //Creates socket connection to the server at port #8888
        br = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Creates reader for receiving strings from the server
        out = new PrintWriter(socket.getOutputStream(), true); //Creates writer to send strings to the server
        unitBoard = GameState.getNewBoard(); //Gets a fresh board
        GameState.updateBoard(unitBoard); //Saves the fresh board
        faction = Integer.parseInt(br.readLine()); //Receives the player's assigned faction from the server
        GameState.setServerJoined(true);
        GameState.setFaction(faction);
        if (faction==2){
            GameState.setLastMovedPlayer(2); //Makes sure that player 1 always goes first at the very start of the game
        }
    }
    public void run() {
        try {
            while (true) { //Keeps listening for server commands until the game is closed
                int lastMovedPlayer = Integer.parseInt(br.readLine()); //Receives the number from the server of the player who just moved
                GameState.setLastMovedPlayer(lastMovedPlayer);
                unitBoard = gson.fromJson(br.readLine(), new TypeToken<ArrayList<ArrayList<Unit>>>() {
                }.getType()); //Receives the updated board from the server
                if(GameState.getFaction()!=lastMovedPlayer) { //If the player isn't the one who just moved, update his info to match the one who just moved
                    unitPanel.updateGrid(unitBoard);
                    unitPanel.updateTerritories();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Disconnected from server. Please restart game.", "Connection Error", JOptionPane.INFORMATION_MESSAGE); //If there's a network error, inform the users
        }
    }
    public static void makeMove(ArrayList<ArrayList<Unit>> unitBoard){ //Send a move to the server, in the form of 2 strings; your faction number, and your updated board
        out.println(GameState.getFaction());
        Gson gson = new Gson();
        out.println(gson.toJson(unitBoard));
    }
}
