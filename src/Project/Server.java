package Project;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    public void run() {
        int portNumber = 8888;
        try { //Creates a server once the user clicks Host Game
            List<Socket> socketList = new ArrayList<>(); //Stores the connected sockets
            ServerSocket serverSocket = new ServerSocket(portNumber);
            while (true) {
                if (!GameState.getSomeoneHasMoved()) { //If no one has moved yet, accept any other clients who connect
                    Socket socket = serverSocket.accept(); //No limit on number of clients because any beyond 2 can just be treated as spectators
                    socketList.add(socket);
                    new ServerThread(socket, socketList).start(); //Creates a new thread to communicate with the clients
                }
            }
        } catch(IOException e){
            JOptionPane.showMessageDialog(null, "User disconnected. Please restart game.", "Connection Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}