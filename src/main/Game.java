package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	public static int WIDTH = 288;
	public static int HEIGHT = 288;
	public static int SCALE = 2;
	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public Board board;
	public static boolean selected = false;
	public static int previousI = 0, previousJ = 0, nextI = -1, nextJ = -1;

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		board = new Board();
	}

	public void tick() {
		if (Game.selected && (Game.nextI != -1 && Game.nextJ != -1)) {
			int iPos1 = Game.previousI / 48;
			int jPos1 = Game.previousJ / 48;
			int iPos2 = Game.nextI / 48;
			int jPos2 = Game.nextJ / 48;

			if ((iPos2 == iPos1 + 1 || iPos2 == iPos1 - 1) && jPos2 == jPos1 || jPos2 == jPos1 + 1
					|| jPos2 == jPos1 - 1) {
				int prevCandy = Board.BOARD[iPos1][jPos1];
				int nextCandy = Board.BOARD[iPos2][jPos2];
				Board.BOARD[iPos1][jPos1] = nextCandy;
				Board.BOARD[iPos2][jPos2] = prevCandy;
				Game.nextI = -1;
				Game.nextJ = -1;
				Game.selected = false;
			} else {
				System.out.println("Can't move there!!");
			}
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = image.getGraphics();

		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		board.render(g);

		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle("Game 10");
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		new Thread(game).start();
	}

	public void run() {
		double fps = 60.0;
		while (true) {
			tick();
			render();
			try {
				Thread.sleep((int) (1000 / fps));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!Game.selected) {
			Game.selected = true;
			Game.previousI = e.getX() / SCALE;
			Game.previousJ = e.getY() / SCALE;
		} else {
			Game.nextI = e.getX() / SCALE;
			Game.nextJ = e.getY() / SCALE;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}