package br.com.pincubics.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import javax.swing.JFrame;

public class Main extends Canvas implements Runnable, KeyListener {
    //serial version of canvas
    private static final long serialVersionUID = 1L;
    //make the window
    public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 4;

	protected static String gameState = "INTRO";
	
	private BufferedImage image;

	public static Menu menu = new Menu();
	private keys_type keys = new keys_type();
	private Tick tick = new Tick();
	private Render render = new Render();

    public Main() {
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		initFrame();

    }

    public void initFrame() {

		addKeyListener(this);

		frame = new JFrame("Hezildo The Game");
		frame.add(this);
		
		frame.setResizable(false);
		frame.pack();
		
		//Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		frame.setAlwaysOnTop(true);
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

    public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    public static void main(String[] args) {
		Main game = new Main();
		game.start();
	}

    public void tick() {
		tick.tick();
    }

    public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.dispose();
		g = bs.getDrawGraphics();
		
        render.render(g);

		bs.show();
    }

	@Override
	public void run() {
		requestFocus(); //focar altomaticamente na janela do jogo quando iniciar
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render(); 
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys.keyReleased(e);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		keys.keyTyped(e);
		
	}

}
