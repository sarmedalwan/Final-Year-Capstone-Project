package Project;

import java.io.*;
import java.util.List;
import com.google.gson.Gson;
import java.net.Socket;

public class ServerThread extends Thread {
    protected Socket socket;
    private List<Socket> sockets;
    public Unit[][] gameBoard;

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
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.println(sockets.size());
            String boardJson = gson.toJson(gameBoard);
            //printWriter.println(boardJson);
        } catch(Exception e){
            System.out.println(e);
        }
        while (true) {
            try {
                for (Socket socket:sockets) {
                    OutputStream outputStream = socket.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(outputStream, true);
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String boardJson = gson.toJson(gameBoard);
                    printWriter.println(boardJson);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}