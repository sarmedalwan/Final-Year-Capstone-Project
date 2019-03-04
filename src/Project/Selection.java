package Project;

import java.util.ArrayList;
import static Project.GameState.width;
import static Project.GameState.height;

public class Selection {
    public static Unit currentSelected(ArrayList<ArrayList<Unit>> gameBoard){ //Returns the currently selected unit from the unit board, or returns null if there isn't one
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (gameBoard.get(i).get(j) != null && gameBoard.get(i).get(j).getSelection()) { //Checks each unit in the board and checks if it's selected. If it is, it returns it
                    gameBoard.get(i).get(j).setSelection(false);
                    return gameBoard.get(i).get(j);
                }
            }
        }
        return null; //If there's no selected unit, return null
    }
}