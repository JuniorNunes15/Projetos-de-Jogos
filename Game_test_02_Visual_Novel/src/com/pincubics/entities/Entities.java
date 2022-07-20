package com.pincubics.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.pincubics.main.Game_Main;

public class Entities {
	
	public static BufferedImage INTRO = Game_Main.introSprite.getSprite(0, 0, 960, 640);
	public static BufferedImage PERSONAGEM = Game_Main.personagem.getSprite(0, 0, 368, 640);
	public static BufferedImage PERSONAGEM2 = Game_Main.personagem2.getSprite(0, 0, 368, 640);
	public static BufferedImage[] CENARIOS = {Game_Main.cenario1.getSprite(0, 0, 1300, 865), Game_Main.cenario2.getSprite(0, 0, 639, 361)};
	public static BufferedImage CENARIO2 = Game_Main.cenario2.getSprite(0, 0, 639, 361);
	
	protected double x;
	protected double y;
	protected int z;
	protected int width;
	protected int height;
	
	public int depth;
	
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
	}
	
}
