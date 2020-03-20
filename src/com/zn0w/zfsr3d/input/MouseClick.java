package com.zn0w.zfsr3d.input;

public class MouseClick {
	
	public MouseButton button;
	public int x, y;
	
	
	public MouseClick(MouseButton button, int x, int y) {
		this.button = button;
		this.x = x;
		this.y = y;
	}
	
}