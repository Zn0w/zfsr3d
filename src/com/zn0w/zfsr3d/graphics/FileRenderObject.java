package com.zn0w.zfsr3d.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class FileRenderObject extends RenderObject {

	public FileRenderObject(int x1, int y1, int x2, int y2, String name) {
		super(x1, y1, x2, y2, name);
	}
	
	@Override
	void draw(Graphics g, int offset_x, int offset_y) {
		int relative_x = x1 + offset_x;
		int relative_y = y1 + offset_y;
		int width = x2 - x1;
		int height = y2 - y1;
		
		g.setColor(Color.blue);
		g.fillOval(relative_x, relative_y, width, height);
		g.setColor(Color.RED);
		g.drawString(name, relative_x, relative_y);
	}
	
}