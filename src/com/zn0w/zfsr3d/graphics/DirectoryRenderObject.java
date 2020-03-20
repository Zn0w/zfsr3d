package com.zn0w.zfsr3d.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class DirectoryRenderObject extends RenderObject {
	
	public String name;
	
	
	public DirectoryRenderObject(int x1, int y1, int x2, int y2, String name) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		this.name = name;
	}
	
	@Override
	void draw(Graphics g, int offset_x, int offset_y) {
		int relative_x = x1 + offset_x;
		int relative_y = y1 + offset_y;
		int width = x2 - x1;
		int height = y2 - y1;
		
		g.setColor(Color.yellow);
		g.fillRect(relative_x, relative_y, width, height);
		g.setColor(Color.RED);
		g.drawString(name, relative_x, relative_y);
	}
	
}