package com.pincubics.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
//import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
//import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import com.pincubics.entities.BulletShot;
import com.pincubics.entities.Enemy;
import com.pincubics.entities.Entity;
import com.pincubics.entities.Npc;
import com.pincubics.entities.Player;
import com.pincubics.graficos.*;
//import com.pincubics.world.Camera;
//import com.pincubics.graficos.UI;
import com.pincubics.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	private int CUR_LEVEL = 1, MAX_LEVEL = 3;
	private BufferedImage image;
	
	public static List<Entity> entity;
	public static List<Enemy> enemyes;
	public static List<BulletShot> bullets;
	public static Spritesheet spritesheet;
	public static Spritesheet spriteText;
	public static Spritesheet spriteFeio; //sprite apenas para teste com o npc aparecendo a imagem dele
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public UI ui;
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelart.ttf");
	public Font newfonte;
	
	public static String gameState = "MENU";
	private boolean showMessageameOver = true;
	private int framesameOver = 0;
	private boolean restartGame = false;
	
	private boolean showMessageFinal = true;
	private int framesamesFinal = 0;
	
	public Menu menu;
	public Sound sound;
	
	public boolean saveGame = false;
	
	public int[] pixels; //array de pixels para manipular imagens
	
	//sistema de luz dinamica
	public BufferedImage lightmap; //colocar uma luz em volta do mapa
	public int[] lightMapPixels;
	
	public int mx, my;
	
	//public int xx, yy; do metodo de mapulacao de objetos
	
	/* criar minimapa */
	public static BufferedImage miniMap;
	public static int[] miniMapPixels;
	
	/* sistema de cutscene */
	public static int entrada = 1;
	public static int comecar = 2;
	public static int jogando = 3;
	public static int estadoCena = entrada;
	
	public int timeCena = 0, masxTimeCena = 60*3; //3 segundos
	
	/**/
	
	public Npc npc;
	
	public Game() {
		//Sound.musicBackground.loop();
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		//setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize())); //colocar a janela em fullscream
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		//iniciando objetos//
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		try {
			lightmap = ImageIO.read(getClass().getResource("/light.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0, lightmap.getWidth());
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entity = new ArrayList<Entity>();
		enemyes = new ArrayList<Enemy>();
		bullets =  new ArrayList<BulletShot>();
		spritesheet = new Spritesheet("/spritsheet.png");
		spriteText = new Spritesheet("/caixaDeDialogo.png");
		spriteFeio = new Spritesheet("/personagenGenerico.png");
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
		entity.add(player);
		world = new World("/level1.png");
		//miniMap = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		//miniMapPixels = ((DataBufferInt)miniMap.getRaster().getDataBuffer()).getData();
		
		npc = new Npc(32, 32, 16, 16, spritesheet.getSprite(0, 64, 16, 16));
		entity.add(npc);
		
		menu = new Menu();
		
		try {
			newfonte = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(70f); //  f e do tamanho da fonte
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initFrame() {
		frame = new JFrame("Game 1");
		frame.add(this);
		//frame.setUndecorated(true); //para a janela cheia
		frame.setResizable(false);
		frame.pack();
		/* trocando o currsor do mouse e da icone do jogo */
		Image imagem = null;
		try {
			imagem = ImageIO.read(getClass().getResource("/icone.png")); //importando imagen para icone do jogo
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("/icon.png")); //importando imagen para cursor do mouse
		Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
		frame.setCursor(c); //implementando cusor do mouse
		frame.setIconImage(imagem); //implementando icone do jogo
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
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		if(gameState == "NORMAL") {
			//xx++; do metodo de manipulacao de objetos
			if(this.saveGame) {
				this.saveGame = false;
				String[] opt1 = {"level"};
				int[] opt2 = {this.CUR_LEVEL};
				Menu.saveGame(opt1, opt2, 10);
				System.out.println("JOgo salvo");
			}
			restartGame = false;
			
			if(estadoCena == jogando) {
				for(int i = 0; i < entity.size(); i++) {
					Entity e = entity.get(i);
					e.tick();
				}
				for(int i = 0; i < bullets.size(); i++) {
					bullets.get(i).tick();
				}
			}
			else if(estadoCena == entrada) {
				if(player.getY() > 100) {
					player.setY(player.getY()-1);
				}
				else {
					estadoCena = comecar;
				}
			}
			else if(estadoCena == comecar) {
				timeCena++;
				if(timeCena == masxTimeCena) {
					estadoCena = jogando;
				}
			}
			
			/*verificar se todos os inimigos foram detruidos para ir ao proximo nivel*/
			if(enemyes.size() == 0) {
				CUR_LEVEL++;
				if(CUR_LEVEL > MAX_LEVEL) {
					CUR_LEVEL = 1;
					gameState = "FINAL";
				}
				String newWorld = "level" + CUR_LEVEL + ".png";
				World.restartGame(newWorld);
			}
		}
		else if(gameState == "FINAL") {
			framesamesFinal++;
			if(framesamesFinal == 30) {
				framesamesFinal = 0;
				if(showMessageFinal) {
					showMessageFinal = false;
				}
				else {
					showMessageFinal = true;
				}
			}
			if(restartGame) {
				restartGame = false;
				gameState = "MENU";
				//CUR_LEVEL = 1;
				//String newWorld = "level" + CUR_LEVEL + ".png";
				//World.restartGame(newWorld);
			}
		}
		else if(gameState == "GAMEOVER") {
			framesameOver++;
			if(framesameOver == 30) {
				framesameOver = 0;
				if(showMessageameOver) {
					showMessageameOver = false;
				}
				else {
					showMessageameOver = true;
				}
			}
			if(restartGame) {
				restartGame = false;
				gameState = "NORMAL";
				CUR_LEVEL = 1;
				String newWorld = "level" + CUR_LEVEL + ".png";
				World.restartGame(newWorld);
			}
		}
		else if(gameState == "MENU") {
			player.updateCamera();
			menu.tick();
		}
		
	}
	
	/*
	public void drawRectangleExample(int xoff, int yoff) { //exemplo de manipulacao de pixels
		for(int xx = 0; xx < 32; xx++) {
			for(int yy = 0; yy < 32; yy++) { //uma area de 32x32 pixels
				int xOff = xx + xoff;
				int yOff = yy + yoff;
				if(xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT) { //se retirar esse if, o quadrado vai rodar em loop pela tela
					continue;
				}
				pixels[xOff + (yOff * WIDTH)] = 0xff0000; //cor vermelha
			}
		}
	}
	*/
	
	//sistema de luz dinamica
	public void applyLight() {
		for(int xx = 0; xx < Game.WIDTH; xx++) {
			for(int yy = 0; yy < Game.HEIGHT; yy++) {
				if(lightMapPixels[xx + (yy * Game.WIDTH)] == 0xffffffff) { //se a posicao atual do meu light map for branca, a cor fica preta no jogo
					pixels[xx + (yy * Game.WIDTH)] = 0x000000; //a cor ficando preta na tela
				}
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		/*renderizacao do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		
		Collections.sort(entity, Entity.nodeSorter);
		
		for(int i = 0; i < entity.size(); i++) {
			Entity e = entity.get(i);
			e.render(g);
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		
		//applyLight(); //adicionando o sistema de luz
		
		ui.render(g);
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		//drawRectangleExample(xx, yy); //utilizando o exemplo de manipular imagem
		
		//g.drawImage(image, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null); //jogo em tela cheia
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		//g.setColor(Color.ORANGE);
		//g.setFont(new Font("arial", Font.BOLD, 20));
		//g.drawString("Ammo: " + player.ammo, 600, 55);
		g.setFont(newfonte); 
		//g.drawString("AAAAAAAAAAA fon!!!", 100, 100); //nova fonte inportada
		
		if(gameState == "GAMEOVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g2.setColor(Color.ORANGE);
			g2.setFont(new Font("arial", Font.BOLD, 20));
			g2.drawString("GAME OVER",  (WIDTH * SCALE) / 2 - 40, (HEIGHT * SCALE) / 2);
			if(showMessageameOver) {
				g2.drawString("[Pressione espaço para continuar]",  (WIDTH * SCALE) / 2 - 130, (HEIGHT * SCALE) / 2 + 50);
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
				g2.drawString("[Pressione espaço para voltar ao menu]",  (WIDTH * SCALE) / 2 - 170, (HEIGHT * SCALE) / 2 + 50);
			}
		}
		
		if(estadoCena == comecar) {
			g.setColor(Color.RED);
			g.drawString("O jogo vai comecar!", 150, 250);
		}
		
		else if(gameState == "MENU") {
			menu.render(g);
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
		World.renderMiniMap();
		g.drawImage(miniMap, 470, 310, Game.WIDTH, Game.HEIGHT, null);
		
		bs.show();
	}
	
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			//System.out.println("DDDDD");
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			//System.out.println("AAAAA");
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			//System.out.println("WWWWW");
			player.up = true;
			if(gameState == "MENU") {
				menu.up = true;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			//System.out.println("SSSSS");
			player.down = true;
			if(gameState == "MENU") {
				menu.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_G) { //pressionar para falar com os npcs
			//npc.showMessage = false;
			if(npc.contMessage < 2) {
				npc.show = true;
				npc.contMessage++;
				npc.curIndex = 0;
				
			}
			else if(npc.contMessage == 2) {
				//boolean showBack = !npc.show; //recebe o contrario do atual estado do show do npc
				npc.show = false;//showBack;
				//npc.contMessage = 0;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			player.shoot = true;
			if(gameState == "MENU") {
				menu.enter = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(!restartGame) {//apertar space depois de morrer para reiniciar o jogo morrer 
				restartGame = true;
			}
			if(gameState == "NORMAL") {//fake jump
				player.jump = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "MENU";
			menu.pause = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F) { //salvar o jogo de maneira generica
			this.saveGame = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot = true;
		player.mx = (e.getX() / 3);
		player.my = (e.getY() / 3);
		//System.out.println(player.mx);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mx = e.getX();
		this.my = e.getY();
	}
	
}
