package testing.tetris;

import java.io.IOException;

public class TetrisApplication {

	static GameProcess gameProcess = new GameProcess();

	public static void main(String[] args) throws IOException, InterruptedException {

		for (int i = 0; i<10; i++){
			System.out.println("\raaaaaaaaaaaaa" + i);
			System.out.flush();
			Thread.sleep(1000);
		}
	}

}
