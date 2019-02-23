package Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.net.Socket;

public class ServerThread extends Thread {
    protected Socket socket;
    private List<Socket> sockets;
    public ArrayList<ArrayList<Unit>> gameBoard;

    public ServerThread(Socket clientSocket, List<Socket> sockets) {
        this.socket = clientSocket;
        this.sockets = sockets;
    }

    @Override
    public void run() {
        InputStream inputStream;
        DataOutputStream dataOutputStream;
        Gson gson = new Gson();
        BufferedReader br;

        try {
            //System.out.println("in serverthread");
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.println(sockets.size());
            //String boardJson = gson.toJson(gameBoard);
            //printWriter.println(boardJson);
        } catch(Exception e){
            System.out.println(e);
        }
        int lastMovedPlayer = 20;
        while (true) {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int factionWhoMoved = Integer.parseInt(br.readLine());
                String playerBoard = br.readLine();
                if (factionWhoMoved == lastMovedPlayer || lastMovedPlayer == 20) {
                    int index = 0;
                    for (Socket socket : sockets) {
                        index++;
                        if (index != factionWhoMoved) {
                            OutputStream outputStr = socket.getOutputStream();
                            PrintWriter printWriter = new PrintWriter(outputStr, true);
                            printWriter.println(factionWhoMoved);
                            printWriter.println(playerBoard);
                        }
                    }
                } else{
                    System.out.println("Cannot move twice in a row");
                }
                lastMovedPlayer = factionWhoMoved;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}