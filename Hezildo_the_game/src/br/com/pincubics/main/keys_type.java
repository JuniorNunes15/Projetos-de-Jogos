package br.com.pincubics.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keys_type implements KeyListener{

	//Menu menu = new Menu();

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if(Main.gameState == "MENU") {
				Main.menu.setUp(true);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if(Main.gameState == "MENU") {
				Main.menu.setDown(true);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(Main.gameState == "MENU") {
				Main.menu.setEnter(true);
			}
		}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
