package com.pincubics.main;

//import java.awt.Canvas;
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
import com.pincubics.levels.Main_Level;
import com.pincubics.world.*;




public class Game_Main extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L; //serial version of Canvas

	private static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 960; //240*4;
	public static final int HEIGHT = 640; //160*4;
	public static final int SCALE = 1;
	
	//private static Tick tick;
	//private Render render;
	public static Menu menu;
	public KeysTypeKeyboard keys;
	
	private static String gameState = "INTRO";
	
	private BufferedImage image;
	
	public static int currentSize = 0;
	
	public static Spritesheet introSprite;
	
	private static int time = 0;
	private static int maxTime = 60*3; //levando 3 segundos para entrar no jogo
	
	
	public Game_Main() {
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//if(currentSize == 0) {
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		/*}
		else {
			setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize())); //colocar a janela em fullscream
		}*/
		//
		
		introSprite = new Spritesheet("/intro2.png");
		
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	
	
	public void initFrame() {
		frame = new JFrame("Game 1");
		frame.add(this);
		/*if(currentSize == 1) { //colocar em tela cheia*/
			//frame.setUndecorated(true);
		/*}
		else {
			frame.setUndecorated(false);
		}*/
		frame.setResizable(false);
		frame.pack();
		/* trocando o currsor do mouse e da icone do jogo */
		//Image imagem = null;
		
		//Toolkit toolkit = Toolkit.getDefaultToolkit();
		//Image image = toolkit.getImage(getClass().getResource("/icon.png")); //importando imagen para cursor do mouse
		//Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
		//frame.setCursor(c); //implementando cusor do mouse
		//frame.setIconImage(imagem); //implementando icone do jogo
		frame.setAlwaysOnTop(true);
		/**/
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
			System.out.println("in normal state");
			//gameState = "MENU";
		}
		else if(gameState == "FINAL") {
			System.out.println("look this tick");
		}
		else if(gameState == "MENU") {
			//player.updateCamera();
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
		//g.setColor(new Color(0, 0, 0));
		//g.fillRect(0, 0, Game_Main.WIDTH, Game_Main.HEIGHT);
		/*renderizacao do jogo*/
		Graphics2D g2 = (Graphics2D) g;
		World.render(g);
		
		//Collections.sort(entity, Entity.nodeSorter);
		
		/*for(int i = 0; i < entity.size(); i++) {
			Entity e = entity.get(i);
			e.render(g);
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}*/
		
		//applyLight(); //adicionando o sistema de luz
		
		//ui.render(g);
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		//g.drawImage(Entities.INTRO, 0, 0, 20, 20, null);
		//drawRectangleExample(xx, yy); //utilizando o exemplo de manipular imagem
		
		/*if(currentSize == 1) { //colocar em tela cheia
			setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
			//if(currentSize == 1) { //colocar em tela cheia
				//frame.setUndecorated(true);
			//}
			//else {
				//frame.setUndecorated(false);
			//}
			g.drawImage(image, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null); //jogo em tela cheia
			//g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		}*/
		
		//g.setColor(Color.ORANGE);
		//g.setFont(new Font("arial", Font.BOLD, 20));
		//g.drawString("Ammo: " + player.ammo, 600, 55);
		//g.setFont(newfonte); 
		//g.drawString("AAAAAAAAAAA fon!!!", 100, 100); //nova fonte inportada
		/*
		if(gameState == "GAMEOVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g2.setColor(Color.ORANGE);
			g2.setFont(new Font("arial", Font.BOLD, 20));
			g2.drawString("GAME OVER",  (WIDTH * SCALE) / 2 - 40, (HEIGHT * SCALE) / 2);
			if(showMessageameOver) {
				g2.drawString("[Pressione espa�o para continuar]",  (WIDTH * SCALE) / 2 - 130, (HEIGHT * SCALE) / 2 + 50);
			}
		}
		else if(gameState == "FINAL") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g2.setColor(Color.ORANGE);
			g2.setFont(new Font("arial", Font.BOLD, 20));
			g2.drawString("CONGRATULATIONS!!",  (WIDTH * SCALE) / 2 - 80, (HEIGHT * SCALE) / 2);
			if(showMessageFinal) {
				g2.drawString("[Pressione espa�o para voltar ao menu]",  (WIDTH * SCALE) / 2 - 170, (HEIGHT * SCALE) / 2 + 50);
			}
		}
		*/
		if(gameState == "INTRO") {
			System.out.println("entrando no jogo");
			g.drawImage(Entities.INTRO, 0, 0, WIDTH, HEIGHT, null);
		}
		
		if(gameState == "comecar") {
			g.setColor(Color.RED);
			g.drawString("O jogo vai comecar!", 150, 250);
		}
		
		if(gameState == "MENU") {
			//System.out.println("MENUUUUU");
			Menu.render(g);
		}
		
		if(gameState == "NORMAL") {
			Main_Level.render(g);
		}
		
		//fazer rotacao de objetos com o mouse
		/*
		Graphics2D g2 = (Graphics2D) g;
		double angleMouse = Math.atan2(200+25 - my,200+25 - mx); //retorna o valor em radianos
		g2.rotate(angleMouse, 200+25, 200+25);//+25 para ficar no centro do objeto
		//System.out.println("valor em graus: " + Math.toDegrees(angleMouse)); //pegando a posicao em graus do mause
		g.setColor(Color.red);
		g.fillRect(200, 200, 50, 50);
		*/
		/*World.renderMiniMap();
		g.drawImage(miniMap, 470, 310, Game.WIDTH, Game.HEIGHT, null);
		*/
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
			//System.out.println("WWWWW");
			//player.up = true;
			if(Game_Main.getGameState() == "MENU") {
				Menu.setUp(true);;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			//System.out.println("WWWWW");
			//player.up = true;
			if(Game_Main.getGameState() == "MENU") {
				Menu.setDown(true);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			//System.out.println("WWWWW");
			//player.up = true;
			if(Game_Main.getGameState() == "MENU") {
				Menu.setEnter(true);
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
