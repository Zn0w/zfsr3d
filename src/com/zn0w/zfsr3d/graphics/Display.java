package com.zn0w.zfsr3d.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.zn0w.zfsr3d.math.Matrix;
import com.zn0w.zfsr3d.math.Vector;
import com.zn0w.zfsr3d.math.MatOp;

public class Display {
	
	private JFrame frame;
	private JPanel panel;
	
	private BufferedImage image;
    private Graphics2D g;
    
    private ArrayList<RenderObject> render_objects = new ArrayList<RenderObject>();
    
    private int width, height;
	private Color clear_color;
	
	private Camera camera;
	
	public Display(int width, int height, String title) {
		this.width = width;
		this.height = height;
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
		
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        
		frame = new JFrame();
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		camera = new Camera(0, 0, width, height);
	}
	
	public void render() {
		// clear
		g.setColor(clear_color);
		g.fillRect(0, 0, width, height);
		
		for (RenderObject render_object : render_objects) {
			if (render_object.parent != null && render_object.parent.hide_children) {
				render_object.hide_children = true;
				continue;
			}
			
			if (camera.captures(render_object)) {
				render_object.draw(g, -camera.origin_x, -camera.origin_y, camera.scale);
			}
		}
		
		// 3D PROJECTION TEST
		
		// test orthographic projection matrix
		Vector points[] = {
				new Vector(new double[] {0.5, 0.5, 0.5}),
				new Vector(new double[] {-0.5, 0.5, 0.5}),
				new Vector(new double[] {-0.5, -0.5, 0.5}),
				new Vector(new double[] {0.5, -0.5, 0.5}),
				new Vector(new double[] {0.5, 0.5, -0.5}),
				new Vector(new double[] {-0.5, 0.5, -0.5}),
				new Vector(new double[] {-0.5, -0.5, -0.5}),
				new Vector(new double[] {0.5, -0.5, -0.5})
		};
		
		Matrix projection_matrix = new Matrix(new double[][] {
			{1, 0, 0},
			{0, 1, 0}
		});
		
		//double angle = Math.PI / 6;
		double angle = System.currentTimeMillis() / 10 * Math.PI / 72;
		
		Matrix rotation_matrix_x = new Matrix(new double[][] {
			{1, 0, 0},
			{0, Math.cos(angle), -Math.sin(angle)},
			{0, Math.sin(angle), Math.cos(angle)}
		});
		
		Matrix rotation_matrix_y = new Matrix(new double[][] {
			{Math.cos(angle), 0, Math.sin(angle)},
			{0, 1, 0},
			{-Math.sin(angle), 0, Math.cos(angle)}
		});
		
		Matrix rotation_matrix_z = new Matrix(new double[][] {
			{Math.cos(angle), -Math.sin(angle), 0},
			{Math.sin(angle), Math.cos(angle), 0},
			{0, 0, 1}
		});
		
		// scale points
		for (Vector point : points)
			for (int i = 0; i < 3; i++)
				point.values[i] *= 200;
		
		// apply rotation matrix
		for (int i = 0; i < 8; i++) {
			points[i] = MatOp.matrixToVector(MatOp.multiply(rotation_matrix_x, points[i]));
			points[i] = MatOp.matrixToVector(MatOp.multiply(rotation_matrix_y, points[i]));
			points[i] = MatOp.matrixToVector(MatOp.multiply(rotation_matrix_z, points[i]));
		}
		
		Vector result_points[] = new Vector[8];
		// get projected points
		for (int i = 0; i < 8; i++)
			result_points[i] = MatOp.matrixToVector(MatOp.multiply(projection_matrix, points[i]));
		
		// render resulting points
		g.setColor(Color.GREEN);
		for (int i = 0; i < 8; i++)
			draw_point(g, (int)(width / 2 - result_points[i].values[0]), (int)(height / 2 - result_points[i].values[1]), 8);
		
		draw_edge(g, 0, 1, result_points);
		draw_edge(g, 1, 2, result_points);
		draw_edge(g, 2, 3, result_points);
		draw_edge(g, 3, 0, result_points);
		draw_edge(g, 0, 4, result_points);
		draw_edge(g, 1, 5, result_points);
		draw_edge(g, 2, 6, result_points);
		draw_edge(g, 3, 7, result_points);
		draw_edge(g, 4, 5, result_points);
		draw_edge(g, 5, 6, result_points);
		draw_edge(g, 6, 7, result_points);
		draw_edge(g, 7, 4, result_points);
		
		// 3D PROJECTION TEST
		
		Graphics g2 = panel.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
	}
	
	private void draw_point(Graphics g, int x, int y, int r) {
		g.fillOval(x - r / 2, y - r / 2, r, r);
	}
	
	private void draw_edge(Graphics g, int node1, int node2, Vector[] points) {
		g.drawLine((int)(width / 2 - points[node1].values[0]), (int)(height / 2 - points[node1].values[1]),
				(int)(width / 2 - points[node2].values[0]), (int)(height / 2 - points[node2].values[1]));
	}
	
	public ArrayList<RenderObject> getRenderObjects() {
		return render_objects;
	}
	
	public void setRenderObjects(ArrayList<RenderObject> render_objects) {
		this.render_objects = render_objects;
	}
	
	public boolean isClosed() {
		return frame.isVisible();
	}
	
	public void close() {
		frame.dispose();
	}
	
	public void setClearColor(Color color) {
		clear_color = color;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public JFrame getWindowHandle() {
		return frame;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
}