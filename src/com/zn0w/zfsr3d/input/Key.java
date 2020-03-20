package com.zn0w.zfsr3d.input;

public enum Key {
	
	ARROW_LEFT(0),
	ARROW_UP(1),
	ARROW_RIGHT(2),
	ARROW_DOWN(3);
	
	
	public final int value;
	
	
	private Key(int value) {
		this.value = value;
	}
	
}