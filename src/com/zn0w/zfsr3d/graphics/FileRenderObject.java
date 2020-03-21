package com.zn0w.zfsr3d.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class FileRenderObject extends RenderObject {

	public FileRenderObject(int x1, int y1, int x2, int y2, String name) {
		super(x1, y1, x2, y2, name);
	}
	
	public FileRenderObject(int x1, int y1, int x2, int y2, String name, RenderObject parent) {
		super(x1, y1, x2, y2, name, parent);
	}
	
	@Override
	void draw(Graphics g, int offset_x, int offset_y, double scale) {
		int relative_x = (int) ((x1 + offset_x) * scale);
		int relative_y = (int) ((y1 + offset_y) * scale);
		int width = (int) ((x2 - x1) * scale);
		int height = (int) ((y2 - y1) * scale);
		
		g.setColor(Color.blue);
		g.fillOval(relative_x, relative_y, width, height);
		
		if (GlobalGraphicsSettings.SHOW_ALL_NAMES) {
			g.setColor(Color.RED);
			g.drawString(name, relative_x, relative_y);
		}
		
		// TODO maybe simpler naming??
		if (parent != null) {
			int parent_relative_x = (int) ((parent.x1 + offset_x) * scale);
			int parent_relative_y = (int) ((parent.y1 + offset_y) * scale);
			int parent_width = (int) ((parent.x2 - parent.x1) * scale);
			int parent_height = (int) ((parent.y2 - parent.y1) * scale);
			
			int child_relative_center_x = relative_x + width / 2;
			int child_relative_center_y = relative_y + height / 2;
			int parent_relative_center_x = parent_relative_x + parent_width / 2;
			int parent_relative_center_y = parent_relative_y + parent_height / 2;
			
			g.setColor(Color.red);
			g.drawLine(child_relative_center_x, child_relative_center_y, parent_relative_center_x, parent_relative_center_y);
		}
	}
	
}