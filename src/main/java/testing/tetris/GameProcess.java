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

    public boolean lineIsFull(int i){
        for (int j = 0; j < 10; j++ ){
            if (gameField[i][j] != 1){
                return false;
            }
        }
        return true;
    }

    public void checkForDoneLines(){
        int doneLineCount = 0;
        for (int i = 0; i < 20; i++){
            if (lineIsFull(i)){
                doneLineCount++;
            } else {
                getScoreForLines(doneLineCount);
                doneLineCount = 0;
            }
        }
        if (doneLineCount != 0){
            getScoreForLines(doneLineCount);
        }
    }

    public void fillWithEmptyLine(int i){
            gameField[i] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public void dropTheLines(int i, int step){
        for (int j = i; j >= 0; j--){
            gameField[j+step] = gameField[j];
        }
        for (int j = 0; j < step; j++){
            fillWithEmptyLine(j);
        }
    }

    public void deleteLines(){
        int fullLinesCount = 0;
        boolean fullLineStrike = false;
        int strikeBeginning = -1;
        for (int i = 0; i < 20; i ++){
            if (lineIsFull(i)){
                if (i-1 >= 0 && strikeBeginning == -1) {
                    strikeBeginning = i - 1;
                }
                fullLinesCount++;
                fullLineStrike = true;
            } else {
                fullLineStrike = false;
            }
            if (!fullLineStrike && fullLinesCount != 0){
                dropTheLines(strikeBeginning, fullLinesCount);
                fullLinesCount = 0;
                strikeBeginning = -1;
            }
        }
        if (fullLinesCount != 0){
            dropTheLines(strikeBeginning, fullLinesCount);
        }
    }

    public boolean buildFigure(){

        return true;
    }

    public void dropDown(){

    }
}
