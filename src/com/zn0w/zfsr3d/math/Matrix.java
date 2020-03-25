package com.zn0w.zfsr3d.math;

public class Matrix {
	
	public double[][] values;
	
	
	public Matrix(int size) {
		values = new double[size][size];
	}
	
	public Matrix(double[][] values) {
		this.values = values;
	}
	
}