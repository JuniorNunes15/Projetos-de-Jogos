//tentativa falha de criar um tick fora main
package com.pincubics.main;

public class Tick {
	
	public static Menu menu;
	private static int time = 0;
	private static int maxTime = 3000*60; //levando 3 segundos para entrar no jogo
	
	public static void tick() {
		if(Game_Main.getGameState() == "INTRO") {
			while(time < maxTime) {
				
				time++;
			}
			Game_Main.setGameState("NORMAL");
		}
		else if(Game_Main.getGameState() == "NORMAL") {
			System.out.println("in normal state");
			Game_Main.setGameState("MENU");
		}
		else if(Game_Main.getGameState() == "FINAL") {
			System.out.println("look this tick");
		}
		else if(Game_Main.getGameState() == "MENU") {
			Menu.tick();
		}
		
	}

}
