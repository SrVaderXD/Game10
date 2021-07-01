package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static int WIDTH = 288;
	public static int HEIGHT = 288;
	public static int SCALE = 2;
	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public Board board;

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		board = new Board();
	}

	public void tick() {

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
}