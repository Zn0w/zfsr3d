package com.zn0w.zfsr3d.graphics;

import com.zn0w.zfsr3d.math.Vector;

public class Camera3D {
	
	Vector origin = new Vector(3);	// x, y, z
	Vector rotation = new Vector(3);	// x-axis angle, y-axis angle, z-axis angle
	Vector dimensions = new Vector(2);	// camera width and height
	//double scale = 1.0;
	
	
	public Camera3D(Vector origin, Vector rotation, Vector dimensions) {
		this.origin = origin;
		this.rotation = rotation;
		this.dimensions = dimensions;
	}
	
	public void move(Vector dposition) {
		origin.values[0] += dposition.values[0];
		origin.values[1] += dposition.values[1];
		origin.values[2] += dposition.values[2];
	}
	
	public void rotate(Vector drotation) {
		rotation.values[0] += drotation.values[0];
		rotation.values[1] += drotation.values[1];
		rotation.values[2] += drotation.values[2];
	}
	
	public boolean captures(RenderObject3D object) {
		return 	object.position.values[0] <= (origin.values[0] + dimensions.values[0] / 2) &&
				object.position.values[0] >= (origin.values[0] - dimensions.values[0] / 2) &&
				
				object.position.values[1] <= (origin.values[1] + dimensions.values[1] / 2) &&
				object.position.values[1] >= (origin.values[1] - dimensions.values[1] / 2) &&
				
				object.position.values[2] <= origin.values[2];
	}
	
	/*public void adjustScale(double dscale) {
		scale += dscale;
	}*/
	
	public Vector getOrigin() {
		return origin;
	}
	
	public Vector getRotation() {
		return rotation;
	}
	
	public void setDimensions(Vector dimensions) {
		this.dimensions = dimensions;
	}
	
	public Vector getDimensions() {
		return dimensions;
	}
	
	/*public void setScale(double scale) {
		this.scale = scale;
	}
	
	public double getScale() {
		return scale;
	}*/
	
}