package testing.tetris;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class TetrisApplication {

	static GameProcess gameProcess = new GameProcess();

	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";

	public static void main(String[] args) throws IOException, InterruptedException {

		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		Date date = new Date();
		Random random = new Random(date.getTime());
		gameProcess.initField();
		gameProcess.buildFigure(FigureType.values()[random.nextInt(7-1)]);
		while (true) {
			System.out.println("+--------------------+");
			for (int i = 0; i < 20; i++) {
				for (int k = 0; k < 2; k++) {
					System.out.print("|");
					for (int j = 0; j < 10; j++) {
						if (gameProcess.getGameField()[i][j] == 1) {
							System.out.print(ANSI_RED + "##" + ANSI_RESET);
						} else if (gameProcess.getGameField()[i][j] == 2) {
							System.out.print(ANSI_GREEN + "##" + ANSI_RESET);
						} else {
							System.out.print("  ");
						}
					}
					System.out.println("|");
				}
			}
			System.out.println("+--------------------+");
			if (gameProcess.checkCanMoveDown()){
				gameProcess.moveDownFigure();
			} else {
				gameProcess.freezeFigure();
				gameProcess.setFigureFreezed(true);
			}
			if (gameProcess.figureFreezed){
				gameProcess.setFigureFreezed(false);
				gameProcess.buildFigure(FigureType.values()[random.nextInt(7-1)]);
				if (gameProcess.gameOver){
					break;
				}
			}
			Thread.sleep(500);
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		System.out.println("  ________                                                  \n" +
				" /  _____/_____    _____   ____     _______  __ ___________ \n" +
				"/   \\  ___\\__  \\  /     \\_/ __ \\   /  _ \\  \\/ // __ \\_  __ \\\n" +
				"\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/  (  <_> )   /\\  ___/|  | \\/\n" +
				" \\______  (____  /__|_|  /\\___  >  \\____/ \\_/  \\___  >__|   \n" +
				"        \\/     \\/      \\/     \\/                   \\/       ");
	}
}
