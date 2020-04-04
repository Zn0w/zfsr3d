package com.zn0w.zfsr3d.graphics;

import java.awt.Color;
import java.awt.Graphics;

import com.zn0w.zfsr3d.math.MatOp;
import com.zn0w.zfsr3d.math.Matrix;
import com.zn0w.zfsr3d.math.Vector;

public class FileRenderObject3D extends RenderObject3D {

	public double radius;
	
	// just for the test
	public Vector vertices[] = new Vector[8];
	
	
	// just for the test
	public FileRenderObject3D(String name, Vector position, int size) {
		this.name = name;
		this.position = position;
		
		vertices[0] = new Vector(new double[] {
				position.values[0] + size / 2,
				position.values[1] + size / 2,
				position.values[2] + size / 2
		});
		vertices[1] = new Vector(new double[] {
				position.values[0] - size / 2,
				position.values[1] + size / 2,
				position.values[2] + size / 2
		});
		vertices[2] = new Vector(new double[] {
				position.values[0] - size / 2,
				position.values[1] - size / 2,
				position.values[2] + size / 2
		});
		vertices[3] = new Vector(new double[] {
				position.values[0] + size / 2,
				position.values[1] - size / 2,
				position.values[2] + size / 2
		});
		vertices[4] = new Vector(new double[] {
				position.values[0] + size / 2,
				position.values[1] + size / 2,
				position.values[2] - size / 2
		});
		vertices[5] = new Vector(new double[] {
				position.values[0] - size / 2,
				position.values[1] + size / 2,
				position.values[2] - size / 2
		});
		vertices[6] = new Vector(new double[] {
				position.values[0] - size / 2,
				position.values[1] - size / 2,
				position.values[2] - size / 2
		});
		vertices[7] = new Vector(new double[] {
				position.values[0] + size / 2,
				position.values[1] - size / 2,
				position.values[2] - size / 2
		});
	}
	
	@Override
	void draw(Graphics g, Matrix projection, Matrix rotation, Matrix scale, int screen_width, int screen_height) {
		// apply rotation matrix
		Vector rotated_points[] = new Vector[8];
		for (int i = 0; i < 8; i++) {
			rotated_points[i] = MatOp.matrixToVector(MatOp.multiply(rotation, vertices[i]));
		}
		
		// get projected points
		Vector result_points[] = new Vector[8];
		for (int i = 0; i < 8; i++)
			result_points[i] = MatOp.matrixToVector(MatOp.multiply(projection, rotated_points[i]));
		
		// render resulting points
		g.setColor(Color.GREEN);
		for (int i = 0; i < 8; i++)
			draw_point(g, (int)(screen_width / 2 - result_points[i].values[0]), (int)(screen_height / 2 - result_points[i].values[1]), 8);
		
		draw_edge(g, 0, 1, result_points, screen_width, screen_height);
		draw_edge(g, 1, 2, result_points, screen_width, screen_height);
		draw_edge(g, 2, 3, result_points, screen_width, screen_height);
		draw_edge(g, 3, 0, result_points, screen_width, screen_height);
		draw_edge(g, 0, 4, result_points, screen_width, screen_height);
		draw_edge(g, 1, 5, result_points, screen_width, screen_height);
		draw_edge(g, 2, 6, result_points, screen_width, screen_height);
		draw_edge(g, 3, 7, result_points, screen_width, screen_height);
		draw_edge(g, 4, 5, result_points, screen_width, screen_height);
		draw_edge(g, 5, 6, result_points, screen_width, screen_height);
		draw_edge(g, 6, 7, result_points, screen_width, screen_height);
		draw_edge(g, 7, 4, result_points, screen_width, screen_height);
	}
	
}