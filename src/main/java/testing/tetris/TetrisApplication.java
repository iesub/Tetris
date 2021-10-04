package testing.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class TetrisApplication extends JFrame {

	static GameProcess gameProcess = new GameProcess();

	static int gameSpeed = 500;

	static int buildFigures = 0;

	static class KeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			if (e.getKeyCode() == KeyEvent.VK_SPACE){
				gameProcess.turnFigure();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT){
				gameProcess.moveFigureLeft();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT){
				gameProcess.moveFigureRight();
			}
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		Date date = new Date();
		Random random = new Random(date.getTime());
		gameProcess.initField();
		gameProcess.buildFigure(FigureType.values()[random.nextInt(7 - 1)]);
		buildFigures++;
		gameProcess.setNextFigureType(FigureType.values()[random.nextInt(7 - 1)]);

		JFrame frame = new JFrame();
		frame.setSize(800, 1000);

		JLabel score = new JLabel("<html><font color = 'white'>Счет: " + gameProcess.getScore() + "</font></html>", JLabel.CENTER);
		JLabel nextFigure = new JLabel("", JLabel.CENTER);
		JLabel gameField = new JLabel("", JLabel.CENTER);

		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);
		topPanel.add(score, BorderLayout.WEST);
		topPanel.add(nextFigure, BorderLayout.EAST);

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(gameField, BorderLayout.CENTER);
		frame.addKeyListener(new KeyListener());

		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);

		while (true) {
			score.setText("<html><font color = 'white'>Счет: " + gameProcess.getScore() + "</font></html>");
			switch (gameProcess.getNextFigureType()) {
				case FIGURE_TYPE_I:
					nextFigure.setText("<html><pre><font color = 'white'>########<br></font>" +
							"<font color = 'white'>########</pre><br><br></font></html>");
					break;
				case FIGURE_TYPE_J:
					nextFigure.setText("<html><pre><font color = 'white'>##<br></font>" +
							"<font color = 'white'>##<br></font>" +
							"<font color = 'white'>######<br></font>" +
							"<font color = 'white'>######</pre></font></html>");
					break;
				case FIGURE_TYPE_L:
					nextFigure.setText("<html><pre><font color = 'white'>    ##<br></font>" +
							"<font color = 'white'>    ##<br></font>" +
							"<font color = 'white'>######<br></font>" +
							"<font color = 'white'>######</pre></font></html>");
					break;
				case FIGURE_TYPE_O:
					nextFigure.setText("<html><font color = 'white'>####<br></font>" +
							"<font color = 'white'>####<br></font>" +
							"<font color = 'white'>####<br></font>" +
							"<font color = 'white'>####</pre></font></html>");
				case FIGURE_TYPE_S:
					nextFigure.setText("<html><pre><font color = 'white'>  ####<br></font>" +
							"<font color = 'white'>  ####<br></font>" +
							"<font color = 'white'>####<br></font>" +
							"<font color = 'white'>####</pre></font></html>");
					break;
				case FIGURE_TYPE_T:
					nextFigure.setText("<html><pre><font color = 'white'>  ##<br></font>" +
							"<font color = 'white'>  ##<br></font>" +
							"<font color = 'white'>######<br></font>" +
							"<font color = 'white'>######</pre></font></html>");
					break;
				case FIGURE_TYPE_Z:
					nextFigure.setText("<pre><font color = 'white'>####<br></font>" +
							"<font color = 'white'>####<br></font>" +
							"<font color = 'white'>  ####<br></font>" +
							"<font color = 'white'>  ####</pre></font></html>");
					break;
			}
			StringBuffer gameFieldText = new StringBuffer();
			gameFieldText.append("<html><font color = 'white'>+-----------------------------------+<br></font><pre>");
			for (int i = 0; i < 20; i++) {
				for (int k = 0; k < 2; k++) {
					gameFieldText.append("<font color = 'white'>|</font>");
					for (int j = 0; j < 10; j++) {
						if (gameProcess.getGameField()[i][j] == 1) {
							gameFieldText.append("<font color = 'red'>##</font></label>");
						} else if (gameProcess.getGameField()[i][j] == 2) {
							gameFieldText.append("<font color = 'green'>##</font></label>");
						} else {
							gameFieldText.append("  ");
						}
					}
					gameFieldText.append("<font color = 'white'>|<br></font>");
				}
			}
			gameFieldText.append("</pre><font color = 'white'><label>+-----------------------------------+<label></font></html>");
			gameField.setText(String.valueOf(gameFieldText));
			if (gameProcess.checkCanMoveDown()) {
				gameProcess.moveDownFigure();
			} else {
				gameProcess.freezeFigure();
				gameProcess.setFigureFreezed(true);
			}
			if (gameProcess.figureFreezed) {
				gameProcess.setFigureFreezed(false);
				gameProcess.setTurnCount(0);
				gameProcess.buildFigure(gameProcess.getNextFigureType());
				buildFigures++;
				gameProcess.setNextFigureType(FigureType.values()[random.nextInt(7 - 1)]);
				if (gameProcess.gameOver) {
					break;
				}
			}
			gameProcess.checkForDoneLines();
			gameProcess.deleteLines();
			if (buildFigures == 2){
				buildFigures = 0;
				gameSpeed -= 2;
			}
			Thread.sleep(gameSpeed);
		}
		gameField.setText("<html><pre><font color = 'red'>  ________                                                  <br>" +
				" /  _____/_____    _____   ____     _______  __ ___________ <br>" +
				"/   \\  ___\\__  \\  /     \\_/ __ \\   /  _ \\  \\/ // __ \\_  __ \\<br>" +
				"\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/  (  <_> )   /\\  ___/|  | \\/<br>" +
				" \\______  (____  /__|_|  /\\___  >  \\____/ \\_/  \\___  >__|   <br>" +
				"        \\/     \\/      \\/     \\/                   \\/       </font></pre></html>");
		nextFigure.setText("");
	}
}
