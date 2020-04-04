package com.zn0w.zfsr3d.graphics;

import java.awt.Graphics;

public abstract class RenderObject {
	
	abstract void draw(Graphics g, int offset_x, int offset_y, double scale);
	
}