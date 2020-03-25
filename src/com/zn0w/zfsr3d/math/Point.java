package com.zn0w.zfsr3d.math;

public class Point {
	
	public double values[];
	
	
	public Point(int size) {
		values = new double[size];
	}
	
	public Point(double[] values) {
		this.values = values;
	}
	
	public Point multiply(Matrix matrix) {
		Point new_point = new Point(values.length);
		
		if (values.length == matrix.values.length) {
			for (int i = 0; i < values.length; i++) {
				for (int j = 0; j < values.length; j++) {
					new_point.values[i] += values[j] * matrix.values[i][j];
				}
			}
		}
		
		return new_point;
	}
	
}