package com.pincubics.main;

import java.awt.image.BufferStrategy;

import com.pincubics.world.World;

import java.awt.*;
import java.awt.Graphics;


public class Render extends Canvas {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Game_Main game;
    private World world;
    private Menu menu;
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = game.getImage().getGraphics();
		//g.setColor(new Color(0, 0, 0));
		//g.fillRect(0, 0, game.getWidth(), game.getHeight());
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
		/*g.dispose();
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
		/*
		if(estadoCena == comecar) {
			g.setColor(Color.RED);
			g.drawString("O jogo vai comecar!", 150, 250);
		}
		*/
		if(Game_Main.getGameState() == "MENU") {
			Menu.render(g);
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
		//bs.show();
	}


}
