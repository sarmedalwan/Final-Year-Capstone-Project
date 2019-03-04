package Project;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        GameState.setLastMovedPlayer(2); //Makes it so that player 1 will always go first
        new MenuFrame("Operation Mars"); //Creates the main menu frame
    }
}