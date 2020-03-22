package com.zn0w.zfsr3d.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class MouseInput implements MouseListener, MouseWheelListener {

	public ArrayList<MouseAction> events = new ArrayList<MouseAction>();
	
	private final int frame_edge_x_offset = 8;
	private final int title_bar_y_offset = 30;
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		MouseAction mouse_action = null;
		
		if (e.getButton() == MouseEvent.BUTTON1)
			mouse_action = new MouseAction(MouseActionType.LEFT_CLICK, e.getX() - frame_edge_x_offset, e.getY() - title_bar_y_offset);
		else if (e.getButton() == MouseEvent.BUTTON2)
			mouse_action = new MouseAction(MouseActionType.MIDDLE_CLICK, e.getX() - frame_edge_x_offset, e.getY() - title_bar_y_offset);
		else if (e.getButton() == MouseEvent.BUTTON3)
			mouse_action = new MouseAction(MouseActionType.RIGHT_CLICK, e.getX() - frame_edge_x_offset, e.getY() - title_bar_y_offset);
		
		if (mouse_action != null)
			events.add(mouse_action);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		MouseAction mouse_action = null;
		
		double scroll = e.getPreciseWheelRotation();
		if (scroll > 0.0) {
			mouse_action = new MouseAction(MouseActionType.SCROLL_DOWN, e.getX() - frame_edge_x_offset, e.getY() - title_bar_y_offset, scroll);
		}
		else {
			mouse_action = new MouseAction(MouseActionType.SCROLL_UP, e.getX() - frame_edge_x_offset, e.getY() - title_bar_y_offset, scroll);
		}
		
		if (mouse_action != null)
			events.add(mouse_action);
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