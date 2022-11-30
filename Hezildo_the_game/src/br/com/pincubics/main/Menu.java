package br.com.pincubics.main;

import java.awt.Graphics;

public class Menu {
    
    private static String[] options;

    private static int currentOption = 0;
	private static int maxOptions = options.length - 1;

    private static boolean up, down, enter;
	private static boolean pause = false;

    public static void tick() {
		
    }

    public static void render(Graphics g) {

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
