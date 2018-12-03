package Project;

import static Project.GameState.width;
import static Project.GameState.height;

public class Selection {
    public static Unit selected(Unit[][] gameGrid){
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (gameGrid[i][j] != null && gameGrid[i][j].selected) {
                    gameGrid[i][j].setSelection(false);
                    return gameGrid[i][j];
                }
            }
        }
        return null;
    }
}