package br.com.pincubics.main;

//imports
import java.awt.Graphics;
import java.awt.*;

public class Menu {
    
	//definitions of options im menu e pause game menu
    private String[] mainMenuOptions = {"NOVO_JOGO", "LOAD_GAME", "OPTIONS", "EXIT"};
	private String[] pauseMenuOptions = {"CONTINUE", "OPTIONS", "RETURN_MENU"};

	//definiting length of menus options
    private int currentOption = 0;
	private int maxMainOptions = mainMenuOptions.length - 1;
	private int maxPauseOptions = pauseMenuOptions.length - 1;

    private boolean up, down, enter;
	private static boolean pauseMenu = false;

	//variable to define the width, height and scale foi menu
	private static int width = Main.WIDTH;
	private static int height = Main.HEIGHT;
	private static int scale = Main.SCALE;

	private static int contAux = 0;

    public void tick() {
		//primarys butom and actions
		if(up) { //move the options to next up option
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxMainOptions;
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxMainOptions)
				currentOption = 0;
		}
		if(enter) {
			//if(!enter) //apenas para testes
				//main.setGameState("FINAL");
			enter = false;
			Main.gameState = "NORMAL";
		}

    }

    public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width * scale, height * scale);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 36));

		g.drawString("Hezildo The Game", width+60, height);
		g.drawString("NEW GAME", width+120, height+60);
		g.drawString("CONTINUE", width+120, height+120);
		g.drawString("OPTIONS", width+120, height+180);
		g.drawString("EXIT", width+120, height+240);
		
		g.drawString(">", width+80, height+60+60*currentOption);

		

    }

	//getters and setters
    public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public boolean isEnter() {
		return enter;
	}
	public void setEnter(boolean enter) {
		this.enter = enter;
	}
}
