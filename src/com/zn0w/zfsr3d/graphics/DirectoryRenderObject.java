package com.zn0w.zfsr3d.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class DirectoryRenderObject extends RenderObject {
	
	public DirectoryRenderObject(int x1, int y1, int x2, int y2, String name) {
		super(x1, y1, x2, y2, name);
	}
	
	@Override
	void draw(Graphics g, int offset_x, int offset_y, double scale) {
		int relative_x = (int) ((x1 + offset_x) * scale);
		int relative_y = (int) ((y1 + offset_y) * scale);
		int width = (int) ((x2 - x1) * scale);
		int height = (int) ((y2 - y1) * scale);
		
		g.setColor(Color.yellow);
		g.fillRect(relative_x, relative_y, width, height);
		g.setColor(Color.RED);
		g.drawString(name, relative_x, relative_y);
	}
	
}