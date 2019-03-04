package Project;

import java.io.*;
import java.util.List;
import javax.swing.*;
import java.net.Socket;

public class ServerThread extends Thread {
    protected Socket socket;
    private List<Socket> sockets;

    public ServerThread(Socket clientSocket, List<Socket> sockets) {
        this.socket = clientSocket;
        this.sockets = sockets; //Receives the client socket and the list of all of the sockets connected
    }

    @Override
    public void run() {
        BufferedReader br; //Reader for reading inputs from the clients
        try {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.println(sockets.size()); //Tells the client which player they are by sending the size of the socket list, so if they joined 2nd they'll be player 2 because the size of the list will be 2
        } catch(Exception e){
            System.out.println(e);
        }
        int lastMovedPlayer = 20; //Tells the game that it's the first turn
        while (true) {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int factionWhoMoved = Integer.parseInt(br.readLine()); //Receives the number of the last player who moved
                String playerBoard = br.readLine(); //Receives the serialised form of the game board from the client
                GameState.setSomeoneHasMoved(true); //Records that someone has moved
                if (factionWhoMoved == lastMovedPlayer || lastMovedPlayer == 20 || factionWhoMoved == 11 || factionWhoMoved == 12) { //Broadcast the changes only to the others (not the one who just moved)
                    int index = 0;
                    for (Socket socket : sockets) { //For all of the connections
                        index++;
                        if (index != factionWhoMoved) {
                            OutputStream outputStr = socket.getOutputStream();
                            PrintWriter printWriter = new PrintWriter(outputStr, true);
                            printWriter.println(factionWhoMoved); //Tell the client who moved
                            printWriter.println(playerBoard); //Tell the client the new state of the board
                        }
                    }
                }
                lastMovedPlayer = factionWhoMoved; //Sets the last moved player as the one who just moved
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "User disconnected. Please restart game.", "Connection Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }
}