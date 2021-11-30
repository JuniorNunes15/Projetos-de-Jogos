package com.pincubics.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

//import com.pincubics.entities.Player;
import com.pincubics.main.Game;

public class UI {

	
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(4, 8, 54, 14);
		g.setColor(Color.GREEN);
		g.fillRect(6, 10, (int)((Game.player.life / Game.player.maxLife) * 50), 10);
		if(Game.player.life == Game.player.maxLife) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("arial", Font.BOLD, 10));
			g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, 10, 19);
		}
		else if(Game.player.life < Game.player.maxLife && Game.player.life > 9){
			g.setColor(Color.BLACK);
			g.setFont(new Font("arial", Font.BOLD, 10));
			g.drawString("0" + (int)Game.player.life + "/" + (int)Game.player.maxLife, 10, 19);
		}
		else {
			g.setColor(Color.BLACK);
			g.setFont(new Font("arial", Font.BOLD, 10));
			g.drawString("00" + (int)Game.player.life + "/" + (int)Game.player.maxLife, 10, 19);
		}
		g.setColor(Color.ORANGE);
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.drawString("Enemyes Left: " + (int)Game.enemyes.size(), 2, 155);
		g.drawString("Ammo: " + Game.player.ammo, 186, 19);
	}
	
}
