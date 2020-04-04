package com.zn0w.zfsr3d;

import java.util.ArrayList;

import com.zn0w.zfsr3d.fs_components.Node;
import com.zn0w.zfsr3d.graphics.DirectoryRenderObject2D;
import com.zn0w.zfsr3d.graphics.DirectoryRenderObject3D;
import com.zn0w.zfsr3d.graphics.FileRenderObject2D;
import com.zn0w.zfsr3d.graphics.FileRenderObject3D;
import com.zn0w.zfsr3d.graphics.RenderObject2D;
import com.zn0w.zfsr3d.graphics.RenderObject3D;
import com.zn0w.zfsr3d.math.Vector;

public class FileTreeToRenderObjectsTranslator {
	
	public static void translate_to_treeview(
			ArrayList<RenderObject2D> list,
			RenderObject2D parent,
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
		
		RenderObject2D render_object;
		if (node.isDirectory()) {
			render_object = new DirectoryRenderObject2D(x1, y1, x2, y2, node.getComponentName(), parent);
		}
		else {
			render_object = new FileRenderObject2D(x1, y1, x2, y2, node.getComponentName(), parent);
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
	
	public static ArrayList<RenderObject2D> translate_to_dynamic2d_view(Node node, int display_width, int display_height) {
		ArrayList<RenderObject2D> render_objects = new ArrayList<RenderObject2D>();
		int center_x = display_width / 2;
		int center_y = display_height / 4 * 3;
		int size = (display_width + display_height) / 10;
		
		if (node.isDirectory()) {
			RenderObject2D selected_object = new DirectoryRenderObject2D(
					center_x - size / 2, center_y - size / 2, center_x + size / 2, center_y + size / 2, node.getComponentName()
					);
			render_objects.add(selected_object);
			
			int child_center_x = display_width / 2;
			int child_center_y = display_height / 4;
			int child_size = (int) (size * 0.75);
			int x_margin = (int) (child_size * 3.5);
			
			int left_count = 1;
			int right_count = 0;	// since if i=0, it treats it like a right one, event though i=0 is inherited center
			int i = 0;
			for (Node child : node.getChildren())
			{
				int new_child_center_x;
				if (i % 2 == 0) {
					new_child_center_x = child_center_x + right_count * x_margin;
					right_count++;
				}
				else {
					new_child_center_x = child_center_x - left_count * x_margin;
					left_count++;
				}
				
				RenderObject2D child_object;
				if (child.isDirectory())
					child_object = new DirectoryRenderObject2D(
						new_child_center_x - child_size / 2, child_center_y - child_size / 2,
						new_child_center_x + child_size / 2, child_center_y + child_size / 2,
						child.getComponentName()
					);
				else
					child_object = new FileRenderObject2D(
						new_child_center_x - child_size / 2, child_center_y - child_size / 2,
						new_child_center_x + child_size / 2, child_center_y + child_size / 2,
						child.getComponentName()
					);
				render_objects.add(child_object);
				
				i++;
			}
		}
		else {
			RenderObject2D selected_object = new FileRenderObject2D(
					center_x - size / 2, center_y - size / 2, center_x + size / 2, center_y + size / 2, node.getComponentName()
					);
			render_objects.add(selected_object);
		}
		
		return render_objects;
	}
	
	public static ArrayList<RenderObject3D> translate_to_dynamic3d_view(Node node, int display_width, int display_height) {
		ArrayList<RenderObject3D> render_objects = new ArrayList<RenderObject3D>();
		int size = (display_width + display_height) / 10;
		
		if (node.isDirectory()) {
			RenderObject3D selected_object = new DirectoryRenderObject3D(
				node.getComponentName(),
				new Vector(new double[] {0, -display_height / 4, 0}),
				size
			);
			render_objects.add(selected_object);
			
			int child_center_x = 0;
			int child_center_y = display_height / 4;
			int child_size = (int) (size * 0.75);
			int x_margin = (int) (child_size * 3.5);
			
			int i = -1;
			for (Node child : node.getChildren())
			{
				if (i % 2 == 0) {
					child_center_x *= -1;
					child_center_x += x_margin;
				}
				else {
					child_center_x *= -1;
				}
				
				RenderObject3D child_object;
				if (child.isDirectory())
					child_object = new DirectoryRenderObject3D(
						child.getComponentName(),
						new Vector(new double[] {child_center_x, child_center_y, 0}),
						child_size
					);
				else
					child_object = new FileRenderObject3D(
						child.getComponentName(),
						new Vector(new double[] {child_center_x, child_center_y, 0}),
						child_size
					);
				render_objects.add(child_object);
				
				i++;
			}
		}
		else {
			RenderObject3D selected_object = new FileRenderObject3D(
				node.getComponentName(),
				new Vector(new double[] {0, -display_height / 4, 0}),
				size
			);
			render_objects.add(selected_object);
		}
		
		return render_objects;
	}
	
}