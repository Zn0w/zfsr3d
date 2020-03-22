package com.zn0w.zfsr3d.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyboardInput implements KeyListener {
	
	ArrayList<Integer> pressed_keys = new ArrayList<Integer>();
	ArrayList<Integer> stroked_keys = new ArrayList<Integer>();
	
	private final int store_until_flush = 20;
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!pressed_keys.contains(e.getKeyCode()))
			pressed_keys.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed_keys.remove((Integer)(e.getKeyCode()));
		
		if (stroked_keys.size() >= store_until_flush)
			stroked_keys.clear();
		
		stroked_keys.add(e.getKeyCode());
	}
	
	public boolean isKeyPressed(int keycode) {
		return pressed_keys.contains(keycode);
	}
	
	public boolean wasKeyStroked(int keycode) {
		return stroked_keys.remove((Integer)keycode);
	}
}