package com.pincubics.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import com.pincubics.entities.Entities;
import com.pincubics.graphics.Spritesheet;
import com.pincubics.levels.Level_0;
import com.pincubics.levels.Main_Level;
import com.pincubics.world.*;


public class Game_Main extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L; //serial version of Canvas
    // criando a tela e o seu tamanho
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 960; //240*4;
	public static final int HEIGHT = 640; //160*4;
	public static final int SCALE = 1;
	
	public static Menu menu;
	public KeysTypeKeyboard keys;
	
	private static String gameState = "INTRO";
	
	private BufferedImage image;
	
	public static int currentSize = 0;
	public static boolean cz = false;
	
	public static Spritesheet introSprite;
	public static Spritesheet personagem, personagem2;
	public static Spritesheet cenario1, cenario2;
	
	private static int time = 0;
	private static int maxTime = 60*3; //levando 3 segundos para entrar no jogo
	
	
	public Game_Main() {
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		if(currentSize == 0) {
			setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		}
		else {
			setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize())); //colocar a janela em fullscream
		}
		
		introSprite = new Spritesheet("/intro2.png");
		personagem = new Spritesheet("/sora.png");
		personagem2 = new Spritesheet("/sora2.png");
		cenario1 = new Spritesheet("/scenary1.jpg");
		cenario2 = new Spritesheet("/scenary2.jpg");
		
		initFrame();
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	public void initFrame() {
		frame = new JFrame("Game 1");
		frame.add(this);
		if(currentSize == 1) { //colocar em tela cheia*/
			setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize())); 
			frame.setUndecorated(true);
		}
		else {
			setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
			frame.setUndecorated(false);
		}
		frame.setResizable(false);
		frame.pack();
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
	
	public static void main(String[] args) { //principal function who beginning the code
		Game_Main game = new Game_Main(); //creating a new game
		game.start(); //starting the game
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
			Main_Level.tick();
		}
		else if(gameState == "FINAL") {
			System.out.println("look this tick");
		}
		else if(gameState == "MENU") {
			Menu.tick();
		}
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = getImage().getGraphics();
		/*renderizacao do jogo*/
		Graphics2D g2 = (Graphics2D) g;
		World.render(g);

		g.dispose();
		g = bs.getDrawGraphics();
		if(gameState == "INTRO") {
			System.out.println("entrando no jogo");
			g.drawImage(Entities.INTRO, 0, 0, WIDTH, HEIGHT, null);
		}
		if(gameState == "comecar") {
			g.setColor(Color.RED);
			g.drawString("O jogo vai comecar!", 150, 250);
		}
		if(gameState == "MENU") {
			Menu.render(g);
		}
		/*the games states of normal game and levels*/
		if(gameState == "NORMAL") {
			Main_Level.render(g);
		}
		if(gameState == "Level1") {
			Level_0.render(g);
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
				render(); //o render está dando problema, terminarei as funções bases primeiro, depois concerto isso
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

	//get and setters
	public static String getGameState() {
		return gameState;
	}
	public static void setGameState(String gameState) {
		Game_Main.gameState = gameState;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public int getWidth() {
		return WIDTH;
	}
	public int getHeight() {
		return HEIGHT;
	}
	public static int getSCALE() {
		return SCALE;
	}
	public int getCurrentSize() {
		return currentSize;
	}
	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if(Game_Main.getGameState() == "MENU") {
				Menu.setUp(true);;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if(Game_Main.getGameState() == "MENU") {
				Menu.setDown(true);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(Game_Main.getGameState() == "MENU") {
				Menu.setEnter(true);
			}
			if(Game_Main.getGameState() == "NORMAL") {
				Main_Level.setPressToContinue(true);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}	

}
