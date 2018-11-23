package Project;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientThread extends Thread {
    protected Socket socket;
    private List<Socket> sockets;
    public Unit[][] gameBoard;

    public ClientThread(Socket clientSocket, List<Socket> sockets) {
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
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            String boardJson = gson.toJson(gameBoard);
            printWriter.println(boardJson);
            printWriter.println(sockets.size());
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