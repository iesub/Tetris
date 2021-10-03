package testing.tetris;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameProcess {

    private int[][] gameField = new int[20][10];
    private int score = 0;

    public void initField(){
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10; j++){
                gameField[i][j] = 0;
            }
        }
    }


    public void checkForDoneLines(){
        
    }
}
