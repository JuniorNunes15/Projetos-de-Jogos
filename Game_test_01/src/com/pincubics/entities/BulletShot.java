package com.pincubics.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.pincubics.main.Game;
import com.pincubics.world.Camera;
import com.pincubics.world.World;

public class BulletShot extends Entity{

	private double dx; //direcoes
	private double dy;
	private int speed = 4;
	private int life = 70, curLife = 0;
	
	public BulletShot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		
		if(World.isFreeDynamic((int)(x + (dx * speed)), (int)(y + (dy * speed)), 3, 3)) {
			x += dx * speed;
			y += dy * speed;
		}
		else {
			Game.bullets.remove(this);
			World.GenerateParticles(50, (int)x, (int)y);
			return;
		}
		
		curLife++;
		if(curLife == life) {
			Game.bullets.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
	
}
