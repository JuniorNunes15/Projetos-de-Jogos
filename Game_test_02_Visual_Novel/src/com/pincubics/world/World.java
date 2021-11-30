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
			/*for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelatual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					if(pixelatual == 0xFF000000) {//floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					}
					
					/*paredes*//*
					else if(pixelatual == 0xFFFFFFFF) {//parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
					}
					else if(pixelatual == 0xFFFFAE0C) {//parede 2
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL_2);
					}
					else if(pixelatual == 0xFFFF006E) {//parede vinhas 1
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL_2_VINHAS1);
					}
					else if(pixelatual == 0xFFFF0072) {//parede vinhas 2
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL_2_VINHAS2);
					}
					else if(pixelatual == 0xFFFF00A5) {//parede vinhas 3
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL_2_VINHAS3);
					}
					
					/********//*
					else if(pixelatual == 0xFF0026FF) {//jogador
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
					}
					else if(pixelatual == 0xFFFF0000) {//enemy
						Enemy en = new Enemy(xx * 16, yy * 16, 16 , 16 , Entity.ENEMY_EN);
						Game.entity.add(en);
						Game.enemyes.add(en);
						//Game.entity.add(new Enemy(xx * 16, yy * 16, 16 , 16 , Entity.ENEMY_EN));
					}
					else if(pixelatual == 0xFFFF6A00) {//weapon
						Game.entity.add(new Weapon(xx * 16, yy * 16, 16 , 16 , Entity.WEAPON_EN));
					}
					else if(pixelatual == 0xFFFF7FED) {//life pack
						LifePack pack = new LifePack(xx * 16, yy * 16, 16 , 16 , Entity.LIFEPACK_EN);
						//pack.setMask(8, 8, 8, 8);
						Game.entity.add(pack);
					}
					else if(pixelatual == 0xFFFFD800) {//Bullets
						Game.entity.add(new Bullet(xx * 16, yy * 16, 16 , 16 , Entity.BULLET_EN));
					}
				}
			}*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void GenerateParticles(int amount, int x, int y) {
		
	}
	//public static boolean isFreeDynamic(int xNext, int yNext, int width, int height) {}
	public static void restartGame(String level) {
		
	}
	public static void render(Graphics g) {}
	public static void renderMiniMap() {
		
	}
	//
}
