package br.com.pincubics.main;

//import java.awt.image.BufferStrategy;
//import java.awt.image.BufferedImage;
//import java.awt.image.*;
import java.awt.Graphics;

public class Render {
    
    public void render(Graphics g) {
		/*renderizacao do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		//World.render(g);

		if(Main.gameState == "INTRO") {
			//System.out.println("entrando no jogo");

			//g.drawImage(Entities.INTRO, 0, 0, WIDTH, HEIGHT, null);
		}
		if(Main.gameState == "comecar") {
			//g.setColor(Color.RED);
			//g.drawString("O jogo vai comecar!", 150, 250);
		}
		if(Main.gameState == "MENU") {
			Main.menu.render(g);
		}
		/*the games states of normal game and levels*/
		if(Main.gameState == "NORMAL") {
			//Main_Level.render(g);
		}
		if(Main.gameState == "Level1") {
			//Level_0.render(g);
		}
		
		//bs.show();
    }

}
