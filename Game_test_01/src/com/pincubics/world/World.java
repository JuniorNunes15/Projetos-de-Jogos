package com.pincubics.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.pincubics.entities.*;
import com.pincubics.graficos.Spritesheet;
import com.pincubics.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getHeight() * map.getTileWidth()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getHeight() * map.getTileWidth()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelatual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					if(pixelatual == 0xFF000000) {//floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					}
					
					/*paredes*/
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
					
					/********/
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
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void GenerateParticles(int amount, int x, int y) { //metodo das particulas
		for(int i = 0; i < amount; i++) {
			Game.entity.add(new Particle(x, y, 1, 1, null));
		}
	}
	
	public static boolean isFreeDynamic(int xNext, int yNext, int width, int height) { //colisao das balas com as paredes
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + width- 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + height- 1) / TILE_SIZE;
		
		int x4 = (xNext + width- 1) / TILE_SIZE;
		int y4 = (yNext + height- 1) / TILE_SIZE;
		
		if(!(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile || tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile || tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile || tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile)) {
			return true;
		}
		//if(zPlayer > 0) {
		//	return true;
		//}
		return false;
	}
	
	public static boolean isFree(int xNext, int yNext, int zPlayer) { //colisao
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE- 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE- 1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE- 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE- 1) / TILE_SIZE;
		
		if(!(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile || tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile || tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile || tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile)) {
			return true;
		}
		if(zPlayer > 0) {
			return true;
		}
		return false;
	}
	
	public static void restartGame(String level) {
		Game.entity.clear();
		Game.enemyes.clear();
		Game.bullets.clear();
		Game.entity = new ArrayList<Entity>();
		Game.enemyes = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritsheet.png");
		Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entity.add(Game.player);
		Game.world = new World("/" + level);
		return;
	}
	
	public void render(Graphics g) {
		int xStart = Camera.x >> 4; // esse ">> 4" equivale a "/ 16"
		int yStart = Camera.y >> 4;
		
		int xFinal = xStart + (Game.WIDTH >> 4);
		int yFinal = yStart + (Game.HEIGHT >> 4);
		
		for(int xx = xStart; xx <= xFinal; xx++) {
			for(int yy = yStart; yy <= yFinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}
	
	public static void renderMiniMap() {
		/*for(int i = 0; i < Game.miniMapPixels.length; i++) {
			Game.miniMapPixels[i] = 0;
		}
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(tiles[xx + (yy * WIDTH)] instanceof WallTile) {
					Game.miniMapPixels[xx + (yy * WIDTH)] = 0xff0000;
				}
			}
		}
		int xPlayer = Game.player.getX()/16;
		int yPlayer = Game.player.getY()/16;
		Game.miniMapPixels[xPlayer + (yPlayer * WIDTH)] = 0x0000ff;*/
	}
	
}
