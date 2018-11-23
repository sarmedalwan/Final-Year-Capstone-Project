package Project;

import static Project.GameState.columns;
import static Project.GameState.rows;

public class Selection {
    public static Unit selected(Unit[][] gameGrid){
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                if (gameGrid[i][j] != null) {
                    if (gameGrid[i][j].selected = true) {
                        gameGrid[i][j].setSelection(false);
                        return gameGrid[i][j];
                    }
                }
            }
        }
        return null;
    }
}
