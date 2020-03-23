package com.zn0w.zfsr3d;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import com.zn0w.zfsr3d.fs_components.FileTree;
import com.zn0w.zfsr3d.fs_components.FileTreeGenerator;
import com.zn0w.zfsr3d.graphics.Display;
import com.zn0w.zfsr3d.graphics.GlobalGraphicsSettings;
import com.zn0w.zfsr3d.graphics.RenderObject;
import com.zn0w.zfsr3d.input.KeyboardInput;
import com.zn0w.zfsr3d.input.MouseAction;
import com.zn0w.zfsr3d.input.MouseActionType;
import com.zn0w.zfsr3d.input.MouseInput;

public class Main {
	
	public static final int CAMERA_SPEED = 1;
	
	public static ArrayList<RenderObject> computed_treeview = new ArrayList<RenderObject>();
	
	
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
			FileTreeToRenderObjectsTranslator.translate_to_treeview(display.getRenderObjects(), null, fs.getRoot(), initial_size, 0.75, display.getWidth() / 2, 10 + initial_size / 2);
			
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
				
				if (keyboard_input.wasKeyStroked(KeyEvent.VK_S)) {
					GlobalGraphicsSettings.SHOW_ALL_NAMES = !GlobalGraphicsSettings.SHOW_ALL_NAMES;
				}
				
				display.getCamera().move(dx, dy);
				
				// process mouse input
				for (MouseAction mouse_action : mouse_input.events) {
					// focus camera on the clicked location (move camera center to the location)
					if (mouse_action.type == MouseActionType.LEFT_CLICK) {
						//System.out.println(mouse_action.x + "  " + mouse_action.y);
						for (RenderObject render_object : display.getRenderObjects()) {
							if (display.getCamera().captures(render_object)) {
								int offset_x = -display.getCamera().getOriginX();
								int offset_y = -display.getCamera().getOriginY();
								double scale = display.getCamera().getScale();
								
								int relative_x = (int) ((render_object.x1 + offset_x) * scale);
								int relative_y = (int) ((render_object.y1 + offset_y) * scale);
								int width = (int) ((render_object.x2 - render_object.x1) * scale);
								int height = (int) ((render_object.y2 - render_object.y1) * scale);
								
								if (
										relative_x <= mouse_action.x && relative_x + width >= mouse_action.x &&
										relative_y <= mouse_action.y && relative_y + height >= mouse_action.y
									) {
									render_object.hide_children = !render_object.hide_children;
									break;
								}
							}
						}
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
	
}