package com.pincubics.levels;

import java.awt.Graphics;



public class Level_0 extends Main_Level{
	
	public static boolean InLevel = false;
	//private static int talkLine = 0;
	
	//public static String[] talks = new String[5];
	//talks = "";
	public static String[] talks = {"Já era noite!",
									"O único som que se dava pra escultar era o do vento e a da minha pesada respiração!",
									"Não tinha mais forças para me movimentar,",
									"a única coisa que podia fazer, era esperar pela minha morte!",
									};
	
	public static void tick() {
		/*if(InLevel) {
			if(Main_Level.isPressToContinue()) {
				talkLine++;
				time = 0;
				curIndex = 0;
				Main_Level.setPressToContinue(false);
			}
		}*/
		
	}
	
	public static void render(Graphics g) {
		//showMessage = true;
		//talkBalon(g, talks2[talkLine]);
	}

}
