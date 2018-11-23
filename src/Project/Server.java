package Project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String args[]) throws IOException {
        int portNumber = 8888;
        List<Socket> socketList = new ArrayList<>();
        System.out.println("Server created: " + portNumber);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        while (true) {
            Socket socket = serverSocket.accept();
            socketList.add(socket);
            new ServerThread(socket, socketList).start();
        }
        }
    }