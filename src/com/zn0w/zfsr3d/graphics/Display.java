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
    
    private ArrayList<RenderObject2D> render_objects_2d = new ArrayList<RenderObject2D>();
    private ArrayList<RenderObject3D> render_objects_3d = new ArrayList<RenderObject3D>();
    
    private int width, height;
	private Color clear_color;
	
	private Camera camera;
	private Camera3D camera_3d;
	
	// TODO temporary solution, before introducing special Render classes
	public enum RenderMode {
		RENDER_2D, RENDER_3D
	};
	public RenderMode render_mode = RenderMode.RENDER_2D;
	
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
		
		double angle_x = Math.PI / 6;
		double angle_y = Math.PI / 12;
		double angle_z = 0;
		
		Matrix rotation_matrix_x = new Matrix(new double[][] {
			{1, 0, 0},
			{0, Math.cos(angle_x), -Math.sin(angle_x)},
			{0, Math.sin(angle_x), Math.cos(angle_x)}
		});
		
		Matrix rotation_matrix_y = new Matrix(new double[][] {
			{Math.cos(angle_y), 0, Math.sin(angle_y)},
			{0, 1, 0},
			{-Math.sin(angle_y), 0, Math.cos(angle_y)}
		});
		
		Matrix rotation_matrix_z = new Matrix(new double[][] {
			{Math.cos(angle_z), -Math.sin(angle_z), 0},
			{Math.sin(angle_z), Math.cos(angle_z), 0},
			{0, 0, 1}
		});
		
		camera_3d = new Camera3D(
			new Vector(new double[] {0, 0, 1}),
			new Vector(new double[] {angle_x, angle_y, angle_z}),
			new Vector(new double[] {width, height}),
			MatOp.multiply(MatOp.multiply(rotation_matrix_z, rotation_matrix_y), rotation_matrix_x)
		);
	}
	
	private void render_2d() {
		// clear
		g.setColor(clear_color);
		g.fillRect(0, 0, width, height);
		
		for (RenderObject2D render_object : render_objects_2d) {
			if (render_object.parent != null && render_object.parent.hide_children) {
				render_object.hide_children = true;
				continue;
			}
					
			if (camera.captures(render_object)) {
				render_object.draw(g, -camera.origin_x, -camera.origin_y, camera.scale);
			}
		}
				
		Graphics g2 = panel.getGraphics();
		g2.drawImage(image, 0, 0, null);
	    g2.dispose();
	}
	
	private void render_3d() {
		// clear
		g.setColor(clear_color);
		g.fillRect(0, 0, width, height);
		
		Matrix projection_matrix = new Matrix(new double[][] {
			{1, 0, 0},
			{0, 1, 0}
		});
		
		for (RenderObject3D render_object : render_objects_3d) {
			if (camera_3d.captures(render_object))
				render_object.draw(
						g, projection_matrix,
						new Matrix(new double[][] {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}),
						new Vector(new double[] {width / 2, height / 2}),
						camera_3d
				);
		}
		
		Graphics g2 = panel.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
	}
	
	public void render() {
		if (render_mode == RenderMode.RENDER_2D)
			render_2d();
		else
			render_3d();
	}
	
	public ArrayList<RenderObject2D> getRenderObjects2D() {
		return render_objects_2d;
	}
	
	public void setRenderObjects2D(ArrayList<RenderObject2D> render_objects) {
		this.render_objects_2d = render_objects;
	}
	
	public ArrayList<RenderObject3D> getRenderObjects3D() {
		return render_objects_3d;
	}
	
	public void setRenderObjects3D(ArrayList<RenderObject3D> render_objects) {
		this.render_objects_3d = render_objects;
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
	
	public Camera3D getCamera3D() {
		return camera_3d;
	}
	
}