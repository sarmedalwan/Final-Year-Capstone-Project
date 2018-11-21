package Project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {

    public static void main(String args[]) throws IOException {
        final int portNumber = 81;
        List<Socket> sockets = new ArrayList<>();
        System.out.println("Creating server socket on port " + portNumber);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Unit[][] a = GameState.getNewBoard();
        while (true) {
            Socket socket = serverSocket.accept();
            sockets.add(socket);
            new ClientThread(socket, sockets, a).start();
        }
        }
    }