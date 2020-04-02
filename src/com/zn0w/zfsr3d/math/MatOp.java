package com.zn0w.zfsr3d.math;

public class MatOp {
	
	public static Matrix multiply(Matrix a, Matrix b) {
		if (a.cols != b.rows) {
			// TODO make a custom exception, for now return null
			return null;
		}
		
		Matrix result = new Matrix(a.rows, b.cols);
		
		for (int i = 0; i < result.rows; i++) {
			for (int j = 0; j < result.cols; j++) {
				float sum = 0;
				for (int k = 0; k < a.cols; k++) {
					sum += a.values[i][k] * b.values[k][j];
				}
				result.values[i][j] = sum;
			}
		}
		
		return result;
	}
	
	public static Matrix multiply(Matrix a, Vector v) {
		Matrix b = vectorToMatrix(v);
		return multiply(a, b);
	}
	
	public static Matrix vectorToMatrix(Vector v) {
		Matrix m = new Matrix(v.length, 1);
		
		for (int i = 0; i < v.length; i++) {
			m.values[i][0] = v.values[i];
		}
		
		return m;
	}
	
	public static Vector matrixToVector(Matrix m) {
		Vector v = new Vector(m.rows);
		
		for (int i = 0; i < m.rows; i++) {
			v.values[i] = m.values[i][0];
		}
		
		return v;
	}
	
}