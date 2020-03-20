package com.zn0w.zfsr3d.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
        
		frame = new JFrame();
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(panel);
		frame.setVisible(true);
		
		camera = new Camera(0, 0, width, height);
	}
	
	public void render() {
		// clear
		g.setColor(clear_color);
		g.fillRect(0, 0, width, height);
		
		for (RenderObject render_object : render_objects) {
			if (camera.captures(render_object)) {
				render_object.draw(g, -camera.origin_x, -camera.origin_y, camera.scale);
			}
		}
		
		Graphics g2 = panel.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
	}
	
	public ArrayList<RenderObject> getRenderObjects() {
		return render_objects;
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