package com.zn0w.zfsr3d.graphics;

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
	
	
	public Display(int width, int height, String title) {
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
	}
	
	public void render() {
		for (RenderObject render_object : render_objects) {
			render_object.draw(g);
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
	
}