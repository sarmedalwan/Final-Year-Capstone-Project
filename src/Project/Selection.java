package Project;

import java.util.ArrayList;
import static Project.GameState.width;
import static Project.GameState.height;

public class Selection {
    public static Unit currentSelected(ArrayList<ArrayList<Unit>> gameBoard){
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (gameBoard.get(i).get(j) != null && gameBoard.get(i).get(j).getSelection()) {
                    gameBoard.get(i).get(j).setSelection(false);
                    return gameBoard.get(i).get(j);
                }
            }
        }
        return null;
    }
}