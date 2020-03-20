package com.zn0w.zfsr3d;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import com.zn0w.zfsr3d.fs_components.FileTree;
import com.zn0w.zfsr3d.fs_components.FileTreeGenerator;
import com.zn0w.zfsr3d.fs_components.Node;
import com.zn0w.zfsr3d.graphics.DirectoryRenderObject;
import com.zn0w.zfsr3d.graphics.Display;
import com.zn0w.zfsr3d.graphics.FileRenderObject;
import com.zn0w.zfsr3d.graphics.RenderObject;

public class Main {
	
	public static int WIDTH = 1280;
	
	
	public static void main(String[] args) {
		System.out.println("Hello World! It's Zn0w File System Respresentation 3D");
		
		String dirName = "C:\\Users\\blagi\\Desktop\\test";
		
        try {
			FileTree fs = FileTreeGenerator.getFSTree(dirName);
			
			Display display = new Display(1280, 720, "zfsr3d display test");
			display.setClearColor(Color.darkGray);
			
			int initial_size = 100;
			fs_to_render_objects(display.getRenderObjects(), fs.getRoot(), initial_size, 0.5, WIDTH / 2, 10 + initial_size / 2);
			
			while (display.isClosed()) {
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
			int new_center_x;
			if (i % 2 == 0) {
				new_center_x = center_x + right_count * size;
				right_count++;
			}
			else {
				new_center_x = center_x - left_count * size;
				left_count++;
			}
			
			fs_to_render_objects(list, children.get(i), (int)(size * size_scale), size_scale, new_center_x, y2 + size);
		}
	}
	
}