package com.zn0w.zfsr3d.graphics;

import java.awt.Color;
import java.awt.Graphics;

public abstract class RenderObject2D {
	
	public int x1, y1, x2, y2;
	public String name;
	public RenderObject2D parent;
	public boolean hide_children = false;	// TODO maybe change to true by default??
	
	
	public RenderObject2D(int x1, int y1, int x2, int y2, String name) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		this.name = name;
		this.parent = null;
	}
	
	public RenderObject2D(int x1, int y1, int x2, int y2, String name, RenderObject2D parent) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		this.name = name;
		this.parent = parent;
	}
	
	protected void common_conditional_draw(
			Graphics g, int relative_x, int relative_y, int offset_x, int offset_y, double scale, int width, int height
			) {
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
	
	abstract void draw(Graphics g, int offset_x, int offset_y, double scale);
	
}