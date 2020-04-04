package com.zn0w.zfsr3d.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class DirectoryRenderObject2D extends RenderObject2D {
	
	public DirectoryRenderObject2D(int x1, int y1, int x2, int y2, String name) {
		super(x1, y1, x2, y2, name);
	}
	
	public DirectoryRenderObject2D(int x1, int y1, int x2, int y2, String name, RenderObject2D parent) {
		super(x1, y1, x2, y2, name, parent);
	}
	
	@Override
	void draw(Graphics g, int offset_x, int offset_y, double scale) {
		int relative_x = (int) ((x1 + offset_x) * scale);
		int relative_y = (int) ((y1 + offset_y) * scale);
		int width = (int) ((x2 - x1) * scale);
		int height = (int) ((y2 - y1) * scale);
		
		g.setColor(Color.yellow);
		g.fillRect(relative_x, relative_y, width, height);
		
		common_conditional_draw(g, relative_x, relative_y, offset_x, offset_y, scale, width, height);
	}
	
}