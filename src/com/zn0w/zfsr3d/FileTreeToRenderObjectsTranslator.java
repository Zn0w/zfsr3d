package com.zn0w.zfsr3d;

import java.util.ArrayList;

import com.zn0w.zfsr3d.fs_components.Node;
import com.zn0w.zfsr3d.graphics.DirectoryRenderObject;
import com.zn0w.zfsr3d.graphics.FileRenderObject;
import com.zn0w.zfsr3d.graphics.RenderObject;

public class FileTreeToRenderObjectsTranslator {
	
	public static void translate_to_treeview(
			ArrayList<RenderObject> list,
			RenderObject parent,
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
			render_object = new DirectoryRenderObject(x1, y1, x2, y2, node.getComponentName(), parent);
		}
		else {
			render_object = new FileRenderObject(x1, y1, x2, y2, node.getComponentName(), parent);
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
			
			translate_to_treeview(list, render_object, children.get(i), (int)(size * size_scale), size_scale, new_center_x, new_center_y);
		}
	}
	
}