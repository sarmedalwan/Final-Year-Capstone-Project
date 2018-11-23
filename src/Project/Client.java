package Project;

import CE303Project.GameFrame;
import CE303Project.GamePanel;
import CE303Project.GameState;
import CE303Project.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static CE303Project.GameState.COLUMNS;
import static CE303Project.GameState.ROWS;

public class Client {
    static int faction;
    static PrintWriter out;
    static int[][] a;
    public static void main(String args[]) throws IOException {
        final String host = "localhost";
        final int portNumber = 81;
        a = new int[10][6];
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

        Socket socket = new Socket(host, portNumber);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        GameState.updateBoard(a);

        faction = Integer.parseInt(br.readLine());
        System.out.println("Server says: You are player " + faction);

        GamePanel gamePanel = new GamePanel(a, faction);
        new GameFrame(gamePanel, "CE303 Game by Sarmed Alwan, 1603088");

        while(true) {
            int playerMove = Integer.parseInt(br.readLine());
            System.out.println("Server says:" + playerMove);
            int xMove = Integer.parseInt(br.readLine());
            System.out.println("Server says:" + xMove);
            int yMove = Integer.parseInt(br.readLine());
            System.out.println("Server says:" + yMove);
            int a[][] = GameState.getBoard();
            a[xMove][yMove] = playerMove;
            GameState.updateBoard(a);
            gamePanel.repaint();
        }

        }
        public static void makeMove(Move move){
            out.println(faction);
            out.println(move.getFirstMove().getX());
            out.println(move.getFirstMove().getY());
        }
    }