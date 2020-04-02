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
		
		/*int points[][] = {{100, 100}, {200, 100}, {200, 200}, {100, 200}};
		int z = -2;
		g.drawLine(points[0][0] / (-z), points[0][1] / (-z), points[1][0] / (-z), points[1][1] / (-z));
		g.drawLine(points[1][0] / (-z), points[1][1] / (-z), points[2][0] / (-z), points[2][1] / (-z));
		g.drawLine(points[2][0] / (-z), points[2][1] / (-z), points[3][0] / (-z), points[3][1] / (-z));
		g.drawLine(points[3][0] / (-z), points[3][1] / (-z), points[0][0] / (-z), points[0][1] / (-z));*/
		
		/*double near = 0.1;
		double far = 1000.0;
		double fov = Math.PI / 2;
		double aspect_ratio = width / height;
		
		Point[] points = {
				new Point(new double[] {10, 10, 1, 1}),
				new Point(new double[] {20, 10, 1, 1}),
				new Point(new double[] {20, 20, 1, 1}),
				new Point(new double[] {10, 20, 1, 1}),
				new Point(new double[] {10, 10, 11, 1}),
				new Point(new double[] {20, 10, 11, 1}),
				new Point(new double[] {20, 20, 11, 1}),
				new Point(new double[] {10, 20, 11, 1})
		};
		
		double angle = Math.PI;
		
		Matrix rotation_matrix_x = new Matrix(new double[][] {
			{1, 0, 0, 0},
			{0, Math.cos(angle), Math.sin(angle), 0},
			{0, -Math.sin(angle), Math.cos(angle), 0},
			{0, 0, 0, 1},
		});
		
		Matrix rotation_matrix_y = new Matrix(new double[][] {
			{(int)Math.cos(angle), 0, -Math.sin(angle), 0},
			{0, 1, 0, 0},
			{(int)Math.sin(angle), 0, Math.cos(angle), 0},
			{0, 0, 0, 1},
		});
		
		Matrix rotation_matrix_z = new Matrix(new double[][] {
			{Math.cos(angle), Math.sin(angle), 0, 0},
			{-Math.sin(angle), Math.cos(angle), 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1},
		});
		
		Matrix projection_matrix = new Matrix(new double[][] {
			{aspect_ratio * fov, 0, 0, 0},
			{0, fov, 0, 0},
			{0, 0, far / (far - near), 1},
			{0, 0, (-far * near) / (far - near), 0}
		});
		
		// translate points z coordinate (test)
		points[0].values[2] -= 2.5;
		points[1].values[2] -= 2.5;
		points[2].values[2] -= 2.5;
		points[3].values[2] -= 2.5;
		
		points[4].values[2] -= 2.5;
		points[5].values[2] -= 2.5;
		points[6].values[2] -= 2.5;
		points[7].values[2] -= 2.5;
		
		Point[] result_points = new Point[8];
		
		for (int i = 0; i < 8; i++) {
			points[i] = points[i].multiply(rotation_matrix_z);
		}
		
		for (int i = 0; i < 8; i++) {
			//points[i] = points[i].multiply(rotation_matrix_x);
		}
		
		for (int i = 0; i < 8; i++) {
			result_points[i] = points[i].multiply(projection_matrix);
		}
		
		for (int i = 0; i < 8; i++) {
			if (result_points[i].values[3] != 0) {
				result_points[i].values[0] /= result_points[i].values[3];
				result_points[i].values[1] /= result_points[i].values[3];
				result_points[i].values[2] /= result_points[i].values[3];
				result_points[i].values[3] /= result_points[i].values[3];
			}
		}
		
		draw_cube(result_points, g);*/
		
		// test orthographic projection matrix
		Vector points[] = {
				new Vector(new double[] {0.5, 0.5, 0}),
				new Vector(new double[] {-0.5, 0.5, 0}),
				new Vector(new double[] {-0.5, -0.5, 0}),
				new Vector(new double[] {0.5, -0.5, 0})
		};
		
		Matrix projection_matrix = new Matrix(new double[][] {
			{1, 0, 0},
			{0, 1, 0}
		});
		
		//double angle = Math.PI / 6;
		double angle = System.currentTimeMillis() / 100;
		Matrix rotation_matrix_x = new Matrix(new double[][] {
			{Math.cos(angle), -Math.sin(angle)},
			{Math.sin(angle), Math.cos(angle)}
		});
		
		for (Vector point : points)
			for (int i = 0; i < 3; i++)
				point.values[i] *= 200;
		
		Vector result_points[] = new Vector[4];
		
		for (int i = 0; i < 4; i++)
			result_points[i] = MatOp.matrixToVector(MatOp.multiply(projection_matrix, points[i]));
		
		for (int i = 0; i < 4; i++)
			result_points[i] = MatOp.matrixToVector(MatOp.multiply(rotation_matrix_x, result_points[i]));
		
		draw_point(g, (int)(width / 2 - result_points[0].values[0]), (int)(height / 2 - result_points[0].values[1]), 8);
		draw_point(g, (int)(width / 2 - result_points[1].values[0]), (int)(height / 2 - result_points[1].values[1]), 8);
		draw_point(g, (int)(width / 2 - result_points[2].values[0]), (int)(height / 2 - result_points[2].values[1]), 8);
		draw_point(g, (int)(width / 2 - result_points[3].values[0]), (int)(height / 2 - result_points[3].values[1]), 8);
		
		// 3D PROJECTION TEST
		
		Graphics g2 = panel.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
	}
	
	private static void draw_point(Graphics g, int x, int y, int r) {
		g.fillOval(x - r / 2, y - r / 2, r, r);
	}
	
	private static void draw_cube(Vector[] points, Graphics g) {
		g.drawLine((int)points[0].values[0], (int)points[0].values[1], (int)points[1].values[0], (int)points[1].values[1]);
		g.drawLine((int)points[1].values[0], (int)points[1].values[1], (int)points[2].values[0], (int)points[2].values[1]);
		g.drawLine((int)points[2].values[0], (int)points[2].values[1], (int)points[3].values[0], (int)points[3].values[1]);
		g.drawLine((int)points[3].values[0], (int)points[3].values[1], (int)points[0].values[0], (int)points[0].values[1]);
		
		g.drawLine((int)points[4].values[0], (int)points[4].values[1], (int)points[5].values[0], (int)points[5].values[1]);
		g.drawLine((int)points[5].values[0], (int)points[5].values[1], (int)points[6].values[0], (int)points[6].values[1]);
		g.drawLine((int)points[6].values[0], (int)points[6].values[1], (int)points[7].values[0], (int)points[7].values[1]);
		g.drawLine((int)points[7].values[0], (int)points[7].values[1], (int)points[4].values[0], (int)points[4].values[1]);
		
		g.drawLine((int)points[0].values[0], (int)points[0].values[1], (int)points[4].values[0], (int)points[4].values[1]);
		g.drawLine((int)points[1].values[0], (int)points[1].values[1], (int)points[5].values[0], (int)points[5].values[1]);
		g.drawLine((int)points[2].values[0], (int)points[2].values[1], (int)points[6].values[0], (int)points[6].values[1]);
		g.drawLine((int)points[3].values[0], (int)points[3].values[1], (int)points[7].values[0], (int)points[7].values[1]);
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