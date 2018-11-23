package Project;

import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    static int faction;
    static PrintWriter out;
    public static void main(String args[]) throws IOException {
        String host = "localhost";
        int portNumber = 8888;
        Gson gson = new Gson();
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

        Socket socket = new Socket(host, portNumber);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        Unit[][] gameBoard = GameState.newUnitBoard();
        GameState.updateBoard(gameBoard);
        faction = Integer.parseInt(br.readLine());
        System.out.println("Welcome, Player " + faction);

        new MainFrame("Sarmed Alwan's Operation Mars");

        while(true) {
            Unit[][] unitBoard = gson.fromJson(br.readLine(), Unit[][].class);
            GameState.updateBoard(unitBoard);
        }

        }
        public static void makeMove(Move move){
            out.println(move);
        }
    }