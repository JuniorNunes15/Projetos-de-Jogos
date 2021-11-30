package com.pincubics.entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.pincubics.main.Game;
import com.pincubics.world.Camera;
import com.pincubics.world.Node;
import com.pincubics.world.Vector2i;

public class Entity {

	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(96, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(112, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(96, 16, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(112, 16, 16, 16);
	public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(144, 16, 16, 16);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(128, 0, 16, 16);
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(144, 0, 16, 16);
	public static BufferedImage GUN_RIGHT_FEEDBACK = Game.spritesheet.getSprite(128, 32, 16, 16);
	public static BufferedImage GUN_LEFT_FEEDBACK = Game.spritesheet.getSprite(144, 32, 16, 16);
	
	public static BufferedImage DIALOG_TEXT = Game.spriteText.getSprite(8, 106, 224, 51);
	
	public static BufferedImage PERSONAGEM_FEIO = Game.spriteFeio.getSprite(0, 0, 163, 125);
	
	protected double x;
	protected double y;
	protected int z;
	protected int width;
	protected int height;
	
	public int depth;
	
	protected List<Node> path;
	
	private BufferedImage sprite;
	
	private int maskX, maskY, mWidth, mHeight;
	
	public int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskX = 0;
		this.maskY = 0;
		this.mWidth = width;
		this.mHeight = height;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {	
		@Override
		public int compare(Entity n0, Entity n1) {
			if(n1.depth < n0.depth) {
				return +1;
			}
			if(n1.depth > n0.depth) {
				return -1;
			}
			return 0;
		}
	};
	
	public void setMask(int maskX, int maskY, int mWidth, int mHeight) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.mWidth = mWidth;
		this.mHeight = mHeight;
	}

	public int getX() {
		return (int)x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return (int)y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void tick() {
		
	}
	
	//calcular a distancia entre objetos
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		//forma como goto de escrever uma funcao de calcular distancia
		/*
		double disCalc = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return disCalc;
		*/
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16 && !isColidding(this.getX() + 1, this.getY())) {
					x++;
				}
				else if(x > target.x * 16 && !isColidding(this.getX() - 1, this.getY())) {
					x--;
				}
				if(y < target.y * 16 && !isColidding(this.getX(), this.getY() + 1)) {
					y++;
				}
				else if(y > target.y * 16 && !isColidding(this.getX(), this.getY() - 1)) {
					y--;
				}
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
			}
		}
	}
	
	public boolean isColidding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext + maskx, yNext + masky, maskw, maskh);
		for(int i = 0; i < Game.enemyes.size(); i++) {
			Enemy e = Game.enemyes.get(i);
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskX, e1.getY() + e1.maskY, e1.mWidth, e1.mHeight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskX, e2.getY() + e2.maskY, e2.mWidth, e2.mHeight);
		if(e1Mask.intersects(e2Mask) && e1.z == e2.z) {
			return true;
		}
		return false;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.RED);
		//g.fillRect(this.getX() + maskX - Camera.x, this.getY() + maskY - Camera.y, mWidth, height);
	}
	
}
