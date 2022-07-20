//tentativa falha de criar um render fora da main
package com.pincubics.main;

import java.awt.image.BufferStrategy;
import com.pincubics.world.World;
import java.awt.*;
import java.awt.Graphics;


public class Render extends Canvas {
	
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
		Graphics2D g2 = (Graphics2D) g;
		World.render(g);
		
		
		if(Game_Main.getGameState() == "MENU") {
			Menu.render(g);
		}
		
	}
}
