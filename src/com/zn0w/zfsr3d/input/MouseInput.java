package com.zn0w.zfsr3d.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MouseInput implements MouseListener {

	public ArrayList<MouseClick> events = new ArrayList<MouseClick>();
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		MouseClick mouse_click = null;
		
		if (e.getButton() == MouseEvent.BUTTON1)
			mouse_click = new MouseClick(MouseButton.LEFT, e.getX(), e.getY());
		else if (e.getButton() == MouseEvent.BUTTON2)
			mouse_click = new MouseClick(MouseButton.MIDDLE, e.getX(), e.getY());
		else if (e.getButton() == MouseEvent.BUTTON3)
			mouse_click = new MouseClick(MouseButton.RIGHT, e.getX(), e.getY());
		
		if (mouse_click != null)
			events.add(mouse_click);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}