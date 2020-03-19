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
	
	public static void main(String[] args) {
		System.out.println("Hello World! It's Zn0w File System Respresentation 3D");
		
		String dirName = "C:\\Users\\blagi\\Desktop\\test";
		
        try {
			FileTree fs = FileTreeGenerator.getFSTree(dirName);
			
			Display display = new Display(1280, 720, "zfsr3d display test");
			display.setClearColor(Color.darkGray);
			
			fs_to_render_objects(display.getRenderObjects(), fs.getRoot(), 100, 0.5, 10, 10);
			
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
			int prevous_object_right_boundary,
			int prevous_object_bottom_boundary
	) {
		int x1 = prevous_object_right_boundary + 50;
		int y1 = prevous_object_bottom_boundary + 50;
		int x2 = x1 + size;
		int y2 = y1 + size;
		
		RenderObject render_object;
		if (node.isDirectory()) {
			render_object = new DirectoryRenderObject(x1, y1, x2, y2);
		}
		else {
			render_object = new FileRenderObject(x1, y1, x2, y2);
		}
		list.add(render_object);
		
		ArrayList<Node> children = node.getChildren();
		for (int i = 0; i < children.size(); i++)
		{
			fs_to_render_objects(list, children.get(i), (int)(size * size_scale), size_scale, x2 + i * size, y2);
		}
	}
	
}