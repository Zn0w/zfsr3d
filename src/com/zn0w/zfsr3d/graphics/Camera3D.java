package com.zn0w.zfsr3d.graphics;

import com.zn0w.zfsr3d.math.MatOp;
import com.zn0w.zfsr3d.math.Matrix;
import com.zn0w.zfsr3d.math.Vector;

public class Camera3D {
	
	Vector origin = new Vector(3);	// x, y, z
	Vector rotation_angles = new Vector(3);	// x-axis angle, y-axis angle, z-axis angle
	Vector dimensions = new Vector(2);	// camera width and height
	
	Matrix rotation_matrix = null;
	
	
	public Camera3D(Vector origin, Vector rotation_angles, Vector dimensions, Matrix rotation_matrix) {
		this.origin = origin;
		this.rotation_angles = rotation_angles;
		this.dimensions = dimensions;
		this.rotation_matrix = rotation_matrix; 
	}
	
	public void move(Vector dposition) {
		origin.values[0] += dposition.values[0];
		origin.values[1] += dposition.values[1];
		origin.values[2] += dposition.values[2];
	}
	
	public void rotate(Vector drotation) {
		rotation_angles.values[0] += drotation.values[0];
		rotation_angles.values[1] += drotation.values[1];
		rotation_angles.values[2] += drotation.values[2];
		
		// re-calculate rotation matrix
		Matrix rotation_matrix_x = new Matrix(new double[][] {
			{1, 0, 0},
			{0, Math.cos(rotation_angles.values[0]), -Math.sin(rotation_angles.values[0])},
			{0, Math.sin(rotation_angles.values[0]), Math.cos(rotation_angles.values[0])}
		});
		
		Matrix rotation_matrix_y = new Matrix(new double[][] {
			{Math.cos(rotation_angles.values[1]), 0, Math.sin(rotation_angles.values[1])},
			{0, 1, 0},
			{-Math.sin(rotation_angles.values[1]), 0, Math.cos(rotation_angles.values[1])}
		});
		
		Matrix rotation_matrix_z = new Matrix(new double[][] {
			{Math.cos(rotation_angles.values[1]), -Math.sin(rotation_angles.values[1]), 0},
			{Math.sin(rotation_angles.values[1]), Math.cos(rotation_angles.values[1]), 0},
			{0, 0, 1}
		});
		
		rotation_matrix = MatOp.multiply(MatOp.multiply(rotation_matrix_z, rotation_matrix_y), rotation_matrix_x);
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
	
	public Vector getRotationAngles() {
		return rotation_angles;
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