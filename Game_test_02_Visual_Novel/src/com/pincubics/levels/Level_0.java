package com.pincubics.levels;

import java.awt.Graphics;



public class Level_0 extends Main_Level{
	
	public static boolean InLevel = false;
	//private static int talkLine = 0;
	
	//public static String[] talks = new String[5];
	//talks = "";
	public static String[] talks2 = {"essa não",
									"oque foi?",
									"a bola que estava na estante sumiu",
									"estranho, tinha visto ela hoje de manhã"};
	
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
