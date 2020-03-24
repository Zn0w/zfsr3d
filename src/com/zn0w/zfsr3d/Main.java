package com.zn0w.zfsr3d;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.zn0w.zfsr3d.fs_components.FileTree;
import com.zn0w.zfsr3d.fs_components.FileTreeGenerator;
import com.zn0w.zfsr3d.fs_components.Node;
import com.zn0w.zfsr3d.graphics.Display;
import com.zn0w.zfsr3d.graphics.GlobalGraphicsSettings;
import com.zn0w.zfsr3d.graphics.RenderObject;
import com.zn0w.zfsr3d.input.KeyboardInput;
import com.zn0w.zfsr3d.input.MouseAction;
import com.zn0w.zfsr3d.input.MouseActionType;
import com.zn0w.zfsr3d.input.MouseInput;

public class Main {
	
	private static FileTree fs;
	private static Display display;
	private static KeyboardInput keyboard_input;
	private static MouseInput mouse_input;
	
	private static final int CAMERA_SPEED = 1;
	
	private enum ViewMode {
		TREEVIEW, DYNAMIC_2D_VIEW
	};
	
	private static ViewMode view_mode = ViewMode.TREEVIEW;
	private static ArrayList<RenderObject> computed_treeview = new ArrayList<RenderObject>();
	private static HashMap<Node, ArrayList<RenderObject>> computed_dynamic2d_view = new HashMap<Node, ArrayList<RenderObject>>();
	private static Node current_node;	// current_node is used while in one of the dynamic view modes
	
	
	public static void main(String[] args) {
		System.out.println("Hello World! It's Zn0w File System Respresentation 3D");
		
		//String dirName = "C:\\Users\\blagi\\Desktop\\important";
		String dirName = "D:\\work\\projects\\java_workspace\\Zfsr3d";
		
        try {
			fs = FileTreeGenerator.getFSTree(dirName);
			
			display = new Display(1280, 720, "zfsr3d display test");
			display.setClearColor(Color.darkGray);
			
			keyboard_input = new KeyboardInput();
			display.getWindowHandle().addKeyListener(keyboard_input);
			
			mouse_input = new MouseInput();
			display.getWindowHandle().addMouseListener(mouse_input);
			display.getWindowHandle().addMouseWheelListener(mouse_input);
			
			int initial_size = 100;
			// by default the view is treeview
			FileTreeToRenderObjectsTranslator.translate_to_treeview(computed_treeview, null, fs.getRoot(), initial_size, 0.75, display.getWidth() / 2, 10 + initial_size / 2);
			display.setRenderObjects(computed_treeview);
			
			current_node = fs.getRoot();
			
			
			// TODO add a delta time handling for input
			long last_time = System.currentTimeMillis();
			
			while (display.isClosed()) {
				long current_time = System.currentTimeMillis();
				long delta_time = current_time - last_time;
				last_time = current_time;
				System.out.println("delta time: " + delta_time + "  FPS: " + (1.0f / (delta_time / 1000.0f)));
				
				process_keyboard_input(delta_time);
				process_mouse_input(delta_time);
				
				display.render();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void process_keyboard_input(long delta_time) {
		if (view_mode == ViewMode.TREEVIEW) {
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
		}
		else if (view_mode == ViewMode.DYNAMIC_2D_VIEW) {
			int dx = 0;
			if (keyboard_input.isKeyPressed(KeyEvent.VK_LEFT))
				dx = (int) (-CAMERA_SPEED * delta_time);
			else if (keyboard_input.isKeyPressed(KeyEvent.VK_RIGHT))
				dx = (int) (CAMERA_SPEED * delta_time);
			
			display.getCamera().move(dx, 0);
		}
		
		if (keyboard_input.wasKeyStroked(KeyEvent.VK_S)) {
			GlobalGraphicsSettings.SHOW_ALL_NAMES = !GlobalGraphicsSettings.SHOW_ALL_NAMES;
		}
		if (keyboard_input.wasKeyStroked(KeyEvent.VK_T)) {
			view_mode = ViewMode.TREEVIEW;
			set_up_treeview_scene();
		}
		if (keyboard_input.wasKeyStroked(KeyEvent.VK_D)) {
			view_mode = ViewMode.DYNAMIC_2D_VIEW;
			set_up_dynamic2d_view_scene();
		}
	}
	
	public static void process_mouse_input(long delta_time) {
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
							if (view_mode == ViewMode.TREEVIEW) {
								render_object.hide_children = !render_object.hide_children;
								break;
							}
							else if (view_mode == ViewMode.DYNAMIC_2D_VIEW) {
								for (Node child : current_node.getChildren()) {
									if (child.getComponentName() == render_object.name) {
										current_node = child;
										set_up_dynamic2d_view_scene();
										break;
									}
								}
							}
						}
					}
				}
			}
			else if (mouse_action.type == MouseActionType.SCROLL_UP ||
					mouse_action.type == MouseActionType.SCROLL_DOWN) {
				display.getCamera().adjustScale(mouse_action.scroll / 10.0);
			}
			else if (mouse_action.type == MouseActionType.RIGHT_CLICK) {
				if (view_mode == ViewMode.DYNAMIC_2D_VIEW) {
					if (current_node.getParent() != null) {
						current_node = current_node.getParent();
						set_up_dynamic2d_view_scene();
					}
				}
			}
		}
		mouse_input.events.clear();
	}
	
	private static void center_camera() {
		display.getCamera().move(-display.getCamera().getOriginX(), -display.getCamera().getOriginY());
	}
	
	private static void set_up_treeview_scene() {
		display.setRenderObjects(computed_treeview);
		center_camera();
	}
	
	private static void set_up_dynamic2d_view_scene() {
		if (!computed_dynamic2d_view.containsKey(current_node)) {
			ArrayList<RenderObject> render_objects =
					FileTreeToRenderObjectsTranslator.translate_to_dynamic2d_view(
							current_node, display.getWidth(), display.getHeight()
							);
			computed_dynamic2d_view.put(current_node, render_objects);
		}
		
		display.setRenderObjects(computed_dynamic2d_view.get(current_node));
		
		center_camera();
	}
		
}