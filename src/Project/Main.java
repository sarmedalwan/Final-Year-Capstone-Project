package Project;

import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) throws IOException {
        //new Server().start(); //FOR TESTING
        GameState.setLastMovedPlayer(2);
        new MenuFrame("Operation Mars");
    }
}