package testing.tetris;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameProcess {

    private int[][] gameField = new int[20][10];
    private int score = 0;
    private FigureType currentFigure;
    private FigureType nextFigureType;

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

    public boolean buildFigure(FigureType figureType){
        switch (figureType){
            case FIGURE_TYPE_I:
                if (gameField[0][3] != 1) {
                    gameField[0][3] = 2;
                }
                if (gameField[0][4] != 1) {
                    gameField[0][4] = 2;
                }
                if (gameField[0][5] != 1) {
                    gameField[0][5] = 2;
                }
                if (gameField[0][6] != 1) {
                    gameField[0][6] = 2;
                }
                break;
            case FIGURE_TYPE_J:
                if (gameField[1][3] != 1) {
                    gameField[1][3] = 2;
                }
                if (gameField[1][4] != 1) {
                    gameField[1][4] = 2;
                }
                if (gameField[1][5] != 1) {
                    gameField[1][5] = 2;
                }
                if (gameField[0][3] != 1) {
                    gameField[0][3] = 2;
                }
                break;
            case FIGURE_TYPE_L:
                if (gameField[1][3] != 1) {
                    gameField[1][3] = 2;
                }
                if (gameField[1][4] != 1) {
                    gameField[1][4] = 2;
                }
                if (gameField[1][5] != 1) {
                    gameField[1][5] = 2;
                }
                if (gameField[0][5] != 1) {
                    gameField[0][5] = 2;
                }
                break;
            case FIGURE_TYPE_O:
                if (gameField[1][4] != 1) {
                    gameField[1][4] = 2;
                }
                if (gameField[0][4] != 1) {
                    gameField[0][4] = 2;
                }
                if (gameField[0][5] != 1) {
                    gameField[0][5] = 2;
                }
                if (gameField[1][5] != 1) {
                    gameField[1][5] = 2;
                }
                break;
            case FIGURE_TYPE_S:
                if (gameField[1][3] != 1) {
                    gameField[1][3] = 2;
                }
                if (gameField[1][4] != 1) {
                    gameField[1][4] = 2;
                }
                if (gameField[0][4] != 1) {
                    gameField[0][4] = 2;
                }
                if (gameField[0][5] != 1) {
                    gameField[0][5] = 2;
                }
                break;
            case FIGURE_TYPE_T:
                if (gameField[1][3] != 1) {
                    gameField[1][3] = 2;
                }
                if (gameField[1][4] != 1) {
                    gameField[1][4] = 2;
                }
                if (gameField[1][5] != 1) {
                    gameField[1][5] = 2;
                }
                if (gameField[0][4] != 1) {
                    gameField[0][4] = 2;
                }
                break;
        }
        return true;
    }

    public void freezeFigure(){
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10; j++){
                if (gameField[i][j]==2){
                    gameField[i][j]=1;
                }
            }
        }
    }

    public boolean checkCanMoveDown() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameField[i][j] == 2) {
                    if (i + 1 == 20) {
                        return false;
                    }
                    if (i + 1 != 20 && gameField[i + 1][j] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveDownFigure(){
        for (int i = 19; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (gameField[i][j] == 2) {
                    gameField[i+1][j] = 2;
                    gameField[i][j] = 0;
                }
            }
        }
    }

    public void fallingProcess() {
        while (true) {
            if (checkCanMoveDown()){
                moveDownFigure();
            } else {
             freezeFigure();
             break;
            }
        }
    }

    public boolean checkCanMoveLeft() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameField[i][j] == 2) {
                    if (j - 1 < 0) {
                        return false;
                    }
                    if (j - 1 < 0 && gameField[i][j-1] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveFigureLeft(){
        if (checkCanMoveLeft()) {
            for (int i = 19; i >= 0; i--) {
                for (int j = 0; j < 10; j++) {
                    if (gameField[i][j] == 2) {
                        gameField[i][j - 1] = 2;
                        gameField[i][j] = 0;
                    }
                }
            }
        }
    }

    public void moveFigureRight(){

    }
}
