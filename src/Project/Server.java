package Project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    public void run() {
        int portNumber = 8888;
        try {
            List<Socket> socketList = new ArrayList<>();
            System.out.println("Server created: " + portNumber);
            ServerSocket serverSocket = new ServerSocket(portNumber);
            //ArrayList<ArrayList<Unit>> a = GameState.getNewBoard();
            while (true) {
                Socket socket = serverSocket.accept();
                socketList.add(socket);
                new ServerThread(socket, socketList).start();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}