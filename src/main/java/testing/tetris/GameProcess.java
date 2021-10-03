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

    public void getScoreForLines(int lines){
        switch (lines){
            case 1:
                score+=100;
                break;
            case 2:
                score+=300;
                break;
            case 3:
                score+=700;
                break;
            case 4:
                score+=1500;
                break;
        }
    }

    public void checkForDoneLines(){
        int doneLineCount = 0;
        int blockCount = 0;
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10; j++){
                if (gameField[i][j] == 1){
                    blockCount++;
                }
            }
            if (blockCount == 10){
                doneLineCount++;
                blockCount = 0;
            } else {
                getScoreForLines(doneLineCount);
                doneLineCount = 0;
            }
        }
        if (doneLineCount != 0){
            getScoreForLines(doneLineCount);
        }
    }

    public void deleteLines(){

    }
}
