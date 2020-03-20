package com.zn0w.zfsr3d.graphics;

import java.awt.Graphics;

public abstract class RenderObject {
	
	public int x1, y1, x2, y2;
	public String name;
	
	
	public RenderObject(int x1, int y1, int x2, int y2, String name) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		this.name = name;
	}
	
	abstract void draw(Graphics g, int offset_x, int offset_y, double scale);
	
}