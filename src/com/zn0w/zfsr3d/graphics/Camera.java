package com.zn0w.zfsr3d.graphics;

public class Camera {
	
	int origin_x, origin_y, width, height;
	
	
	public Camera(int origin_x, int origin_y, int width, int height) {
		this.origin_x = origin_x;
		this.origin_y = origin_y;
		this.width = width;
		this.height = height;
	}
	
	public void move(int dx, int dy) {
		origin_x += dx;
		origin_y += dy;
	}
	
	public int getOriginX() {
		return origin_x;
	}
	
	public int getOriginY() {
		return origin_y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean captures(RenderObject object) {
		return 	object.x1 <= (origin_x + width) &&
				object.x2 >= origin_x &&
				object.y1 <= (origin_y + height) &&
				object.y2 >= origin_y;
	}
	
}