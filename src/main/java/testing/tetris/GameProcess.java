package testing.tetris;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Setter
@Getter
public class GameProcess {

    private int[][] gameField = new int[20][10];
    private int score = 0;
    private FigureType currentFigure;
    private FigureType nextFigureType;

    int[] firstCubeCoord = new int[]{0, 0};
    int[] secondCubeCoord = new int[]{0, 0};
    int[] thirdCubeCoord = new int[]{0, 0};
    int[] fourthCubeCoord = new int[]{0, 0};

    int turnCount = 0;
    boolean figureFreezed;
    boolean gameOver = false;

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
        currentFigure = figureType;
        switch (figureType){
            case FIGURE_TYPE_I:
                if (gameField[0][3] != 1 &&
                        gameField[0][4] != 1 &&
                        gameField[0][5] != 1 &&
                        gameField[0][6] != 1) {
                    gameField[0][3] = 2;
                    gameField[0][4] = 2;
                    gameField[0][5] = 2;
                    gameField[0][6] = 2;
                } else {gameOver = true;}
                break;
            case FIGURE_TYPE_J:
                if (gameField[1][3] != 1 &&
                        gameField[1][4] != 1 &&
                        gameField[1][5] != 1 &&
                        gameField[0][3] != 1) {
                    gameField[1][3] = 2;
                    gameField[1][4] = 2;
                    gameField[1][5] = 2;
                    gameField[0][3] = 2;
                } else {gameOver = true;}
                break;
            case FIGURE_TYPE_L:
                if (gameField[1][3] != 1 &&
                        gameField[1][4] != 1 &&
                        gameField[1][5] != 1 &&
                        gameField[0][5] != 1) {
                    gameField[1][3] = 2;
                    gameField[1][4] = 2;
                    gameField[1][5] = 2;
                    gameField[0][5] = 2;
                } else {gameOver = true;}
                break;
            case FIGURE_TYPE_O:
                if (gameField[1][4] != 1 &&
                        gameField[0][4] != 1 &&
                        gameField[0][5] != 1 &&
                        gameField[1][5] != 1) {
                    gameField[1][4] = 2;
                    gameField[0][4] = 2;
                    gameField[0][5] = 2;
                    gameField[1][5] = 2;
                } else {gameOver = true;}
                break;
            case FIGURE_TYPE_S:
                if (gameField[1][3] != 1 &&
                        gameField[1][4] != 1 &&
                        gameField[0][4] != 1 &&
                        gameField[0][5] != 1) {
                    gameField[1][3] = 2;
                    gameField[1][4] = 2;
                    gameField[0][4] = 2;
                    gameField[0][5] = 2;
                } else {gameOver = true;}
                break;
            case FIGURE_TYPE_T:
                if (gameField[1][3] != 1 &&
                        gameField[1][4] != 1 &&
                        gameField[1][5] != 1 &&
                        gameField[0][4] != 1) {
                    gameField[1][3] = 2;
                    gameField[1][4] = 2;
                    gameField[1][5] = 2;
                    gameField[0][4] = 2;
                } else {gameOver = true;}
                break;
            case FIGURE_TYPE_Z:
                if (gameField[0][3] != 1 &&
                        gameField[0][4] != 1 &&
                        gameField[1][4] != 1 &&
                        gameField[1][5] != 1) {
                    gameField[0][3] = 2;
                    gameField[0][4] = 2;
                    gameField[1][4] = 2;
                    gameField[1][5] = 2;
                } else {gameOver = true;}
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
             figureFreezed = true;
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
                    if (j - 1 >= 0 && gameField[i][j-1] == 1) {
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

    public boolean checkCanMoveRight() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (gameField[i][j] == 2) {
                    if (j + 1 > 9) {
                        return false;
                    }
                    if (j + 1 <= 9 && gameField[i][j+1] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveFigureRight(){
        if (checkCanMoveRight()) {
            for (int i = 19; i >= 0; i--) {
                for (int j = 9; j >= 0; j--) {
                    if (gameField[i][j] == 2) {
                        gameField[i][j + 1] = 2;
                        gameField[i][j] = 0;
                    }
                }
            }
        }
    }

    public void findFigureCoords(){
        int coordCount = 0;
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10; j++){
                if (gameField[i][j] == 2){
                    switch (coordCount){
                        case 0:
                            firstCubeCoord[0] = i;
                            firstCubeCoord[1] = j;
                            coordCount++;
                            break;
                        case 1:
                            secondCubeCoord[0] = i;
                            secondCubeCoord[1] = j;
                            coordCount++;
                            break;
                        case 2:
                            thirdCubeCoord[0] = i;
                            thirdCubeCoord[1] = j;
                            coordCount++;
                            break;
                        case 3:
                            fourthCubeCoord[0] = i;
                            fourthCubeCoord[1] = j;
                            coordCount++;
                            break;
                    }
                }
            }
        }
    }

    public boolean blockMoveTrue(String course, int length, int[] coords){
        switch (course){
            case "top-right":
                if (coords[0]-length < 0){return false;}
                if (coords[1]+length > 9){return false;}
                if (gameField[coords[0]-length][coords[1]+length] != 1){
                    return true;
                }
            case "top-left":
                if (coords[0]-length < 0){return false;}
                if (coords[1]-length < 0){return false;}
                if (gameField[coords[0]-length][coords[1]-length] != 1){
                    return true;
                }
            case "bot-right":
                if (coords[0]+length > 19){return false;}
                if (coords[1]+length > 9){return false;}
                if (gameField[coords[0]+length][coords[1]+length] != 1){
                    return true;
                }
            case "bot-left":
                if (coords[0]+length > 19){return false;}
                if (coords[1]-length < 0){return false;}
                if (gameField[coords[0]+length][coords[1]-length] != 1){
                    return true;
                }
            case "right":
                if (coords[1]+length > 9){return false;}
                if (gameField[coords[0]][coords[1]+length] != 1){
                    return true;
                }
            case "left":
                if (coords[1]-length < 0){return false;}
                if (gameField[coords[0]][coords[1]-length] != 1){
                    return true;
                }
            case "top":
                if (coords[0]-length < 0){return false;}
                if (gameField[coords[0]-length][coords[1]] != 1){
                    return true;
                }
            case "bot":
                if (coords[0]+length > 19){return false;}
                if (gameField[coords[0]+length][coords[1]] != 1){
                    return true;
                }
        }
        return false;
    }

    public void blockMove(String course, int length, int[] coords){
        switch (course){
            case "top-right":
                gameField[coords[0]-length][coords[1]+length] = 2;
                gameField[coords[0]][coords[1]] = 0;
                break;
            case "top-left":
                gameField[coords[0]-length][coords[1]-length] = 2;
                gameField[coords[0]][coords[1]] = 0;
                break;
            case "bot-right":
                gameField[coords[0]+length][coords[1]+length] = 2;
                gameField[coords[0]][coords[1]] = 0;
                break;
            case "bot-left":
                gameField[coords[0]+length][coords[1]-length] = 2;
                gameField[coords[0]][coords[1]] = 0;
                break;
            case "right":
                gameField[coords[0]][coords[1]+length] = 2;
                gameField[coords[0]][coords[1]] = 0;
                break;
            case "left":
                gameField[coords[0]][coords[1]-length] = 2;
                gameField[coords[0]][coords[1]] = 0;
                break;
            case "top":
                gameField[coords[0]-length][coords[1]] = 2;
                gameField[coords[0]][coords[1]] = 0;
                break;
            case "bot":
                gameField[coords[0]+length][coords[1]] = 2;
                gameField[coords[0]][coords[1]] = 0;
                break;
        }
    }



    public void turnFigure(){
        findFigureCoords();
        switch (currentFigure){
            case FIGURE_TYPE_I:
                if (turnCount == 0){
                    if (blockMoveTrue("bot-left", 1, secondCubeCoord) &&
                            blockMoveTrue("bot-left", 2, thirdCubeCoord) &&
                            blockMoveTrue("bot-left", 3, fourthCubeCoord)){
                        blockMove("bot-left", 1, secondCubeCoord);
                        blockMove("bot-left", 2, thirdCubeCoord);
                        blockMove("bot-left", 3, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if(turnCount == 1){
                    if (blockMoveTrue("top-left", 1, secondCubeCoord) &&
                            blockMoveTrue("top-left", 2, thirdCubeCoord) &&
                            blockMoveTrue("top-left", 3, fourthCubeCoord)){
                        blockMove("top-left", 1, secondCubeCoord);
                        blockMove("top-left", 2, thirdCubeCoord);
                        blockMove("top-left", 3, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if(turnCount == 2){
                    if (blockMoveTrue("top-right", 1, thirdCubeCoord) &&
                            blockMoveTrue("top-right", 2, secondCubeCoord) &&
                            blockMoveTrue("top-right", 3, firstCubeCoord)){
                        blockMove("top-right", 1, thirdCubeCoord);
                        blockMove("top-right", 2, secondCubeCoord);
                        blockMove("top-right", 3, firstCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if (turnCount == 3){
                    if (blockMoveTrue("bot-right", 3, firstCubeCoord) &&
                            blockMoveTrue("bot-right", 2, secondCubeCoord) &&
                            blockMoveTrue("bot-right", 1, thirdCubeCoord)){
                        blockMove("bot-right", 3, firstCubeCoord);
                        blockMove("bot-right", 2, secondCubeCoord);
                        blockMove("bot-right", 1, thirdCubeCoord);
                    } else {
                        turnCount--;
                    }
                }
            break;
            case FIGURE_TYPE_J:
                if (turnCount == 0){
                    if (blockMoveTrue("right", 1, firstCubeCoord) &&
                            blockMoveTrue("top", 1, secondCubeCoord) &&
                            blockMoveTrue("left", 2, fourthCubeCoord) &&
                            blockMoveTrue("bot-left", 1, thirdCubeCoord)){
                        blockMove("right", 1, firstCubeCoord);
                        blockMove("top", 1, secondCubeCoord);
                        blockMove("left", 2, fourthCubeCoord);
                        blockMove("bot-left", 1, thirdCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if(turnCount == 1){
                    if (blockMoveTrue("right", 2, firstCubeCoord) &&
                            blockMoveTrue("bot-right", 1, secondCubeCoord) &&
                            blockMoveTrue("top-right", 1, thirdCubeCoord) &&
                            blockMoveTrue("top", 2, fourthCubeCoord)){
                        blockMove("right", 2, firstCubeCoord);
                        blockMove("bot-right", 1, secondCubeCoord);
                        blockMove("top-right", 1, thirdCubeCoord);
                        blockMove("top", 2, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if(turnCount == 2){
                    if (blockMoveTrue("bot-right", 2, firstCubeCoord) &&
                            blockMoveTrue("bot-right", 1, secondCubeCoord) &&
                            blockMoveTrue("bot-left", 1, fourthCubeCoord)){
                        blockMove("bot-right", 2, firstCubeCoord);
                        blockMove("bot-left", 1, fourthCubeCoord);
                        blockMove("bot-right", 1, secondCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if (turnCount == 3){
                    if (blockMoveTrue("left", 1, firstCubeCoord) &&
                            blockMoveTrue("top-left", 1, thirdCubeCoord) &&
                            blockMoveTrue("top-left", 2, fourthCubeCoord)){
                        blockMove("bot-left", 1, firstCubeCoord);
                        blockMove("top-left", 1, thirdCubeCoord);
                        blockMove("top-left", 2, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                }
                break;
            case FIGURE_TYPE_L:
                if (turnCount == 0){
                    if (blockMoveTrue("bot", 2, firstCubeCoord) &&
                            blockMoveTrue("top-right", 1, secondCubeCoord) &&
                            blockMoveTrue("bot-left", 1, fourthCubeCoord)){
                        blockMove("bot", 2, firstCubeCoord);
                        blockMove("top-right", 1, secondCubeCoord);
                        blockMove("bot-left", 1, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if(turnCount == 1){
                    if (blockMoveTrue("top-right", 1, secondCubeCoord) &&
                            blockMoveTrue("top-left", 1, thirdCubeCoord) &&
                            blockMoveTrue("top-left", 2, fourthCubeCoord)){
                        blockMove("top-right", 1, secondCubeCoord);
                        blockMove("top-left", 1, thirdCubeCoord);
                        blockMove("top-left", 2, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if(turnCount == 2){
                    if (blockMoveTrue("bot-right", 2, firstCubeCoord) &&
                            blockMoveTrue("bot-right", 1, secondCubeCoord) &&
                            blockMoveTrue("top-right", 1, fourthCubeCoord)){
                        blockMove("bot-right", 2, firstCubeCoord);
                        blockMove("bot-right", 1, secondCubeCoord);
                        blockMove("top-right", 1, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if (turnCount == 3){
                    if (blockMoveTrue("bot-left", 1, firstCubeCoord) &&
                            blockMoveTrue("top-left", 1, fourthCubeCoord)){
                        blockMove("bot-left", 1, firstCubeCoord);
                        blockMove("top-left", 1, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                }
                break;
            case FIGURE_TYPE_S:
                if (turnCount == 0 || turnCount == 2){
                    if (blockMoveTrue("left", 1, firstCubeCoord) &&
                            blockMoveTrue("bot-left", 1, secondCubeCoord) &&
                            blockMoveTrue("bot", 1, fourthCubeCoord)){
                        blockMove("left", 1, firstCubeCoord);
                        blockMove("bot", 1, fourthCubeCoord);
                        blockMove("bot-left", 1, secondCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if(turnCount == 1 || turnCount == 3) {
                    if (blockMoveTrue("right", 2, firstCubeCoord) &&
                            blockMoveTrue("top-right", 1, secondCubeCoord) &&
                            blockMoveTrue("top-left", 1, fourthCubeCoord)) {
                        blockMove("right", 2, firstCubeCoord);
                        blockMove("top-right", 1, secondCubeCoord);
                        blockMove("top-left", 1, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                }
                break;
            case FIGURE_TYPE_Z:
                if (turnCount == 0 || turnCount == 2){
                    if (blockMoveTrue("bot", 1, firstCubeCoord) &&
                            blockMoveTrue("bot-left", 1, thirdCubeCoord) &&
                            blockMoveTrue("left", 1, fourthCubeCoord)){
                        blockMove("bot", 1, firstCubeCoord);
                        blockMove("bot-left", 1, thirdCubeCoord);
                        blockMove("left", 1, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if(turnCount == 1 || turnCount == 3) {
                    if (blockMoveTrue("right", 2, secondCubeCoord) &&
                            blockMoveTrue("top", 2, fourthCubeCoord)) {
                        blockMove("right", 2, secondCubeCoord);
                        blockMove("top", 2, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                }
                break;
            case FIGURE_TYPE_T:
                if (turnCount == 0){
                    if (blockMoveTrue("bot-right", 1, secondCubeCoord)){
                        blockMove("bot-right", 1, secondCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if(turnCount == 1){
                    if (blockMoveTrue("top", 1, thirdCubeCoord) &&
                            blockMoveTrue("top-left", 1, secondCubeCoord) &&
                            blockMoveTrue("top", 1, fourthCubeCoord)){
                        blockMove("top", 1, thirdCubeCoord);
                        blockMove("top-left", 1, secondCubeCoord);
                        blockMove("top", 1, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if(turnCount == 2){
                    if (blockMoveTrue("bot", 1, firstCubeCoord) &&
                            blockMoveTrue("bot", 1, fourthCubeCoord) &&
                            blockMoveTrue("bot-left", 1, thirdCubeCoord)){
                        blockMove("bot", 1, firstCubeCoord);
                        blockMove("bot", 1, fourthCubeCoord);
                        blockMove("bot-left", 1, thirdCubeCoord);
                    } else {
                        turnCount--;
                    }
                } else if (turnCount == 3){
                    if (blockMoveTrue("top-right", 1, fourthCubeCoord)){
                        blockMove("top-right", 1, fourthCubeCoord);
                    } else {
                        turnCount--;
                    }
                }
                break;
        }
        if (turnCount == 3){
            turnCount = 0;
        } else {
            turnCount++;
        }
    }
}
