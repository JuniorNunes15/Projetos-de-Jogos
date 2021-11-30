package com.pincubics.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.pincubics.main.Game;

public class Npc extends Entity {
	
	public String[] frases = new String[5];
	
	public boolean showMessage = false;
	public boolean show = false;
	public int contMessage = -1;
	public int curIndex = 0;
	public int time = 0; //controla o tempo de aparecer a mensagem
	public int maxTime = 10;

	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		frases[0] = "Seja bem vindo ao jogo!";
		frases[1] = "Derrote todos os inimigos para passar\n pra proxima fase!";
		frases[2] = "Pegue a arma no chão e junte todas \n as balas, para derrotar os inimigos!";
		frases[3] = "v";
		frases[4] = "NomeGenerico";
	}
	
	public void tick() {
		depth = 1;
		/*if(contMessage > 2) {
			contMessage = 0;
		}*/
		if(calculateDistance(Game.player.getX(), Game.player.getY(), (int)x, (int)y) < 20) {
			if(show == true) {
				showMessage = true;
			}
			else if(show == false){
				showMessage = false;
				contMessage = -1;
			}
		}
		else {
			showMessage = false;
			show = false;
			contMessage = -1;
		}
		if(showMessage) { //fazer a frase ter animacao
			time++;
			if(time >= maxTime) {
				time = 0;
				if(curIndex < frases[contMessage].length()) {
					curIndex++;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		g.setFont(new Font("Arial", Font.BOLD, 9));
		if(calculateDistance(Game.player.getX(), Game.player.getY(), (int)x, (int)y) < 20) {
			g.setColor(Color.ORANGE);
			g.drawString(" G ", (int)x, (int)y);
		}
		if(showMessage) {
			g.setColor(Color.black);
			g.drawImage(PERSONAGEM_FEIO, 0, 0, null);
			g.drawImage(DIALOG_TEXT, 8, 105, null);
			g.drawString(frases[contMessage].substring(0, curIndex), 20, 135);
			g.setFont(new Font("Arial", Font.BOLD, 9));
			g.drawString(frases[4], 20, 118);
			g.drawString(frases[3], 220, 150);
			g.fillOval(217, 143, 8, 8);
		}
	}

}
