//classe desnecessaria pegada de outro projeto
package com.pincubics.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	//acho que não precisarei dessas funções, mas deixarei por enquanto//
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getHeight() * map.getTileWidth()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getHeight() * map.getTileWidth()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void GenerateParticles(int amount, int x, int y) {
		
	}
	public static void restartGame(String level) {
		
	}
	public static void render(Graphics g) {}
	public static void renderMiniMap() {
		
	}
}
