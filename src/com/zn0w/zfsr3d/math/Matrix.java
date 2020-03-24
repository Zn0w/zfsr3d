package com.zn0w.zfsr3d.math;

public class Matrix {
	
	public int[][] values;
	
	
	public Matrix(int size) {
		values = new int[size][size];
	}
	
	public Matrix(int[][] values) {
		this.values = values;
	}
	
}