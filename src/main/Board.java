package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Board {
	public static final int WIDTH = 6, HEIGHT = 6;
	public static int[][] BOARD;
	public static int GRID_SIZE = 40;
	public static BufferedImage spritesheet;
	public static int CANDY_0 = 0, CANDY_1 = 1, CANDY_2 = 2;
	public BufferedImage CANDY_0_SPRITE = getSprite(1297, 190, 120, 117);
	public BufferedImage CANDY_1_SPRITE = getSprite(972, 280, 115, 117);
	public BufferedImage CANDY_2_SPRITE = getSprite(972, 420, 134, 136);
	private int score = 0;

	public Board() {
		BOARD = new int[WIDTH][HEIGHT];

		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				BOARD[i][j] = new Random().nextInt(3);
			}
		}
	}

	public static BufferedImage getSprite(int x, int y, int width, int height) {
		if (spritesheet == null) {
			try {
				spritesheet = ImageIO.read(Board.class.getResource("/spritesheet.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return spritesheet.getSubimage(x, y, width, height);
	}

	public void tick() {
		// HORIZONTAL CHECKS
		ArrayList<Candy> combos = new ArrayList<Candy>();

		for (int j = 0; j < HEIGHT; j++) {
			if (combos.size() == 3) {
				for (int k = 0; k < combos.size(); k++) {
					int xtemp = combos.get(k).x;
					int ytemp = combos.get(k).y;
					BOARD[xtemp][ytemp] = new Random().nextInt(3);
				}
				combos.clear();
				System.out.println("SCORE!!");
				score += 100;
				return;
			}
			combos.clear();

			for (int i = 0; i < WIDTH; i++) {
				int color = BOARD[i][j];

				if (combos.size() == 3) {
					for (int k = 0; k < combos.size(); k++) {
						int xtemp = combos.get(k).x;
						int ytemp = combos.get(k).y;
						BOARD[xtemp][ytemp] = new Random().nextInt(3);
					}
					combos.clear();
					System.out.println("SCORE!!");
					score += 100;
					return;
				}
				if (combos.size() == 0) {
					combos.add(new Candy(i, j, color));
				} else if (combos.size() > 0) {
					if (combos.get(combos.size() - 1).CANDY_TYPE == color) {
						combos.add(new Candy(i, j, color));
					} else {
						combos.clear();
						combos.add(new Candy(i, j, color));
					}
				}
			}
		}

		// VERTICAL CHECKS
		combos = new ArrayList<Candy>();

		for (int i = 0; i < WIDTH; i++) {
			if (combos.size() == 3) {
				for (int k = 0; k < combos.size(); k++) {
					int xtemp = combos.get(k).x;
					int ytemp = combos.get(k).y;
					BOARD[xtemp][ytemp] = new Random().nextInt(3);
				}
				combos.clear();
				System.out.println("SCORE!!");
				score += 100;
				return;
			}
			combos.clear();

			for (int j = 0; j < HEIGHT; j++) {
				int color = BOARD[i][j];

				if (combos.size() == 3) {
					for (int k = 0; k < combos.size(); k++) {
						int xtemp = combos.get(k).x;
						int ytemp = combos.get(k).y;
						BOARD[xtemp][ytemp] = new Random().nextInt(3);
					}
					combos.clear();
					System.out.println("SCORE!!");
					score += 100;
					return;
				}
				if (combos.size() == 0) {
					combos.add(new Candy(i, j, color));
				} else if (combos.size() > 0) {
					if (combos.get(combos.size() - 1).CANDY_TYPE == color) {
						combos.add(new Candy(i, j, color));
					} else {
						combos.clear();
						combos.add(new Candy(i, j, color));
					}
				}
			}
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				g.setColor(Color.white);
				g.drawRect(i * GRID_SIZE + 24, j * GRID_SIZE + 24, GRID_SIZE, GRID_SIZE);
				g.setColor(Color.white);
				g.drawString("SCORE: " + score, 5, 15);

				int candy = BOARD[i][j];

				if (candy == CANDY_0) {
//					g.setColor(Color.red);
//					g.fillRect(i * GRID_SIZE + 12 + 24, j * GRID_SIZE + 12 + 24, 25, 25);
					g.drawImage(CANDY_0_SPRITE, i * GRID_SIZE + 12 + 24, j * GRID_SIZE + 12 + 24, 25, 25, null);
				}
				if (candy == CANDY_1) {
//					g.setColor(Color.green);
//					g.fillRect(i * GRID_SIZE + 12 + 24, j * GRID_SIZE + 12 + 24, 25, 25);
					g.drawImage(CANDY_1_SPRITE, i * GRID_SIZE + 12 + 24, j * GRID_SIZE + 12 + 24, 25, 25, null);
				}
				if (candy == CANDY_2) {
//					g.setColor(Color.blue);
//					g.fillRect(i * GRID_SIZE + 12 + 24, j * GRID_SIZE + 12 + 24, 25, 25);
					g.drawImage(CANDY_2_SPRITE, i * GRID_SIZE + 12 + 24, j * GRID_SIZE + 12 + 24, 25, 25, null);
				}
				if (Game.selected) {
					int iPos = Game.previousI / GRID_SIZE;
					int jPos = Game.previousJ / GRID_SIZE;
					g.setColor(Color.red);
					g.drawRect(iPos * GRID_SIZE + 24, jPos * GRID_SIZE + 24, GRID_SIZE, GRID_SIZE);
				}
			}
		}
	}

}
