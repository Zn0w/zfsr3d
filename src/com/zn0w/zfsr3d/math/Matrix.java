package com.zn0w.zfsr3d.math;

public class Matrix {
	
	public double[][] values;
	public int cols, rows;
	
	
	public Matrix(int rows, int cols) {
		values = new double[rows][cols];
		this.cols = cols;
		this.rows = rows;
	}
	
	public Matrix(double[][] values) {
		this.values = values;
		this.cols = values[0].length;
		this.rows = values.length;
	}
	
}