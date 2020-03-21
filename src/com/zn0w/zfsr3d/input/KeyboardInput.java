package com.zn0w.zfsr3d.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
	
	// just 4 keys for now (arrows)
	private boolean[] keys = new boolean[4];
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key_code = e.getKeyCode();
		if (key_code >= 37 && key_code <= 40)
			keys[key_code % 37] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key_code = e.getKeyCode();
		if (key_code >= 37 && key_code <= 40)
			keys[key_code % 37] = false;
	}
	
	public boolean isKeyPressed(Key key) {
		return keys[key.value];
	}
}