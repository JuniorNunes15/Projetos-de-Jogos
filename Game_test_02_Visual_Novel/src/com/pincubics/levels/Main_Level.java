package com.pincubics.levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//import java.io.*;

import com.pincubics.entities.Entities;
import com.pincubics.main.Game_Main;

public class Main_Level {
	
	private static boolean pressToContinue = false;
	private static int actualLevel = 0;
	public static String text;
	
	public static int time = 0;
	public static int maxTime = 10;
	public static int curIndex = 1;
	
	private static int talkLine = 0;
	
	public static boolean showMessage = false;
	
	private static String[] Levels = {"Prologo", "capitulo 1", "capitulo 2"};
	
	/*try {
		FileReader file = new FileReader(text);
	}
	catch(IOException e){
		
	}*/
	
	public static void tick() {
		
		if(actualLevel == 0) {
			
		}
		if(pressToContinue) {
			talkLine++;
			time = 0;
			curIndex = 0;
			pressToContinue = false;
			showMessage = true;
		}
		/*if(actualLevel == 0) {
			Level_0.InLevel = true;
			//Level_0.tick();
			//Game_Main.setGameState("Level1");
		}*/
		
		if(showMessage) { //fazer a frase ter animacao
			//curIndex = 0;
			time++;
			if(time == maxTime) {
				time = 0;
				if(curIndex < text.length()) {
					curIndex++;
				}
			}
		}
		
	}
	
	public static void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game_Main.WIDTH * Game_Main.SCALE, Game_Main.HEIGHT * Game_Main.SCALE);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.BLUE);
		
		if(actualLevel == 0) {
			if(talkLine < Level_0.talks.length){
				shownSprites(g, "1-11");
				talkBalon(g, Level_0.talks[talkLine]);
			}
			else {
				actualLevel++;
				talkLine = 0;
			}
		}
		//talkBalon(g, Levels[actualLevel]);
		if(actualLevel == 1) {
			if(talkLine < Level_1.talks.length){
				shownSprites(g, "1+11");
				talkBalon(g, Level_1.talks[talkLine]);
			}
			else {
				actualLevel++;
				talkLine = 0;
			}
		}
		if(actualLevel == 2) {
			if(talkLine < 1) {
				shownSprites(g, "0/11");
				g.setColor(Color.RED);
				g.drawString("DIARIO DE UM AVENTUREIRO", 300, 300);
			}
			else {
				actualLevel++;
			}
		}
		if(actualLevel == 3) {
			actualLevel = 0;
			Game_Main.setGameState("MENU");
		}
		g.setColor(Color.BLACK);
		g.drawString(Levels[actualLevel], 40, 90);
	}
	
	public static void talkBalon(Graphics g, String talk) {
		setText(talk);
		g.setColor(Color.WHITE);
		g.fillRect(20, 450, 920, 170);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString(talk, 40, 490);
	}
	
	public static void shownSprites(Graphics g, String sprites) { //sprite possi quatro numeros "xxxx" 
		//a primeira posição é referente ao cenario
		int cenario = Character.getNumericValue(sprites.charAt(0));
		g.drawImage(Entities.CENARIOS[cenario] , 0, 0, Game_Main.WIDTH, Game_Main.HEIGHT, null);
		
		//a segunda posição é relacionado a posição do sprite
		if(sprites.charAt(1) == '-') { //esquerda
			g.drawImage(Entities.PERSONAGEM, 10, 10, Game_Main.WIDTH -300, Game_Main.HEIGHT, null);
		}
		if(sprites.charAt(1) == '+') { //direita
			g.drawImage(Entities.PERSONAGEM2 , 300, 10, Game_Main.WIDTH -300, Game_Main.HEIGHT, null);
		}
		if(sprites.charAt(1) == '/') { //centro
			g.drawImage(Entities.PERSONAGEM2 , 100, 10, Game_Main.WIDTH -300, Game_Main.HEIGHT, null);
		}
		
	}

	public static boolean isPressToContinue() {
		return pressToContinue;
	}

	public static void setPressToContinue(boolean pressToContinue) {
		Main_Level.pressToContinue = pressToContinue;
	}

	public static String getText() {
		return text;
	}

	public static void setText(String text) {
		Main_Level.text = text;
	}
	
	

}
