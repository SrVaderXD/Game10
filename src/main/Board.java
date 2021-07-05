package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Board {
	public static final int WIDTH = 6, HEIGHT = 6;
	public static int[][] BOARD;
	public static int CANDY_0 = 0, CANDY_1 = 1, CANDY_2 = 2;

	public Board() {
		BOARD = new int[WIDTH][HEIGHT];

		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				BOARD[i][j] = new Random().nextInt(3);
			}
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				g.setColor(Color.white);
				g.drawRect(i * 48, j * 48, 48, 48);

				int candy = BOARD[i][j];

				if (candy == CANDY_0) {
					g.setColor(Color.red);
					g.fillRect(i * 48 + 12, j * 48 + 12, 25, 25);
				}
				if (candy == CANDY_1) {
					g.setColor(Color.green);
					g.fillRect(i * 48 + 12, j * 48 + 12, 25, 25);
				}
				if (candy == CANDY_2) {
					g.setColor(Color.blue);
					g.fillRect(i * 48 + 12, j * 48 + 12, 25, 25);
				}
				if (Game.selected) {
					int iPos = Game.previousI / 48;
					int jPos = Game.previousJ / 48;
					g.setColor(Color.black);
					g.drawRect(iPos * 48, jPos * 48, 48, 48);
				}
			}
		}
	}

}
