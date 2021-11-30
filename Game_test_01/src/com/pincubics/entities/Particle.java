package com.pincubics.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.pincubics.main.Game;
import com.pincubics.world.Camera;

public class Particle extends Entity { //sistema de particulas para o jogo
	
	public int LifePaticleTime = 15; //tempo dde vida das particulas
	public int curLifeParticle = 0;
	public int speed = 4;
	
	public double dx = 0;
	public double dy = 0;
	
	public Particle(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
	}
	
	public void tick() {
		x += dx * speed;
		y += dy * speed;
		
		curLifeParticle++;
		if(curLifeParticle == LifePaticleTime) {
			Game.entity.remove(this);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
	
}
