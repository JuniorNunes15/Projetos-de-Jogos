package com.pincubics.levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.pincubics.main.Game_Main;

public class Main_Level {
	
	public static void tick() {
		
	}
	
	public static void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game_Main.WIDTH * Game_Main.SCALE, Game_Main.HEIGHT * Game_Main.SCALE);
		talkBalon(g, "ha ha, isso é estranho.");
		talkBalon(g, "por que estou nesse planeta?");
	}
	
	public static void talkBalon(Graphics g, String talk) {
		g.setColor(Color.WHITE);
		g.fillRect(20, 450, 920, 170);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString(talk, 40, 490);
	}

}
