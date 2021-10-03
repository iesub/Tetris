package testing.tetris;

import java.io.IOException;

public class TetrisApplication {

	static GameProcess gameProcess = new GameProcess();

	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";

	public static void main(String[] args) throws IOException, InterruptedException {

		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		gameProcess.initField();
		gameProcess.buildFigure(FigureType.FIGURE_TYPE_L);
		while (true) {
			System.out.println("+--------------------+");
			for (int i = 0; i < 20; i++) {
				for (int k = 0; k < 2; k++) {
					System.out.print("|");
					for (int j = 0; j < 10; j++) {
						if (gameProcess.getGameField()[i][j] == 1) {
							System.out.print(ANSI_RED + "##" + ANSI_RESET);
						} else if (gameProcess.getGameField()[i][j] == 2) {
							System.out.print(ANSI_RED + "##" + ANSI_RESET);
						} else {
							System.out.print("  ");
						}
					}
					System.out.println("|");
				}
			}
			System.out.println("+--------------------+");
			gameProcess.turnFigure();
			if (gameProcess.checkCanMoveDown()){
				gameProcess.moveDownFigure();
			}
			Thread.sleep(1000);
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}
	}
}
