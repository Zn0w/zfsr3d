package com.zn0w.zfsr3d;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import com.zn0w.zfsr3d.fs_components.FileTree;
import com.zn0w.zfsr3d.fs_components.FileTreeGenerator;
import com.zn0w.zfsr3d.fs_components.Node;
import com.zn0w.zfsr3d.graphics.DirectoryRenderObject;
import com.zn0w.zfsr3d.graphics.Display;
import com.zn0w.zfsr3d.graphics.FileRenderObject;
import com.zn0w.zfsr3d.graphics.RenderObject;
import com.zn0w.zfsr3d.input.KeyboardInput;
import com.zn0w.zfsr3d.input.MouseAction;
import com.zn0w.zfsr3d.input.MouseActionType;
import com.zn0w.zfsr3d.input.MouseInput;

public class Main {
	
	public static final int CAMERA_SPEED = 1;
	
	
	public static void main(String[] args) {
		System.out.println("Hello World! It's Zn0w File System Respresentation 3D");
		
		//String dirName = "C:\\Users\\blagi\\Desktop\\important";
		String dirName = "D:\\work\\projects\\java_workspace\\Zfsr3d";
		
        try {
			FileTree fs = FileTreeGenerator.getFSTree(dirName);
			
			Display display = new Display(1280, 720, "zfsr3d display test");
			display.setClearColor(Color.darkGray);
			
			KeyboardInput keyboard_input = new KeyboardInput();
			display.getWindowHandle().addKeyListener(keyboard_input);
			
			MouseInput mouse_input = new MouseInput();
			display.getWindowHandle().addMouseListener(mouse_input);
			display.getWindowHandle().addMouseWheelListener(mouse_input);
			
			int initial_size = 100;
			fs_to_render_objects(display.getRenderObjects(), fs.getRoot(), initial_size, 0.75, display.getWidth() / 2, 10 + initial_size / 2);
			
			// TODO add a delta time handling for input
			long last_time = System.currentTimeMillis();
			
			while (display.isClosed()) {
				long current_time = System.currentTimeMillis();
				long delta_time = current_time - last_time;
				last_time = current_time;
				System.out.println("delta time: " + delta_time + "  FPS: " + (1.0f / (delta_time / 1000.0f)));
				
				// process keyboard input
				int dx = 0, dy = 0;
				if (keyboard_input.isKeyPressed(KeyEvent.VK_UP))
					dy = (int) (-CAMERA_SPEED * delta_time);
				else if (keyboard_input.isKeyPressed(KeyEvent.VK_DOWN))
					dy = (int) (CAMERA_SPEED * delta_time);
				if (keyboard_input.isKeyPressed(KeyEvent.VK_LEFT))
					dx = (int) (-CAMERA_SPEED * delta_time);
				else if (keyboard_input.isKeyPressed(KeyEvent.VK_RIGHT))
					dx = (int) (CAMERA_SPEED * delta_time);
				
				display.getCamera().move(dx, dy);
				
				// process mouse input
				for (MouseAction mouse_action : mouse_input.events) {
					// focus camera on the clicked location (move camera center to the location)
					if (mouse_action.type == MouseActionType.LEFT_CLICK) {
						dx = mouse_action.x - display.getWidth() / 2;
						dy = mouse_action.y - display.getHeight() / 2;
						
						display.getCamera().move(dx, dy);
					}
					else if (mouse_action.type == MouseActionType.SCROLL_UP ||
							mouse_action.type == MouseActionType.SCROLL_DOWN) {
						display.getCamera().adjustScale(mouse_action.scroll / 10.0);
					}
				}
				mouse_input.events.clear();
				
				display.render();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void fs_to_render_objects(
			ArrayList<RenderObject> list,
			Node node,
			int size,
			double size_scale,
			int center_x,
			int center_y
	) {
		int x1 = center_x - size / 2;
		int y1 = center_y - size / 2;
		int x2 = center_x + size / 2;
		int y2 = center_y + size / 2;
		
		RenderObject render_object;
		if (node.isDirectory()) {
			render_object = new DirectoryRenderObject(x1, y1, x2, y2, node.getComponentName());
		}
		else {
			render_object = new FileRenderObject(x1, y1, x2, y2, node.getComponentName());
		}
		list.add(render_object);
		
		ArrayList<Node> children = node.getChildren();
		int left_count = 1;
		int right_count = 0;	// since if i=0, it treats it like a right one, event though i=0 is inherited center
		for (int i = 0; i < children.size(); i++)
		{
			int x_margin = (int) (size * 3.5);
			//int x_margin = 50;
			int y_margin = size;
			int new_center_x;
			if (i % 2 == 0) {
				new_center_x = center_x + right_count * x_margin;
				right_count++;
			}
			else {
				new_center_x = center_x - left_count * x_margin;
				left_count++;
			}
			int new_center_y = center_y + y_margin;
			
			fs_to_render_objects(list, children.get(i), (int)(size * size_scale), size_scale, new_center_x, new_center_y);
		}
	}
	
}