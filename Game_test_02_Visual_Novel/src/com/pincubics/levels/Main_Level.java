package com.pincubics.levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.*;

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
	
	try {
		FileReader file = new FileReader(text);
	}
	catch(IOException e){
		
	}
	
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
		if(actualLevel == 0) {
			if(talkLine < Level_0.talks2.length){
				talkBalon(g, Level_0.talks2[talkLine]);
			}
			else {
				actualLevel++;
			}
		}
		
		//if(Level_0.InLevel) {
		//	Level_0.render(g);
		//}
		//talkBalon(g, "ha ha, isso é estranho.");
		//talkBalon(g, "por que estou nesse planeta?");
	}
	
	public static void talkBalon(Graphics g, String talk) {
		//System.out.println("dentro do balão de fala");
		//text = talk;
		setText(talk);
		g.setColor(Color.WHITE);
		g.fillRect(20, 450, 920, 170);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString(talk, 40, 490);
		//System.out.println(curIndex);
		g.drawString(text.substring(0, curIndex), 40, 510);
		//showMessage = false;
		System.out.println(text);
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
