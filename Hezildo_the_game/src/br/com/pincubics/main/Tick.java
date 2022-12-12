package br.com.pincubics.main;

public class Tick {

    private static int time = 0;
	private static int maxTime = 60*3; //levando 3 segundos para entrar no jogo
    
    public void tick() {
        if(Main.gameState == "INTRO") {
            System.out.println("entrando");
            time++;
            //render();
            if(time >= maxTime) {
            
                Main.gameState = "MENU";
            }
        }
        if(Main.gameState == "NORMAL") {
            //Main_Level.tick();
        }
        if(Main.gameState == "FINAL") {
            Main.gameState = "MENU";
        }
        if(Main.gameState == "MENU") {
            Main.menu.tick();
        }
    }

}
