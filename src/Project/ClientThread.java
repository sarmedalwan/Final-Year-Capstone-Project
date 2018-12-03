package Project;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientThread extends Thread {
    protected Socket socket;
    private List<Socket> sockets;
    public int[] playerpositions;
    public Unit[][] gameBoard;

    public ClientThread(Socket clientSocket, List<Socket> sockets, Unit[][] gameBoard) {
        this.socket = clientSocket;
        this.sockets = sockets;
        this.gameBoard = gameBoard;
    }

    public void run() {
        InputStream inp;
        BufferedReader br;
        DataOutputStream out;
        Gson gson = new Gson();

        try {
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            String boardJson = gson.toJson(gameBoard);
            pw.println(boardJson);
            pw.println(sockets.size());
        } catch(Exception e){
            System.out.println(e);
        }
        while (true) {
            try {
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int playerMove = Integer.parseInt(br.readLine());
                int xMove = Integer.parseInt(br.readLine());
                int yMove = Integer.parseInt(br.readLine());
                for (Socket socket:sockets) {
                    OutputStream outputStr = socket.getOutputStream();
                    PrintWriter printWr = new PrintWriter(outputStr, true);
                    printWr.println(playerMove);
                    printWr.println(xMove);
                    printWr.println(yMove);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}