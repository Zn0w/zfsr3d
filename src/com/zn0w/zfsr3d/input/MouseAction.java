package com.zn0w.zfsr3d.input;

public class MouseAction {
	
	public MouseActionType type;
	public int x, y;
	public double scroll;	// the amount of clicks scrolled (may be fractional if high-precision wheel, if negative, wheel up)
	
	
	public MouseAction(MouseActionType type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
		
		scroll = 0.0;
	}
	
	public MouseAction(MouseActionType type, int x, int y, double scroll) {
		this.type = type;
		this.x = x;
		this.y = y;
		
		this.scroll = scroll;
	}
	
}