package com.zn0w.zfsr3d.graphics;

import java.awt.Graphics;

public abstract class RenderObject {
	
	public int x1, y1, x2, y2;
	
	
	abstract void draw(Graphics g, int offset_x, int offset_y);
	
}