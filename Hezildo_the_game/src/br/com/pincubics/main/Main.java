package br.com.pincubics.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {
    //serial version of canvas
    private static final long serialVersionUID = 1L;
    //make the window
    public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;

	private static String gameState = "INTRO";
	private static int time = 0;
	private static int maxTime = 60*3; //levando 3 segundos para entrar no jogo
	
	private BufferedImage image;

    public Main() {
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		initFrame();

    }

    public void initFrame() {
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
		if(gameState == "INTRO") {
			System.out.println("entrando");
			time++;
			//render();
			if(time >= maxTime) {
				gameState = "MENU";
			}
		}
		else if(gameState == "NORMAL") {
			//Main_Level.tick();
		}
		else if(gameState == "FINAL") {
			System.out.println("look this tick");
		}
		else if(gameState == "MENU") {
			//Menu.tick();
		}
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		/*renderizacao do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		//World.render(g);

		g.dispose();
		g = bs.getDrawGraphics();
		if(gameState == "INTRO") {
			System.out.println("entrando no jogo");

			//g.drawImage(Entities.INTRO, 0, 0, WIDTH, HEIGHT, null);
		}
		if(gameState == "comecar") {
			//g.setColor(Color.RED);
			//g.drawString("O jogo vai comecar!", 150, 250);
		}
		if(gameState == "MENU") {
			//Menu.render(g);
		}
		/*the games states of normal game and levels*/
		if(gameState == "NORMAL") {
			//Main_Level.render(g);
		}
		if(gameState == "Level1") {
			//Level_0.render(g);
		}
		
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

}
