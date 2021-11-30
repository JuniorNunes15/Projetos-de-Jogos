package com.pincubics.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.pincubics.world.World;

public class Menu {
	
	public String[] options = {"Novo jogo" , "Carregar", "Sair"};

	public int currentOption = 0;
	public int maxOptions = options.length - 1;
	
	public boolean up, down, enter;
	public static boolean pause = false;
	
	public static boolean saveExist = false;
	public static boolean saveGame = false;
	
	public void tick() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExist = true;
		}
		else {
			saveExist = false;
		}
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOptions;
			}
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOptions) {
				currentOption = 0;
			}
		}
		if(enter) {
			//Sound.music.loop();
			enter = false;
			if(options[currentOption] == "Novo jogo" || options[currentOption] == "Continuar") {
				Game.gameState = "NORMAL";
				pause = false;
				file = new File("save.txt");
				file.delete();
			}
			else if(options[currentOption] == "Carregar") {
				file = new File("save.txt");
				if(file.exists()) {
					String saver = loadGame(10);
					applySave(saver);
				}
			}
			else if(options[currentOption] == "Sair") {
				System.exit(1);
			}
		}
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0]){
				case "level":
					World.restartGame("level" + spl2[1] + ".png");
					Game.gameState = "NORMAL";
					pause = false;
					break;
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] transit = singleLine.split(":");
						char[] val = transit[1].toCharArray();
						transit[1] = "";
						for(int i = 0; i < val.length; i++) {
							val[i] -= encode;
							transit[1] += val[i];
						}
						line += transit[0];
						line += ":";
						line += transit[1];
						line += "/";
					}
				}
				catch (IOException e){
				}
			}
			catch (FileNotFoundException e){
				
			}
		}
		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}
		catch (IOException e){
			e.printStackTrace();
		}
		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++) {
				value[n] += encode;
				current += value[n];
			}
			try {
				write.write(current);
				if(i < val1.length - 1) {
					write.newLine();
				}
			}
			catch (IOException e){
			}
		}
		try {
			write.flush();
			write.close();
		}
		catch (IOException e){
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("Um clone qualquer!", (Game.WIDTH * Game.SCALE) / 2 - 150, (Game.HEIGHT * Game.SCALE) / 2 - 150);
		//opçoes
		g.setFont(new Font("arial", Font.BOLD, 30));
		if(pause == false) {
			g.drawString("Novo jogo", (Game.WIDTH * Game.SCALE) / 2 - 80, (Game.HEIGHT * Game.SCALE) / 2 - 80);
		}
		else {
			g.drawString("Resumir", (Game.WIDTH * Game.SCALE) / 2 - 80, (Game.HEIGHT * Game.SCALE) / 2 - 80);
		}
		g.drawString("Carregar", (Game.WIDTH * Game.SCALE) / 2 - 80, (Game.HEIGHT * Game.SCALE) / 2 - 40);
		g.drawString("Sair", (Game.WIDTH * Game.SCALE) / 2 - 80, (Game.HEIGHT * Game.SCALE) / 2);
		
		if(options[currentOption] == "Novo jogo") {
			g.drawString("> ", (Game.WIDTH * Game.SCALE) / 2 - 120, (Game.HEIGHT * Game.SCALE) / 2 - 80);
		}
		else if(options[currentOption] == "Carregar") {
			g.drawString("> ", (Game.WIDTH * Game.SCALE) / 2 - 120, (Game.HEIGHT * Game.SCALE) / 2 - 40);
		}
		else if(options[currentOption] == "Sair") {
			g.drawString("> ", (Game.WIDTH * Game.SCALE) / 2 - 120, (Game.HEIGHT * Game.SCALE) / 2);
		}
	}

}
