package com.pincubics.entities;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.pincubics.main.Game;
import com.pincubics.main.Sound;
import com.pincubics.world.AStar;
//import com.pincubics.main.Sound;
import com.pincubics.world.Camera;
import com.pincubics.world.Vector2i;
import com.pincubics.world.World;

public class Enemy extends Entity {
	
	private double speed = 0.6;
	private int maskx, masky, maskw, maskh;
	private int frames = 0, maxFrames = 15, index = 0, maxIndex = 1;
	private BufferedImage[] sprites;
	private int life = 10;
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(128, 16, 16, 16);
	}
	
	public void tick() {
		depth = 0;
		
		maskx = 4;
		masky = 4;
		maskw = 9; //mascara do inimigo em w
		maskh = 12; //mascara do inimigo em h
		/*primeiro modo para colisoes de inimmigos
		if(Game.rand.nextInt(100) < 50) {
			if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())) {
				x += speed;
			}
			else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())) {
				x -= speed;
			}
			else if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))) {
				y += speed;
			}
			else if((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))) {
				y -= speed;
			}
		}
		*/
		
		/* modo de colidir sem AStar */
		/*
		//o inimigo ira atras do jogador quando a disancia entre eles for menor que 70
		if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 70) {
			if(isColiddingWithPlayer() == false) {
				if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY(), 0) && !isColidding((int)(x+speed), this.getY())) {
					x += speed;
				}
				else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY(), 0) && !isColidding((int)(x-speed), this.getY())) {
					x -= speed;
				}
				if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed), 0) && !isColidding(this.getX(), (int)(y+speed))) {
					y += speed;
				}
				else if((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed), 0) && !isColidding(this.getX(), (int)(y-speed))) {
					y -= speed;
				}
			}
			else { //inimigo colidindo
				if(Game.rand.nextInt(100) < 10) {
					//Sound.hurtEffect.play();
					Game.player.life--;
					Game.player.isDamaged = true;
					System.out.println("life: " + Game.player.life);
				}
			}
		}
		//acao que  inimigo pode fazer quando o jogador nap estiver perto 
		else {
			
		}
		*/
		
		/* Metodo de colisao com o AStar */
		if(!isColiddingWithPlayer()) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i((int)(x/16), (int)(y/16));
				Vector2i end = new Vector2i((int)(Game.player.x/16), (int)(Game.player.y/16));
				path = AStar.findPath(Game.world, start, end);
			}
		}
		else {
			if(new Random().nextInt(100) < 5 ) {
				//Sound.hurtPlayer.play();
				//Sound.hurtEffect.play();
				Game.player.life -= Game.rand.nextInt(3);
				Game.player.isDamaged = true;
			}
		}
		//followPath(path);
		/**/
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		
		collidingBullet();
		
		if(life <= 0) {
			World.GenerateParticles(100, (int)x, (int)y);
			destroySelf();
			return;
		}
		
		if(isDamaged) {
			damageCurrent++;
			if(damageCurrent == damageFrames) {
				damageCurrent = 0;
				isDamaged = false;
			}
		}
	}
	
	public void destroySelf() {
		Game.enemyes.remove(this);
		Game.entity.remove(this);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShot) {
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					life--;
					Game.bullets.remove(i);
					return;
				}
			}
		}
		
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		
		return enemyCurrent.intersects(player);
	}
	
	
	
	public void render(Graphics g) {
		if(!isDamaged) {
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else {
			g.drawImage(Entity.ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}

}
