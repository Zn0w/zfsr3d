package com.zn0w.zfsr3d.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class DirectoryRenderObject extends RenderObject {
	
	public int x1, y1, x2, y2;
	
	
	public DirectoryRenderObject(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	@Override
	void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x1, y1, x2, y2);
	}
	
}