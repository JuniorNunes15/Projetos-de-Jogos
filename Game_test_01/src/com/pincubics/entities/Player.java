package com.pincubics.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
//import java.util.ArrayList;

//import com.pincubics.graficos.Spritesheet;
import com.pincubics.main.Game;
import com.pincubics.world.Camera;
import com.pincubics.world.World;

public class Player extends Entity{
	
	public boolean right, left, up, down;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public double speed = 1.6;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	private BufferedImage playerDamageLeft;
	private BufferedImage playerDamageRight;
	
	private boolean hasGun = false;
	
	public int ammo = 0; //minha municao
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public boolean shoot = false, mouseShoot = false;
	
	public double life = 100, maxLife = 100;
	public int mx, my; //posicao do mause no ponto x e y
	
	//criar um fake jump
	public boolean jump = false;
	public boolean isJumping = false;
	public int z = 0;
	//jumpFrames sera a altura que ira chegar
	public int jumpFrames = 30, jumpCur = 0;
	public int jumpSpd = 2;
	public boolean jumpUp = false, jumpDown = false;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDamageLeft = Game.spritesheet.getSprite(16, 16, 16, 16);
		playerDamageRight = Game.spritesheet.getSprite(0, 16, 16, 16);
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
		}
		
	}
	
	public void tick() {
		
		if(jump) {
			if(isJumping == false) {
				jump = false;
				isJumping = true;
				jumpUp = true;
			}
		}
		if(isJumping) {
			//if(jumpCur < jumpFrames) {
			if(jumpUp) {
				jumpCur += jumpSpd;
			}
			else if(jumpDown) {
				jumpCur -= jumpSpd;
				if(jumpCur <= 0) {
					isJumping = false;
					jumpDown = false;
					jumpUp = false;
				}
			}
			z = jumpCur;
			if(jumpCur >= jumpFrames) {
				jumpUp = false;
				jumpDown = true;
			}	
			//}
		}
		
		depth = 1;
		
		moved = false;
		if(right && World.isFree((int)(x+speed), this.getY(), z)) {
			moved = true;
			x += speed;
			dir = right_dir;
		}
		else if(left && World.isFree((int)(x-speed), this.getY(), z)) {
			moved = true;
			x -= speed;
			dir = left_dir;
		}
		if(up && World.isFree(this.getX(), (int)(y-speed), z)) {
			moved = true;
			y -= speed;
		}
		else if(down && World.isFree(this.getX(), (int)(y+speed), z)) {
			moved = true;
			y += speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionGun();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 10) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(shoot) { //atirar
			shoot = false;
			if(hasGun && ammo > 0) {
				ammo--;
				shoot = false;
				int dx = 0;
				int px = 0;
				int py = 4;
				if(dir == right_dir) {
					px = 22;
					dx = 1;
				}
				else {
					px = -10;
					dx = -1;
				}
				
				BulletShot bullet = new BulletShot(this.getX() + px, this.getY() + py, 3, 3, null, dx, 0);
				Game.bullets.add(bullet);
			}
		}
		
		if(mouseShoot) {
			mouseShoot = false;
			if(hasGun && ammo > 0) {
				ammo--;
				//mouseShoot = false;
				
				int px = 0;
				int py = 6;
				double angle = 0;
				if(dir == right_dir) {
					px = 24;
					angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
				}
				else {
					px = -12;
					angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
				}
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				
				BulletShot bullet = new BulletShot(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
				Game.bullets.add(bullet);
			}
		}
		
		if(life == 0) { //Game over
			/*
			Game.entity = new ArrayList<Entity>();
			Game.enemyes = new ArrayList<Enemy>();
			Game.spritesheet = new Spritesheet("/spritsheet.png");
			Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
			Game.entity.add(Game.player);
			Game.world = new World("/map.png");
			return;
			*/
			life = 0;
			Game.gameState = "GAMEOVER";
		}
		
		updateCamera();
	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}
	
	public void checkCollisionGun() {
		for(int i = 0; i < Game.entity.size(); i++) {
			Entity atual = Game.entity.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColidding(this, atual)) {
					hasGun = true;
					Game.entity.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionAmmo() {
		for(int i = 0; i < Game.entity.size(); i++) {
			Entity atual = Game.entity.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)) {
					if(ammo < 999) {
						ammo += 9;
						if(ammo > 999) {
							ammo = 999;
						}
						Game.entity.remove(atual);
					}
				}
			}
		}
	}
	
	public void checkCollisionLifePack() {
		for(int i = 0; i < Game.entity.size(); i++) {
			Entity atual = Game.entity.get(i);
			if(atual instanceof LifePack) {
				if(Entity.isColidding(this, atual)) {
					if(life < 100) {
						life += 10;
						if(life > 100) {
							life = 100;
						}
						Game.entity.remove(atual);
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(moved == true) { //esta se movendo
				if(dir == right_dir) {
					g.setColor(Color.GRAY);
					g.fillOval(this.getX() - Camera.x + 4, this.getY() - Camera.y + 12, 8, 8); //sombra
					g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					if(hasGun) { // desenhar a arma para o lado direita
						g.drawImage(Entity.GUN_RIGHT, this.getX() - Camera.x + 12, this.getY() - Camera.y - z, null);
					}
				}
				else if(dir == left_dir) {
					g.setColor(Color.GRAY);
					g.fillOval(this.getX() - Camera.x + 3, this.getY() - Camera.y + 12, 8, 8); //sombra
					g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					if(hasGun) { // desenhar arma para esquerda
						g.drawImage(Entity.GUN_LEFT, this.getX() - Camera.x - 12, this.getY() - Camera.y - z, null);
					}
				}
			}
			else { //esta parado
				if(dir == right_dir) {
					g.setColor(Color.GRAY);
					g.fillOval(this.getX() - Camera.x + 4, this.getY() - Camera.y + 12, 8, 8); //sombra
					g.drawImage(rightPlayer[0], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					if(hasGun) { // desenhar a arma para o lado direita
						g.drawImage(Entity.GUN_RIGHT, this.getX() - Camera.x + 12, this.getY() - Camera.y - z, null);
					}
				}
				else if(dir == left_dir) {
					g.setColor(Color.GRAY);
					g.fillOval(this.getX() - Camera.x + 3, this.getY() - Camera.y + 12, 8, 8); //sombra
					g.drawImage(leftPlayer[3], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					if(hasGun) { // desenhar arma para esquerda
						g.drawImage(Entity.GUN_LEFT, this.getX() - Camera.x - 12, this.getY() - Camera.y - z, null);
					}
				}
			}
		}
		else {
			if(dir == right_dir) {
				g.drawImage(playerDamageLeft, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if(hasGun) {
					g.drawImage(Entity.GUN_RIGHT_FEEDBACK, this.getX() - Camera.x + 12, this.getY() - Camera.y - z, null);
				}
			}
			else if(dir == left_dir) {
				g.drawImage(playerDamageRight, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
				if(hasGun) {
					g.drawImage(Entity.GUN_LEFT_FEEDBACK, this.getX() - Camera.x - 12, this.getY() - Camera.y - z, null);
				}
			}
			
		}
		if(isJumping) {
			g.setColor(Color.GRAY);
			g.fillOval(this.getX() - Camera.x + 4, this.getY() - Camera.y + 12, 8, 8);
		}
	}
}
