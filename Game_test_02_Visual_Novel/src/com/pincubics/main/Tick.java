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
			//xx++; do metodo de manipulacao de objetos
			/*if(this.saveGame) { //system to save game
				this.saveGame = false;
				String[] opt1 = {"level"};
				int[] opt2 = {this.CUR_LEVEL};
				Menu.saveGame(opt1, opt2, 10);
				System.out.println("JOgo salvo");
			}*/
			//restartGame = false;
			
			/*if(estadoCena == jogando) {
				for(int i = 0; i < entity.size(); i++) {
					Entity e = entity.get(i);
					e.tick();
				}
				for(int i = 0; i < bullets.size(); i++) {
					bullets.get(i).tick();
				}
			}
			else if(estadoCena == entrada) {
				if(player.getY() > 100) {
					player.setY(player.getY()-1);
				}
				else {
					estadoCena = comecar;
				}
			}
			else if(estadoCena == comecar) {
				timeCena++;
				if(timeCena == masxTimeCena) {
					estadoCena = jogando;
				}
			}*/
			
			/*verificar se todos os inimigos foram detruidos para ir ao proximo nivel*/
			/*if(enemyes.size() == 0) {
				CUR_LEVEL++;
				if(CUR_LEVEL > MAX_LEVEL) {
					CUR_LEVEL = 1;
					gameState = "FINAL";
				}
				String newWorld = "level" + CUR_LEVEL + ".png";
				World.restartGame(newWorld);
			}*/
		}
		else if(Game_Main.getGameState() == "FINAL") {
			System.out.println("look this tick");
			/*framesamesFinal++;
			if(framesamesFinal == 30) {
				framesamesFinal = 0;
				if(showMessageFinal) {
					showMessageFinal = false;
				}
				else {
					showMessageFinal = true;
				}
			}
			if(restartGame) {
				restartGame = false;
				gameState = "MENU";
				//CUR_LEVEL = 1;
				//String newWorld = "level" + CUR_LEVEL + ".png";
				//World.restartGame(newWorld);
			}
		}
		else if(gameState == "GAMEOVER") {
			framesameOver++;
			if(framesameOver == 30) {
				framesameOver = 0;
				if(showMessageameOver) {
					showMessageameOver = false;
				}
				else {
					showMessageameOver = true;
				}
			}
			if(restartGame) {
				restartGame = false;
				gameState = "NORMAL";
				CUR_LEVEL = 1;
				String newWorld = "level" + CUR_LEVEL + ".png";
				World.restartGame(newWorld);
			}*/
		}
		else if(Game_Main.getGameState() == "MENU") {
			//player.updateCamera();
			Menu.tick();
		}
		
	}

}
