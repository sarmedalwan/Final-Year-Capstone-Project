package Project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Sarmed on 05/02/2019.
 */
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
    public ClientThread(UnitPanel panel, TerritoriesPanel terrPanel){
        try {
            territoriesPanel = terrPanel;
            unitPanel = panel;
            host = "localhost";
            portNumber = 8888;
            gson = new Gson();
            System.out.println("Creating socket to '" + host + "' on port " + portNumber);
            socket = new Socket(host, portNumber);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            unitBoard = GameState.getNewBoard();
            GameState.updateBoard(unitBoard);
            faction = Integer.parseInt(br.readLine());
            System.out.println("Welcome, Player " + faction);
            GameState.updateFaction(faction);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            while (true) {
                //GameState.updateBoard(gson.fromJson(br.readLine(), new TypeToken<ArrayList<ArrayList<Unit>>>(){}.getType()));
                int lastMovedPlayer = Integer.parseInt(br.readLine());
                System.out.println("Server says: Player " + lastMovedPlayer + " moved");
                unitPanel.updateGrid(gson.fromJson(br.readLine(), new TypeToken<ArrayList<ArrayList<Unit>>>() {
                }.getType()));
                unitPanel.updateTerritories();
                //unitBoard = GameState.getBoard();
                //GameState.updateBoard(unitBoard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void makeMove(ArrayList<ArrayList<Unit>> unitBoard){
        out.println(faction);
        Gson gson = new Gson();
        out.println(gson.toJson(unitBoard));
    }
}
