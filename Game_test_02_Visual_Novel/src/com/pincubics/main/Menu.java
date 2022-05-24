package com.pincubics.main;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.pincubics.entities.Entities;
import com.pincubics.graphics.Spritesheet;

//import com.sun.tools.javac.launcher.Main;

public class Menu extends Object {
	
	private static String[] options = {"Novo jogo" , "Carregar", "Options", "Sair"};
	private static String[] config = {"size", "volumn", "back"};
	private static String[] sizeConfig = {"960x640", "fullscream", "back"};

	private static int currentOption = 0;
	private static int maxOptions = options.length - 1;
	
	private static int currentConfig = 0;
	private static int maxConfig = config.length - 1;
	
	private static int currentSize = 0;
	private static int maxSize = sizeConfig.length - 1;
	
	private static boolean up, down, enter;
	private static boolean pause = false;
	private static boolean configuration = false;
	private static boolean size = false;
	
	private static Game_Main main;
	
	public static void tick() {
		/*
		File file = new File("save.txt");
		if(file.exists()) {
			saveExist = true;
		}
		else {
			saveExist = false;
		}*/
		if(up) {
			up = false;
			if(configuration) {
				if(!size) {
					currentConfig--;
					if(currentConfig < 0) {
						currentConfig = maxConfig;
					}
				}
				else if(size) {
					currentSize--;
					if(currentSize < 0) {
						currentSize = maxSize;
					}
				}
				
			}
			else if(!configuration) {
				currentOption--;
				if(currentOption < 0) {
					currentOption = maxOptions;
				}
			}
		}
		if(down) {
			down = false;
			if(configuration) {
				if(!size) {
					currentConfig++;
					if(currentConfig > maxConfig) {
						currentConfig = 0;
					}
				}
				else if(size) {
					currentSize++;
					if(currentSize > maxSize) {
						currentSize = 0;
					}
				}
				
			}
			else if(!configuration) {
				currentOption++;
				if(currentOption > maxOptions) {
					currentOption = 0;
				}
			}
		}
		if(enter) {
			//Sound.music.loop();
			enter = false;
			if(!configuration) {
				if(options[currentOption] == "Novo jogo" || options[currentOption] == "Continuar") {
					Game_Main.setGameState("NORMAL");
					pause = false;
					//file = new File("save.txt");
					//file.delete();
				}
				else if(options[currentOption] == "Carregar") {
					/*file = new File("save.txt");
					if(file.exists()) {
						String saver = loadGame(10);
						applySave(saver);
					}*/
				}
				else if(options[currentOption] == "Options") {
					configuration = true;
				}
				else if(options[currentOption] == "Sair") {
					System.out.println("saindo");
					System.exit(1);
				}
			}
			
			else if(configuration) {
				if(!size) {
					if(config[currentConfig] == "size") {
						System.out.println("mudando o tamanho!");
						size = true;
					}
					else if(config[currentConfig] == "volumn") {
						System.out.println("mudando o volume!");
					}
					else if(config[currentConfig] == "back") {
						System.out.println("saindo!");
						configuration = false;
					}
				}
				else if(size) {
					if(sizeConfig[currentSize] == "960x640") {
						Game_Main.currentSize = 0;
						Game_Main.cz = true;
						System.out.println("janela!");
					}
					else if(sizeConfig[currentSize] == "fullscream") {
						System.out.println("tela cheia!");
						Game_Main.currentSize = 1;
						Game_Main.cz = true;
						//Game_Main.initFrame();
					}
					else {
						size = false;
					}
				}
				
			}
			
		}
		/**/
	}
	//public static void applySave(String str) {}
	//public static String loadGame(int encode) { //load game}
	//public static void saveGame(String[] val1, int[] val2, int encode) {}
	public static void render(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game_Main.WIDTH * Game_Main.SCALE, Game_Main.HEIGHT * Game_Main.SCALE);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 36));
		//g.drawImage(Entities.INTRO, 0, 0, Game_Main.WIDTH, Game_Main.HEIGHT, null);
		if(!configuration) {
			g.drawString("DIARIO DE UM AVENTUREIRO", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 280, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 150);
			g.setFont(new Font("arial", Font.BOLD, 30));
			if(pause == false) {
				g.drawString("Novo jogo", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 80);
			}
			else {
				g.drawString("Resumir", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 80);
			}
			g.drawString("Carregar", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 40);
			g.drawString("Options", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2);
			g.drawString("Sair", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 + 40);
			
			if(options[currentOption] == "Novo jogo") {
				g.drawString("> ", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 120, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 80);
			}
			else if(options[currentOption] == "Carregar") {
				g.drawString("> ", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 120, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 40);
			}
			else if(options[currentOption] == "Options") {
				g.drawString("> ", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 120, (Game_Main.HEIGHT * Game_Main.SCALE) / 2);
			}
			else if(options[currentOption] == "Sair") {
				g.drawString("> ", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 120, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 + 40);
			}
		}
		
		if(configuration) {
			g.drawString("Options!", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 100, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 150);
			g.setFont(new Font("arial", Font.BOLD, 30));
			if(!size) {
				g.drawString("Size", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 40);
				g.drawString("Volumn", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2);
				g.drawString("Back", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 + 40);
				if(config[currentConfig] == "size") {
					g.drawString("> ", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 120, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 40);
				}
				else if(config[currentConfig] == "volumn") {
					g.drawString("> ", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 120, (Game_Main.HEIGHT * Game_Main.SCALE) / 2);
				}
				else if(config[currentConfig] == "back") {
					g.drawString("> ", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 120, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 + 40);
				}
			}
			
			else if(size) {
				g.drawString("960x640", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 40);
				g.drawString("fullscream", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2);
				g.drawString("Back", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 80, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 + 40);
				if(sizeConfig[currentSize] == "960x640") {
					g.drawString("> ", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 120, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 - 40);
				}
				else if(sizeConfig[currentSize] == "fullscream") {
					g.drawString("> ", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 120, (Game_Main.HEIGHT * Game_Main.SCALE) / 2);
				}
				else if(sizeConfig[currentSize] == "back") {
					g.drawString("> ", (Game_Main.WIDTH * Game_Main.SCALE) / 2 - 120, (Game_Main.HEIGHT * Game_Main.SCALE) / 2 + 40);
				}
			}
			
		}
		
	}
	
	
	public static boolean isUp() {
		return up;
	}
	public static void setUp(boolean up) {
		Menu.up = up;
	}
	public static boolean isDown() {
		return down;
	}
	public static void setDown(boolean down) {
		Menu.down = down;
	}
	public static boolean isEnter() {
		return enter;
	}
	public static void setEnter(boolean enter) {
		Menu.enter = enter;
	}
	
	
}
