package com.pincubics.entities;

import java.awt.Graphics;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import com.pincubics.main.Game_Main;

public class Entities {
	
	public static BufferedImage INTRO = Game_Main.introSprite.getSprite(0, 0, 960, 640);
	

	protected double x;
	protected double y;
	protected int z;
	protected int width;
	protected int height;
	
	public int depth;
	
	//protected List<Node> path;
	
	private BufferedImage sprite;
	
	private int maskX, maskY, mWidth, mHeight;
	
	public int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	
	public void Entity(int x, int y, int width, int height, BufferedImage sprite) {
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
	
	/*public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {	
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
	};*/
	
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
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(), null);
		//g.setColor(Color.RED);
		//g.fillRect(this.getX() + maskX - Camera.x, this.getY() + maskY - Camera.y, mWidth, height);
	}
	
}