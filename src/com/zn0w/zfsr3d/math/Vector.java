package com.zn0w.zfsr3d.math;

public class Vector {
	
	public double values[];
	public int length;
	
	
	public Vector(int size) {
		values = new double[size];
		length = values.length;
	}
	
	public Vector(double[] values) {
		this.values = values;
		length = values.length;
	}
	
}