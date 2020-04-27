package com.zn0w.zfsr3d.graphics;

import java.awt.Graphics;

import com.zn0w.zfsr3d.math.Matrix;
import com.zn0w.zfsr3d.math.Vector;

public abstract class RenderObject3D {

	public String name;
	public Vector position = new Vector(3);	// center coordinates
	
	protected void common_conditional_draw(Graphics g) {
		/*if (GlobalGraphicsSettings.SHOW_ALL_NAMES) {
			g.setColor(Color.RED);
			g.drawString(name, x, y);	// TEST (and no z component consideration for now)
		}*/
	}
	
	protected void draw_point(Graphics g, int x, int y, int r) {
		g.fillOval(x - r / 2, y - r / 2, r, r);
	}
	
	protected void draw_edge(Graphics g, int node1, int node2, Vector[] points, Vector coordinates_origin) {
		g.drawLine((int)(coordinates_origin.values[0] - points[node1].values[0]), (int)(coordinates_origin.values[1] - points[node1].values[1]),
				(int)(coordinates_origin.values[0] - points[node2].values[0]), (int)(coordinates_origin.values[1] - points[node2].values[1]));
	}
	
	// TODO change argument camera to the camera matrix
	abstract void draw(Graphics g, Matrix projection, Matrix scale, Vector coordinates_origin, Camera3D camera);
	
}